package com.koalog.util.graph;

import java.util.LinkedList;
import java.util.Iterator;
import org.apache.log4j.Category;

/**
 * A graph for flow algorithms.
 *
 * @author Yan Georget
 */
public class FlowGraph extends FastGraph {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(FlowGraph.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The residual graph. */
    Graph resGraph;
    /** The flow. */
    int[][] flow;
    int[][] cap;
    int[][] low;
    int[][] resCap;
    boolean[][] plus;
    boolean[][] minus;
    int nodesNb1;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param n the nb of nodes in the graph
     */
    public FlowGraph(int n) {
        super(n);
        nodesNb1 = n-1;
        plus = new boolean[n][n];
        minus = new boolean[n][n];
        low = new int[n][n];
        cap = new int[n][n];
        flow = new int[n][n];
        resCap = new int[n][n];
        resGraph = new FastGraph(n);
    }


    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------
    /** 
     * Returns the current flow.
     * @return a flow 
     * (ie a function that gives the flow value for two node indices).
     */
    public final int[][] getFlow() {
        return flow;
    }
    
    /**
     * Sets the flow.
     * @param flow a flow
     */
    public final void setFlow(int[][] flow) {
        this.flow = flow;
    }

    final boolean plus(int i, int j) {
        return plus[i][j];
    }

    final boolean minus(int i, int j) {
        return minus[i][j];
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see Object */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        for (int i=0; i<nodesNb; i++) {
            buf.append(i);
            buf.append(": ");
            Iterator j = getEdgesIterator(i);
            while (j.hasNext()) {
                int k = ((Integer) j.next()).intValue();
                buf.append(k);
                buf.append("(");
                buf.append(flow[i][k]);
                buf.append(") ");
            }
            buf.append("\n");
        }
        return buf.toString();
    }

    /** 
     * Adds an edge.
     * @param i the start node
     * @param j the end node
     * @param l the low value
     * @param c the capacity
     */
    public final void addEdge(int i, int j, int l, int c) {
        super.addEdge(i, j);
        low[i][j] = l;
        cap[i][j] = c;
    }
    
    //------------------------------------------------------------------------
    // METHODS (FLOW)
    //------------------------------------------------------------------------
    /** 
     * Returns a flow value.
     * @param a a node index
     * @param b a node index
     * @return a flow value
     */
    public final int getFlow(int a, int b) {
        return flow[a][b];
    }

    /** 
     * Returns the global flow value.
     * @return a flow value
     */
    public final int getFlowValue() {
        return flow[0][nodesNb1];
    }

    /** 
     * Sets a flow value.
     * @param a a node index
     * @param b a node index
     * @param f a flow value
     */
    public final void setFlow(int a, int b, int f) {
        flow[a][b] = f;
    }

    /**
     * Computes a maximal flow.
     */
    public final void maximalFlow() {
        LinkedList path;
        while ((path = augmentingPath(nodesNb1, 0)) != null) {
            augmentFlow(nodesNb1, 0, path);
        }
    }
    
    /**
     * Resets the flow.
     */
    public final void initFlow() {
        for (int i=nodesNb; --i>=0;) {
            for (int j=nodesNb; --j>=0;) {
                flow[i][j] = 0;
            } 
        }
    }

    /**
     * Computes a feasible flow.
     * @return a boolean indicating if there is a feasible flow.
     */
    public final boolean feasibleFlow() {
        updateResidualGraph(); 
        for (int i=nodesNb; --i>=0;) {
            for (Iterator j = getEdgesIterator(i); j.hasNext();) {
                final int x = ((Integer) j.next()).intValue();
                while (flow[i][x] < low[i][x]) { // the arc is infeasible
                    final LinkedList path = augmentingPath(x, i);
                    if (path == null) {
                        return false;
                    }
                    augmentFlow(x, i, path);
                }
            }
        }
        return true;
    }
        
    /** 
     * Augments the flow along a path in the residual graph.
     * @param x the start node
     * @param y the end node
     * @param path a path
     */
    protected final void augmentFlow(int x, int y, LinkedList path) {
        final int size = path.size();
        final int[] p = new int[size+1];
        for (int j=size; --j>=0;) {
            p[j] = ((Integer) path.get(j)).intValue();
        }
        p[size] = y;
        final int val = Math.min(residualCapacity(p), resCap[y][x]);
        flow[y][x] += val;
        updateResidualGraph(y, x);
        for (int k=1; k<p.length; k++) {
            final int u = p[k-1];
            final int v = p[k];
            if (plus[u][v]) {
                flow[u][v] += val;
            } else if (minus[u][v]) {
                flow[v][u] -= val;
            }
            updateResidualGraph(u, v);
        }
    }

    /**
     * Updates the residual graph locally.
     * @param i the start node
     * @param j the end node
     */
    private final void updateResidualGraph(int i, int j) {
        if (hasEdge(i, j)) {
            final int fij = flow[i][j];
            if (fij < cap[i][j]) {
                if (low[i][j] < fij) {
                    resCap[i][j] = cap[i][j] - fij;
                    resCap[j][i] = fij - low[i][j];
                    plus[j][i] = minus[i][j] = false;
                    plus[i][j] = minus[j][i] = true;
                    //resGraph.addEdgeIfNecessary(i, j);
                    resGraph.addEdgeIfNecessary(j, i);
                } else {
                    resCap[i][j] = cap[i][j] - fij;
                    resCap[j][i] = 0;
                    plus[j][i] = minus[i][j] = minus[j][i] = false;
                    plus[i][j] = true;
                    resGraph.removeEdge(j, i);
                    //resGraph.addEdgeIfNecessary(i, j);
                }
            } else {
                if (low[i][j] < fij) {
                    resCap[j][i] = fij - low[i][j];
                    resCap[i][j] = 0;
                    plus[i][j] = plus[j][i] = minus[i][j] = false;
                    minus[j][i] = true;
                    resGraph.removeEdge(i, j);
                    resGraph.addEdgeIfNecessary(j, i);
                } else {
                    resCap[i][j] = resCap[j][i] = 0;
                    plus[i][j] = plus[j][i] = minus[i][j] = minus[j][i] = false;
                    resGraph.removeEdge(i, j);
                    resGraph.removeEdge(j, i);
                }
            }
        } else if (hasEdge(j, i)) {
            final int fji = flow[j][i];
            if (fji < cap[j][i]) {
                if (low[j][i] < fji) {
                    resCap[j][i] = cap[j][i] - fji;
                    resCap[i][j] = fji - low[j][i];
                    plus[i][j] = minus[j][i] = false;
                    plus[j][i] = minus[i][j] = true;
                    resGraph.addEdgeIfNecessary(j, i);
                    //resGraph.addEdgeIfNecessary(i, j);
                } else {
                    resCap[j][i] = cap[j][i] - fji;
                    resCap[i][j] = 0;
                    plus[i][j] = minus[i][j] = minus[j][i] = false;
                    plus[j][i] = true;
                    resGraph.removeEdge(i, j);
                    resGraph.addEdgeIfNecessary(j, i);
                }
            } else {
                if (low[j][i] < fji) {
                    resCap[i][j] = fji - low[j][i];
                    resCap[j][i] = 0;
                    plus[i][j] = plus[j][i] = minus[j][i] = false;
                    minus[i][j] = true;
                    resGraph.removeEdge(j, i);
                    //resGraph.addEdgeIfNecessary(i, j);
                } else {
                    resCap[i][j] = resCap[j][i] = 0;
                    plus[i][j] = plus[j][i] = minus[i][j] = minus[j][i] = false;
                    resGraph.removeEdge(i, j);
                    resGraph.removeEdge(j, i);
                }
            }
        } else {
            resCap[i][j] = resCap[j][i] = 0;
            plus[i][j] = plus[j][i] = minus[i][j] = minus[j][i] = false;
            resGraph.removeEdge(i, j);
            resGraph.removeEdge(j, i);
        }
    }
    
    /**
     * Updates the residual graph and the capacity, given the current flow.
     */
    public final void updateResidualGraph() {
        resGraph.clear();
        for (int i=nodesNb; --i>=0;) {
            for (int j=nodesNb; --j>=0;) {
                plus[i][j] = minus[i][j] = false;
                resCap[i][j] = 0;
            } 
        }
        for (int i=nodesNb; --i>=0;) {
            for (Iterator e = getEdgesIterator(i); e.hasNext();) {
                final Integer jo = (Integer) e.next();
                final int j = jo.intValue();
                final int fij = flow[i][j];
                final int cij = cap[i][j];
                final int lij = low[i][j];
                if (fij < cij) {
                    resGraph.addEdge(i, jo);
                    resCap[i][j] = cij - fij;
                    plus[i][j] = true;
                }
                if (lij < fij) {
                    resGraph.addEdge(j, i);
                    resCap[j][i] = fij - lij;
                    minus[j][i] = true;
                } 
            }
        }
    }

    /**
     * Returns an augmenting path between two nodes.
     * @param ix a node index
     * @param iy a node index
     * @return a path
     */
    public final LinkedList augmentingPath(int ix, int iy) {
        if (flow[iy][ix] < cap[iy][ix]) {
            final Integer ixo = new Integer(ix);
            final Integer[] parent = new Integer[nodesNb];
            final boolean[] reachable = new boolean[nodesNb];
            reachable[ix] = true;
            final LinkedList frontier = new LinkedList();
            for (Iterator i = resGraph.getEdgesIterator(ix); i.hasNext();) {
                final Integer z = (Integer) i.next();
                final int iz = z.intValue();
                if (iz != iy) { // we want path different from x->y
                    reachable[iz] = true;
                    frontier.addLast(z);
                    parent[iz] = ixo;
                }
            }
            return resGraph.computePath(frontier, iy, reachable, parent);
        } 
        return null;
    }

    /**
     * Returns the residual capacity of a path.
     * @param p a path
     * @return an integer
     */
    public final int residualCapacity(int[] p) {
        int tmp = Integer.MAX_VALUE;
        for (int k=p.length-1; --k>=0;) {
            final int res = resCap[p[k]][p[k+1]];
            if (res < tmp) {
                tmp = res;
            }
        }
        return tmp;
    }

    /**
     * Returns the residual graph
     * @return a graph
     */
    public final Graph getResidualGraph() {
        return resGraph;
    }
}
/*
 * $Log$
 * Revision 1.17  2005/07/20 15:13:25  yan
 * tiny optims
 *
 * Revision 1.16  2004/11/25 14:09:06  yan
 * various refactoring
 *
 * Revision 1.15  2004/11/25 13:15:18  yan
 * small optims
 *
 * Revision 1.14  2004/11/25 08:42:19  yan
 * using new API
 *
 * Revision 1.13  2004/11/10 14:45:05  yan
 * small optims (final local vars)
 *
 * Revision 1.12  2004/11/04 17:47:54  yan
 * various small optimizations
 *
 * Revision 1.11  2004/06/30 15:02:03  yan
 * optimized updateResidualGraph
 *
 * Revision 1.10  2004/06/30 14:45:36  yan
 * optimized updateResidualGraph
 *
 * Revision 1.9  2004/06/30 12:58:28  yan
 * using new method hasEdge
 *
 * Revision 1.8  2004/06/30 12:50:56  yan
 * optimized update of residual graph
 *
 * Revision 1.7  2004/06/29 16:27:39  yan
 * optims using LinkedList instead of List
 *
 * Revision 1.6  2004/06/27 14:43:37  yan
 * various fixes
 *
 * Revision 1.5  2004/06/21 17:38:24  yan
 * various fixes
 *
 * Revision 1.4  2004/06/21 17:36:24  yan
 * various fixes
 *
 * Revision 1.3  2003/10/12 14:38:18  yan
 * fixed style
 *
 * Revision 1.2  2003/10/12 14:11:47  yan
 * fixed style
 *
 * Revision 1.1  2003/10/07 18:34:24  yan
 * *** empty log message ***
 *
 */
