package com.koalog.util.graph;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import org.apache.log4j.Category;

/**
 * A graph represented as an array of lists of nodes.
 * @author Yan Georget
 */
public class FastGraph extends Graph {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(FastGraph.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The edges of the graph. */
    protected List[] edges;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Constructs a graph.
     * @param n the size of the graph
     */
    public FastGraph(int n) {
        super(n);
        edges = new List[n];
        for (int i=n; --i>=0;) {
            edges[i] = new LinkedList();
        }
    }

    /**
     * Constructs a graph
     * given, for each node, an array of adjacent nodes.
     * @param e an array of arrays
     */
    public FastGraph(int[][] e) {
        this(e.length);
        for (int i=0; i<e.length; i++) {
            final int size = e[i].length;
            for (int j=0; j<size; j++) {
                edges[i].add(new Integer(e[i][j]));
            }
        }
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.util.graph.Graph */
    public void clear() {
        for (int i=edges.length; --i>=0;) {
            edges[i].clear();
        }
    }
    
    /** @see com.koalog.util.graph.Graph */
    public final List getEdges(int n) {
        return edges[n];
    }
    
    /** @see com.koalog.util.graph.Graph */
    public final Iterator getEdgesIterator(int n) {
        return edges[n].iterator();
    }

    /**
     * Sets the edges of a node
     * @param n a node
     * @param e a list of edges/nodes
     */
    public final void setEdges(int n, List e) {
         edges[n] = e;
    }

    /** @see com.koalog.util.graph.Graph */
    public final void clearEdges(int n) {
         edges[n].clear();
    }
    
    /** @see com.koalog.util.graph.Graph */
    public final void removeEdge(int a, int b) {
        edges[a].remove(new Integer(b));
    }

    /** @see com.koalog.util.graph.Graph */
    public final void removeEdge(int a, Integer b) {
        edges[a].remove(b);
    }

    /** @see com.koalog.util.graph.Graph */
    public final void addEdge(int a, int b) {
        edges[a].add(new Integer(b));
    }

    /** @see com.koalog.util.graph.Graph */
    public final void addEdge(int a, Integer b) {
        edges[a].add(b);
    }

    /** @see com.koalog.util.graph.Graph */
    public final boolean hasEdge(int a, int b) {
        return edges[a].contains(new Integer(b));
    }

    /** @see com.koalog.util.graph.Graph */
    public final boolean hasEdge(int a, Integer b) {
        return edges[a].contains(b);
    }

    /** @see com.koalog.util.graph.Graph */
    public final void addEdgeIfNecessary(int a, int b) {
        addEdgeIfNecessary(a, new Integer(b));
    }

    /** @see com.koalog.util.graph.Graph */
    public final void addEdgeIfNecessary(int a, Integer b) {
        if (!hasEdge(a, b)) {
            addEdge(a, b);
        }
    }

    /**
     * Returns the graph as a string.
     * @return a string
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        for (int i=0; i<edges.length; i++) {
            buf.append(i);
            buf.append("\n");
            buf.append(edges[i]);
            buf.append("\n");
        }
        return buf.toString();
    }
}
/*
 * $Log$
 * Revision 1.15  2005/07/20 15:13:25  yan
 * tiny optims
 *
 * Revision 1.14  2004/11/25 14:09:06  yan
 * various refactoring
 *
 * Revision 1.13  2004/11/25 08:40:17  yan
 * externalized some code in Graph
 *
 * Revision 1.12  2004/11/10 14:45:05  yan
 * small optims (final local vars)
 *
 * Revision 1.11  2004/11/04 17:47:54  yan
 * various small optimizations
 *
 * Revision 1.10  2004/06/30 14:45:15  yan
 * using LinkedList
 *
 * Revision 1.9  2004/06/30 12:58:17  yan
 * new method hasEdge
 *
 * Revision 1.8  2004/06/29 16:27:27  yan
 * optims using LinkedList instead of List
 *
 * Revision 1.7  2004/06/27 14:43:12  yan
 * various fixes
 *
 * Revision 1.6  2004/06/21 17:36:10  yan
 * various fixes
 *
 * Revision 1.5  2003/10/12 14:43:06  yan
 * fixed typo
 *
 * Revision 1.4  2003/10/12 14:40:23  yan
 * fixed typo
 *
 * Revision 1.3  2003/10/12 14:38:48  yan
 * fixed API
 *
 * Revision 1.2  2003/10/07 18:31:39  yan
 * added methods
 *
 * Revision 1.1  2003/10/07 11:00:14  yan
 * initial revision
 *
 */
