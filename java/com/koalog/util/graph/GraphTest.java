package com.koalog.util.graph;

import java.util.Map;
import junit.framework.TestCase;
import org.apache.log4j.Category;

/**
 * Test the graph.
 *
 * @author Yan Georget
 */
public class GraphTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTANTS
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(GraphTest.class);

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a name
     */
    public GraphTest(String name) {
        super(name);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the greedy matching.
     */
    public void testGreedyMatching() {
        cat.info("testGreedyMatching");
        Graph g = new FastGraph(new int[][] {
            new int[] {4,5},
            new int[] {3,4},
            new int[] {3},
            new int[] {},
            new int[] {},
            new int[] {}
        });
        int firstEnd = 3;
        Map matching = g.greedyMatching(firstEnd);
        assertEquals(2, matching.keySet().size());
        assertEquals(new Integer(1), matching.get(new Integer(3)));
        assertEquals(new Integer(0), matching.get(new Integer(4)));
    }

    /**
     * Tests the maximum matching.
     */
    public void testMaximumMatching1() {
        cat.info("testMaximumMatching1");
        Graph g = new FastGraph(new int[][] {
            new int[] {4,5},
            new int[] {3,4},
            new int[] {3},
            new int[] {},
            new int[] {},
            new int[] {}
        });
        int firstEnd = 3;
        Map matching = g.maximumMatching(firstEnd);
        assertEquals(3, matching.keySet().size());
    }

    /**
     * Tests the maximum matching.
     */
    public void testMaximumMatching2() {
        cat.info("testMaximumMatching2");
        int n = 1000;
        Graph g = new FastGraph(2*n);
        for (int i=0; i<n-1; i++) {
            g.addEdge(i, i+n);
            g.addEdge(i, i+n+1);
        }
        g.addEdge(n-1, n);
        Map matching = g.maximumMatching(n);
        assertEquals(n, matching.keySet().size());
    }
}
/*
 * $Log$
 * Revision 1.7  2005/07/11 15:33:03  yan
 * added method for maximumMatching
 *
 * Revision 1.6  2005/07/01 09:06:29  yan
 * initial revision
 *
 */
