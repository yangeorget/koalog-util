package com.koalog.util.graph;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import org.apache.log4j.Category;

/**
 * A graph represented as a matrix.
 * @author Yan Georget
 */
public class MatrixGraph extends Graph {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(MatrixGraph.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    private boolean[][] m;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Constructs a graph.
     * @param n the size of the graph
     */
    public MatrixGraph(int n) {
        super(n);
        m = new boolean[n][n];
    }

    /**
     * Constructs a graph
     * given, for each node, an array of adjacent nodes.
     * @param e an array of arrays
     */
    public MatrixGraph(int[][] e) {
        this(e.length);
        for (int i=0; i<e.length; i++) {
            final int size = e[i].length;
            for (int j=0; j<size; j++) {
                m[i][e[i][j]] = true;
            }
        }
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.util.graph.Graph */
    public void clear() {
        final int n = m.length;
        for (int i=n; --i>=0;) {
            for (int j=n; --j>=0;) {
                m[i][j] = false;
            }
        }
    }
    
    /** @see com.koalog.util.graph.Graph */
    public final List getEdges(int n) {
        final List l = new LinkedList();
        for (int j=0; j<m.length; j++) {
            if (m[n][j]) {
                l.add(new Integer(j));
            }
        }
        return l;
    }

    /** @see com.koalog.util.graph.Graph */
    public final Iterator getEdgesIterator(final int n) {
        return new Iterator() {
                int index = -1;

                public boolean hasNext() {
                    while (++index<m.length) {
                        if (m[n][index]) {
                            return true;
                        }
                    }
                    return false;
                }
                
                public Object next() {
                    return new Integer(index);
                }

                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
    }

    /** @see com.koalog.util.graph.Graph */
    public final void clearEdges(int n) {
        for (int j=m.length; --j>=0;) {
            m[n][j] = false;
        }
    }
    
    /** @see com.koalog.util.graph.Graph */
    public final void removeEdge(int a, int b) {
        m[a][b] = false;
    }

    /** @see com.koalog.util.graph.Graph */
    public final void removeEdge(int a, Integer b) {
        removeEdge(a, b.intValue());
    }

    /** @see com.koalog.util.graph.Graph */
    public final void addEdge(int a, int b) {
        m[a][b] = true;
    }

    /** @see com.koalog.util.graph.Graph */
    public final void addEdge(int a, Integer b) {
        addEdge(a, b.intValue());
    }

    /** @see com.koalog.util.graph.Graph */
    public final boolean hasEdge(int a, int b) {
        return m[a][b];
    }

    /** @see com.koalog.util.graph.Graph */
    public final boolean hasEdge(int a, Integer b) {
        return hasEdge(a, b.intValue());
    }

    /** @see com.koalog.util.graph.Graph */
    public final void addEdgeIfNecessary(int a, int b) {
        m[a][b] = true;
    }

        /** @see com.koalog.util.graph.Graph */
    public final void addEdgeIfNecessary(int a, Integer b) {
        addEdgeIfNecessary(a, b.intValue());
    }
}
/*
 * $Log$
 * Revision 1.3  2005/07/20 15:13:25  yan
 * tiny optims
 *
 * Revision 1.2  2004/11/25 14:09:06  yan
 * various refactoring
 *
 * Revision 1.1  2004/11/25 08:43:46  yan
 * initial revision
 *
 */
