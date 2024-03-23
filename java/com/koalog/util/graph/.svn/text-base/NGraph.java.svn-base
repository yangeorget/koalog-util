package com.koalog.util.graph;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.log4j.Category;

/**
 * A graph.
 * @author Yan Georget
 */
public class NGraph {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(NGraph.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The nodes of the graph. */
    protected Node[] nodes;
    /** The number of SCC. */
    protected int sccNb;
    /** The SCC ordinal of the nodes. */
    protected int[] scc;
    /** The Tremaux ordinal of the nodes. */
    protected int[] tremaux;
    private int tremauxNum;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Constructs a graph.
     */
    public NGraph() {
    }

    /**
     * Constructs a graph
     * given, for each node, an array of adjacent nodes.
     * @param a an array of arrays
     */
    public NGraph(int[][] a) {
        nodes = new Node[a.length];
        for (int i=0; i<nodes.length; i++) {
            nodes[i] = new Node(i);
        }
        for (int i=0; i<nodes.length; i++) {
            NEdge[] edges = new NEdge[a[i].length];
            for (int j=0; j<edges.length; j++) {
                edges[j] = new NEdge(nodes[i], 
                                    getNode(a[i][j]));
            }
            nodes[i].setEdges(edges);
        }
    }

    /**
     * Constructs a graph
     * given, for each node, a list of adjacent nodes.
     * @param a an array of lists
     */
    public NGraph(List[] a) {
        updateNodes(a);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Updates a graph
     * given, for each node, a list of adjacent nodes.
     * @param a an array of lists
     */
    public void updateNodes(List[] a) {
        nodes = new Node[a.length];
        for (int i=0; i<nodes.length; i++) {
            nodes[i] = new Node(i);
        }
        for (int i=0; i<nodes.length; i++) {
            NEdge[] edges = new NEdge[a[i].size()];
            for (int j=0; j<edges.length; j++) {
                edges[j] = 
                    new NEdge(nodes[i],
                              getNode(((Integer)a[i].get(j)).intValue()));
            }
            nodes[i].setEdges(edges);
        }
    }

    /**
     * Returns a node of a given index.
     * @param index an index
     * @return a node
     */
    public final Node getNode(int index) {
        return nodes[index];
    }

    /**
     * Returns the number of nodes.
     * @return an integer
     */
    public final int getNodesNb() {
        return nodes.length;
    }
    
    /**
     * Gets the list of all nodes.
     * @return a list
     */
    public final List getNodesAsList() {
        return Arrays.asList(nodes);
    }

    /**
     * Returns the cost of an edge.
     * @param e an edge
     * @return the float 1.0
     */
    public float getCost(NEdge e) {
        return 1.0f;
    }

    /**
     * Removes an edge.
     * @param a the index of the start node of the edge
     * @param b the index of the end node of the edge
     */
    public void removeEdge(int a, int b) {
        // TODO: optimize
        List l = new ArrayList();
        NEdge[] edges = nodes[a].getEdges();
        for (int i=0; i<edges.length; i++) {
            NEdge e = edges[i];
            if (e.getEndNode().getIndex() != b) {
                l.add(e);
            }
        }
        nodes[a].setEdges((NEdge[]) l.toArray(new NEdge[] {}));
    }

    /**
     * Returns the graph as a string.
     * @return a string
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        for (int i=0; i<getNodesNb(); i++) {
            Node n = getNode(i);
            buf.append(n);
            buf.append("\n");
            NEdge[] es = (NEdge[]) n.getEdges();
            for (int j=0; j<es.length; j++) {
                buf.append(es[j]);
                buf.append("\n");
            }
        }
        return buf.toString();
    }

    //------------------------------------------------------------------------
    // METHODS (FOR PATHS)
    //------------------------------------------------------------------------
    /**
     * Computes a path (no cycle allowed, BFS) 
     * from a set of nodes to a node.
     * @param frontier the set of nodes we are starting from
     * @param y the goal node
     * @return a path as a list of edges
     */
    public List computePath(LinkedList frontier, int y) {
        return computePath(frontier, 
                           y, 
                           new boolean[nodes.length],
                           new NEdge[nodes.length]);
    }

    /**
     * Computes a path (no cycle allowed, BFS) 
     * from a set of nodes to a node.
     * @param frontier the set of nodes we are starting from
     * @param y the goal node
     * @param visited the set of nodes visited so far
     * @param parent a representation of the resulting path 
     * by an array of parent edges
     * @return a path as a list of edges
     */
    List computePath(LinkedList frontier, 
                     int y, 
                     boolean[] visited, 
                     NEdge[] parent) {
        if (hasPath(frontier, y, visited, parent)) {
            List path = new ArrayList();
            NEdge p;
            int idx = y;
            while ((p = parent[idx]) != null) {
                path.add(0, p);
                idx = p.getStartNode().getIndex();
            }
            return path;
        } else {
            return null;
        }
    }

    /**
     * Checks if there is a path (no cycle allowed, BFS) 
     * from a set of nodes to a node.
     * @param frontier the set of nodes we are starting from
     * @param iy the goal node
     * @param visited the set of nodes visited so far
     * @param parent a representation of the resulting path 
     * by an array of parent edges
     * @return a boolean indicating if there is a path
     */
    boolean hasPath(LinkedList frontier, 
                    int iy, 
                    boolean[] visited, 
                    NEdge[] parent) {
        while (!frontier.isEmpty()) {
            Node x = (Node) frontier.removeFirst();
            int ix = x.getIndex();
            if (ix == iy) {
                return true;
            }
            visited[ix] = true;
            NEdge[] edges = x.getEdges();
            for (int i=0; i<edges.length; i++) {
                NEdge e = (NEdge) edges[i];
                Node z = e.getEndNode();
                int iz = z.getIndex();
                if (!visited[iz]) {
                    frontier.addLast(z);
                    parent[iz] = e;
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
     * Inits the computation.
     */
    public void initTremaux() {
        tremaux = null;
    }

    /**
     * Computes the Tremaux ordinals.
     */
    public void computeTremaux() {
        tremaux = new int[nodes.length];
        for (int i=0; i<nodes.length; i++) {
            tremaux[i] = -1;
        }
        tremauxNum = 0;
        int i;
        while ((i = freeIndex()) != -1) {
            computeTremaux(i);
        }
    }

    private int freeIndex() {
        for (int i=0; i<nodes.length; i++) {
            if (tremaux[i] == -1) {
                return i;
            }
        }
        return -1;
    }

    private void computeTremaux(int i) {
        tremaux[i] = tremauxNum++;
        NEdge[] edges = nodes[i].getEdges();
        for (int k=0; k<edges.length; k++) {
            int v =  edges[k].getEndNode().getIndex();
            if (tremaux[v] == -1) {
                computeTremaux(v);
            }
        }
    }

    //------------------------------------------------------------------------
    // METHODS (FOR STRONGLY CONNECTED COMPONENTS)
    //------------------------------------------------------------------------
    /**
     * Computes the SCC (Tarjan algorithm).
     */
    public void computeSCC() {
        if (tremaux == null) {
            computeTremaux();
        }
        scc = new int[nodes.length];
        int[] at = new int[nodes.length];
        for (int i=0; i<nodes.length; i++) {
            scc[i] = -1;
            at[i] = -1;
        }
        sccNb = 0;
        for (int i=0; i<nodes.length; i++) {
            if (scc[i] == -1 && at[tremaux[i]] == -1) {
                at(i, tremaux[i], at);
            }
        }
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
    
    private void at(int u, int tu, int[] at) {
        at[tu] = tu;
        NEdge[] edges = nodes[u].getEdges();
        for (int k=0; k<edges.length; k++) {
            Node n = edges[k].getEndNode();
            int v = n.getIndex();
            if (at[tremaux[v]] == -1) {
                at(v, tremaux[v], at);
                if (at[tremaux[v]] < at[tremaux[u]]) {
                    at[tremaux[u]] = at[tremaux[v]];
                }
            } else {
                if (scc[v] == -1) {
                    if (at[tremaux[v]] < at[tremaux[u]]) {
                        at[tremaux[u]] = at[tremaux[v]];
                    }
                }
            }
        }
        if (tremaux[u] == at[tremaux[u]]) {
            sccNb++;
            mark(u, tremaux[u]);
        }
    }
    
    private void mark(int u, int tu) {
        scc[u] = sccNb-1;
        NEdge[] edges = nodes[u].getEdges();
        for (int k=0; k<edges.length; k++) {
            Node n = edges[k].getEndNode();
            int v = n.getIndex();
            if (tremaux[v] > tremaux[u] && scc[v] == -1) {
                mark(v, tremaux[v]);
            }
        }
    }
    
    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------
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

    /**
     * Gets the nodes.
     * @return an array
     */
    public final Node[] getNodes() {
        return nodes;
    }

    /**
     * Sets the nodes.
     * @param nodes an array
     */
    public void setNodes(Node[] nodes) {
        this.nodes = nodes;
    }
}
/*
 * $Log$
 * Revision 1.12  2003/10/12 14:11:11  yan
 * fixed style
 *
 * Revision 1.11  2003/10/07 11:01:32  yan
 * changed some signatures
 *
 * Revision 1.10  2003/10/05 17:47:07  yan
 * added new methods for computing paths
 *
 * Revision 1.9  2003/10/05 14:25:00  yan
 * setNodes not final anymore
 *
 * Revision 1.8  2003/10/03 13:26:53  yan
 * various optims (and API changes)
 *
 * Revision 1.7  2003/10/01 18:16:42  yan
 * made some methods final
 *
 * Revision 1.6  2003/08/07 14:49:29  yan
 * added reset/init methods
 *
 * Revision 1.5  2003/08/07 12:40:44  yan
 * added SCC
 *
 * Revision 1.4  2003/08/05 13:51:31  yan
 * added SCC (tarjan algo)
 *
 * Revision 1.3  2003/07/22 15:57:14  yan
 * nodes is now a protected property
 *
 * Revision 1.2  2003/07/18 13:37:45  yan
 * added getCost method
 *
 * Revision 1.1  2003/07/17 15:17:05  yan
 * initial revision
 *
 */
