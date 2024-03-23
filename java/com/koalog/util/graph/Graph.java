package com.koalog.util.graph;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import org.apache.log4j.Category;

/**
 * A graph.
 * @author Yan Georget
 */
public abstract class Graph {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(Graph.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    int nodesNb;
    /** The number of SCC. */
    protected int sccNb;
    /** The SCC ordinal of the nodes. */
    protected int[] scc;
    /** The Tremaux ordinal of the nodes. */
    protected int[] tremaux;
    private int tremauxNum;
    private int[] at;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    Graph(int nodesNb) {
        this.nodesNb = nodesNb;
        tremaux = new int[nodesNb];
        at = new int[nodesNb];
        scc = new int[nodesNb];
    }

    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------
    /**
     * Returns the number of nodes.
     * @return an integer
     */
    public final int getNodesNb() {
        return nodesNb;
    }

    /**
     * Returns the number of strongly connected components.
     * @return an integer
     */
    public final int getSCCNb() {
        return sccNb;
    }

    /**
     * Returns an array giving, for each node,
     * the ordinal (arbitrary) of the strongly connected component 
     * it belongs to.
     * @return an array of integers
     */
    public final int[] getSCC() {
        return scc;
    }

    //------------------------------------------------------------------------
    // ABSTRACT METHODS
    //------------------------------------------------------------------------
    /** 
     * Clears the graph.
     */
    public abstract void clear();
    
    /**
     * Returns the list of edges adjacent to a given node.
     * @param n a node
     * @return a list
     */
    public abstract List getEdges(int n);

    /**
     * Returns an iterator on the edges adjacent to a given node.
     * @param n a node
     * @return an iterator
     */
    public abstract Iterator getEdgesIterator(int n);

    /**
     * Clears the edges of a node
     * @param n a node
     */
    public abstract void clearEdges(int n);
    
    /**
     * Removes an edge.
     * @param a the index of the start node of the edge
     * @param b the index of the end node of the edge
     */
    public abstract void removeEdge(int a, int b);

    /**
     * Removes an edge.
     * @param a the index of the start node of the edge
     * @param b the index of the end node of the edge
     */
    public abstract void removeEdge(int a, Integer b);

    /**
     * Adds an edge.
     * @param a the index of the start node of the edge
     * @param b the index of the end node of the edge
     */
    public abstract void addEdge(int a, int b);

    /**
     * Adds an edge.
     * @param a the index of the start node of the edge
     * @param b the index of the end node of the edge
     */
    public abstract void addEdge(int a, Integer b);

    /**
     * Returns true if the edge exists in the graph.
     * @param a the index of the start node of the edge
     * @param b the index of the end node of the edge
     * @return a boolean
     */
    public abstract boolean hasEdge(int a, int b);

    /**
     * Returns true if the edge exists in the graph.
     * @param a the index of the start node of the edge
     * @param b the index of the end node of the edge
     * @return a boolean
     */
    public abstract boolean hasEdge(int a, Integer b);

    /**
     * Adds an edge.
     * @param a the index of the start node of the edge
     * @param b the index of the end node of the edge
     */
    public abstract void addEdgeIfNecessary(int a, int b);

    /**
     * Adds an edge.
     * @param a the index of the start node of the edge
     * @param b the index of the end node of the edge
     */
    public abstract void addEdgeIfNecessary(int a, Integer b);

    //------------------------------------------------------------------------
    // METHODS (FOR MATCHING)
    //------------------------------------------------------------------------
    /**
     * A greedy matching method.
     * @param firstEnd the first index of end nodes
     * @return a map mapping end nodes to start nodes
     */
    public final Map greedyMatching(int firstEnd) {
        final Map matching = new HashMap(nodesNb-firstEnd);
        for (int u=0; u<firstEnd; u++) {
            for (Iterator j = getEdgesIterator(u); j.hasNext();) {
                final Object v = j.next();
                if (!matching.containsKey(v)) {
                    matching.put(v, new Integer(u));
                    break;
                }
            }
        } 
        return matching;
    }

    /**
     * Recursively searchs backward through layers to find alternating paths.
     * @param preds a map
     * @param pred a map
     * @param unmatched a list
     * @param matching a map
     * @param v an integer 
     * @return true if found path, false otherwise
     */
    private final boolean recurse(Map preds, 
                                  Map pred, 
                                  List unmatched, 
                                  Map matching, 
                                  Object v) {
        if (preds.containsKey(v)) {
            final List l = (List) preds.remove(v);
            for (Iterator i = l.iterator(); i.hasNext();) {
                final Object u = i.next();
                if (pred.containsKey(u)) {
                    final Object pu = pred.remove(u);
                    if (pu.equals(unmatched) // hack
                        || recurse(preds, 
                                   pred, 
                                   unmatched, 
                                   matching, 
                                   pu)) {
                        matching.put(v, u);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * A maximum matching method.
     * @param firstEnd the first index of end nodes
     * @return a map mapping end nodes to start nodes
     */
    public final Map maximumMatching(int firstEnd) {
        return maximumMatching(firstEnd, greedyMatching(firstEnd));
    }

    /**
     * A maximum matching method (Hopcroft-Karp algorithm).
     * @param firstEnd the first index of end nodes
     * @param matching an initial matching
     * @return a map mapping end nodes to start nodes
     */
    public final Map maximumMatching(int firstEnd, Map matching) {
        // structure residual graph into layers
        // pred[u] gives the neighbor in the previous layer 
        // for start node u 
        final Map pred = new HashMap(firstEnd);
        // preds[v] gives a list of neighbors in the previous layer 
        // for end node v 
        final Map preds = new HashMap(nodesNb-firstEnd);
        // unmatched gives a list of unmatched vertices 
        // in final layer of end nodes,
        // and is also used as a flag value for pred[u] 
        // when u is in the first layer
        final List unmatched = new LinkedList();
        final Map newLayer = new HashMap(nodesNb-firstEnd);
        while (true) {
            pred.clear();
            preds.clear();
            unmatched.clear();
            for (int u=firstEnd; --u>=0;) {
                pred.put(new Integer(u), unmatched); 
            }
            for (Iterator u = matching.values().iterator(); u.hasNext();) {
                pred.remove(u.next());
            }
            final List layer = new LinkedList(pred.keySet());
            // repeatedly extend layering structure by another pair of layers
            while (!layer.isEmpty() && unmatched.isEmpty()) {
                newLayer.clear();
                for (Iterator i=layer.iterator(); i.hasNext();) {
                    final Integer u = (Integer) i.next();
                    for (Iterator j = getEdgesIterator(u.intValue()); 
                         j.hasNext();) {
                        final Object v = j.next();
                        if (!preds.containsKey(v)) {
                            final List l = new LinkedList();
                            l.add(u);
                            newLayer.put(v, l);
                        }
                    }
                }
                layer.clear(); 
                for (Iterator j=newLayer.keySet().iterator(); j.hasNext();) {
                    final Object v = j.next();
                    preds.put(v, newLayer.get(v));
                    if (matching.containsKey(v)) {
                        final Object m = matching.get(v);
                        layer.add(m);
                        pred.put(m, v);
                    } else {
                        unmatched.add(v);
                    }
                }
            }
            // did we finish layering without finding any alternating paths?
            if (unmatched.isEmpty()) {
                return matching;
            } else {
                for (Iterator v = unmatched.iterator(); v.hasNext();) {
                    recurse(preds, 
                            pred, 
                            unmatched, 
                            matching, 
                            v.next());
                }
            }
        }
        
    }

    //------------------------------------------------------------------------
    // METHODS (FOR PATHS)
    //------------------------------------------------------------------------
    /**
     * Computes a path (no cycle allowed, BFS) 
     * from a set of nodes to a node.
     * @param frontier the nodes we are starting from
     * @param node the goal node
     * @return a path as a list of nodes
     */
    public final LinkedList computePath(LinkedList frontier, int node) {
        boolean[] reachable =  new boolean[nodesNb];
        for (Iterator i = frontier.iterator(); i.hasNext();) {
            reachable[((Integer) i.next()).intValue()] = true;
        }
        return computePath(frontier, 
                           node, 
                           reachable, 
                           new Integer[nodesNb]);
    }

    /**
     * Computes a path (no cycle allowed, BFS) 
     * from a set of nodes to a node.
     * @param frontier the nodes we are starting from
     * @param node the goal node
     * @param reachable the set of reachable nodes 
     * @param parent a representation of the resulting path 
     * by an array of parent edges
     * @return a path as a list of nodes
     */
    public final LinkedList computePath(LinkedList frontier, 
                                        int node, 
                                        boolean[] reachable, 
                                        Integer[] parent) {
        if (hasPath(frontier, node, reachable, parent)) {
            final LinkedList path = new LinkedList();
            for (Integer p = parent[node]; 
                 p != null;  
                 p = parent[p.intValue()]) {
                path.addFirst(p);
            }
            return path;
        } else {
            return null;
        }
    }
    
    /**
     * Checks if there is a path (no cycle allowed, BFS) 
     * from a set of nodes to a node.
     * @param frontier the nodes we are starting from
     * @param goal the goal node
     * @param reachable the set of reachable nodes 
     * @param parent a representation of the resulting path 
     * by an array of parent nodes
     * @return a boolean indicating if there is a path
     */
    public final boolean hasPath(LinkedList frontier, 
                                 int goal, 
                                 boolean[] reachable, 
                                 Integer[] parent) {
        while (!frontier.isEmpty()) {
            final Integer node = (Integer) frontier.removeFirst();
            for (Iterator i = getEdgesIterator(node.intValue()); i.hasNext();) {
                final Integer no = (Integer) i.next();
                final int n = no.intValue();
                if (!reachable[n]) {
                    parent[n] = node;
                    if (n == goal) {
                        return true;
                    } else {
                        reachable[n] = true;
                        frontier.addLast(no); 
                    }
                }
            }
        }
        return false;
    }

    //------------------------------------------------------------------------
    // METHODS (FOR TREMAUX)
    //------------------------------------------------------------------------
    /**
     * Returns the Tremaux ordinal.
     * @param i a node index
     * @return the Tremaux ordinal
     */
    public final int getTremaux(int i) {
        return tremaux[i];
    }

    /**
     * Computes the Tremaux ordinals.
     */
    public final void computeTremaux() {
        for (int i=nodesNb; --i>=0;) {
            tremaux[i] = -1;
        }
        tremauxNum = 0;
        int i;
        while ((i = freeIndex()) != -1) {
            computeTremaux(i);
        }
        //cat.info("tremaux computed");
    }

    private final int freeIndex() {
        for (int i=0; i<nodesNb; i++) {
            if (tremaux[i] == -1) {
                return i;
            }
        }
        return -1;
    }

    private final void computeTremaux(int i) {
        tremaux[i] = tremauxNum++;
        for (Iterator j = getEdgesIterator(i); j.hasNext();) {
            final int n = ((Integer) j.next()).intValue();
            if (tremaux[n] == -1) {
                computeTremaux(n);
            }
        }
    }

    //------------------------------------------------------------------------
    // METHODS (FOR STRONGLY CONNECTED COMPONENTS)
    //------------------------------------------------------------------------
    /**
     * Computes the SCC (Tarjan algorithm).
     * 
     * <P>Needs a call to <CODE>computeTremaux</CODE>.
     */
    public final void computeSCC() {
        for (int i=nodesNb; --i>=0;) {
            scc[i] = at[i] = -1;
        }
        sccNb = 0;
        for (int i=0; i<nodesNb; i++) {
            if (scc[i] == -1 && at[tremaux[i]] == -1) {
                at(i, tremaux[i], at);
            }
        }
        //cat.info("scc computed");
    }

    /**
     * Returns the ordinal (arbitrary) 
     * of the SCC corresponding to this node (index).
     * @param i a node index
     * @return an ordinal
     */
    public final int getSCC(int i) {
        return scc[i];
    }

    /**
     * Returns a boolean indicating if two nodes belongs to the same SCC.
     * @param i a node index
     * @param j a node index
     * @return a boolean
     */
    public final boolean sameSCC(int i, int j) {
        return scc[i] == scc[j];
    }
    
    private final void at(int u, int tu, int[] at) {
        at[tu] = tu;
        for (Iterator k = getEdgesIterator(u); k.hasNext();) {
            final int v = ((Integer) k.next()).intValue();
            final int tv = tremaux[v];
            if (at[tv] == -1) {
                at(v, tv, at);
                if (at[tv] < at[tu]) {
                    at[tu] = at[tv];
                }
            } else if (scc[v] == -1 && at[tv] < at[tu]) {
                at[tu] = at[tv];
            }
        }
        if (tremaux[u] == at[tu]) {
            sccNb++;
            mark(u, tu);
        }
    }
    
    private final void mark(int u, int tu) {
        scc[u] = sccNb-1;
        for (Iterator k = getEdgesIterator(u); k.hasNext();) {
            final int v = ((Integer) k.next()).intValue();
            if (tremaux[v] > tu && scc[v] == -1) {
                mark(v, tremaux[v]);
            }
        }
    }
}
/*
 * $Log$
 * Revision 1.16  2005/07/20 15:13:25  yan
 * tiny optims
 *
 * Revision 1.15  2005/07/13 10:09:39  yan
 * optimized maximumMatching
 *
 * Revision 1.14  2005/07/12 16:34:18  yan
 * optimized
 *
 * Revision 1.13  2005/07/12 16:26:30  yan
 * optimized
 *
 * Revision 1.12  2005/07/12 11:54:08  yan
 * working Hopcropt-Karp algo
 *
 * Revision 1.11  2005/07/11 15:33:03  yan
 * added method for maximumMatching
 *
 * Revision 1.10  2005/07/01 09:05:50  yan
 * added commented algo
 *
 * Revision 1.9  2004/11/25 14:20:17  yan
 * fixed style
 *
 * Revision 1.8  2004/11/25 14:09:06  yan
 * various refactoring
 *
 * Revision 1.7  2004/11/25 08:40:32  yan
 * fixed style
 *
 */
