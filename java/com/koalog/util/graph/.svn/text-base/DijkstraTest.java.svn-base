package com.koalog.util.graph;

import java.util.List;
import junit.framework.TestCase;
import org.apache.log4j.Category;

/**
 * Test Dijkstra.
 *
 * @author Yan Georget
 */
public class DijkstraTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTANTS
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(DijkstraTest.class);

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a name
     */
    public DijkstraTest(String name) {
        super(name);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests Dijkstra on the following graph:
     *
     *   3--4
     *  /    \
     * 0--2---1
     *  \    /
     *   ----
     * 
     * where edges are oriented from left to right.
     */
    public void testDijkstra1() {
        cat.info("testDijstra1");
        AbstractDijkstra  dijkstra = new Dijkstra(new NGraph(new int[][] {
            new int[] {2, 3, 1},
            new int[] {},
            new int[] {1},
            new int[] {4},
            new int[] {1}
        }));
        dijkstra.computeOptimalPaths(0, 1);
        List l = dijkstra.getOptimalPaths();
        assertEquals(l.size(), 1);
        cat.info(l.get(0));
        assertTrue(dijkstra.getOptimalCost() == 1.0f);
    }

    /**
     * Tests Dijkstra on the following graph:
     *
     *   3--4
     *  /  / \
     * 0--2-5-1
     * 
     * where edges are oriented from left to right.
     */
    public void testDijkstra2() {
        cat.info("testDijstra2");
        AbstractDijkstra  dijkstra = new Dijkstra(new NGraph(new int[][] {
            new int[] {2, 3},
            new int[] {},
            new int[] {4, 5},
            new int[] {4},
            new int[] {1},
            new int[] {1}
        }));
        dijkstra.computeOptimalPaths(0, 1);
        List l = dijkstra.getOptimalPaths();
        assertEquals(3, l.size());
        cat.info(l.get(0));
        cat.info(l.get(1));
        cat.info(l.get(2));
        assertTrue(dijkstra.getOptimalCost() == 3.0f);
    }

    /**
     * Tests Dijkstra on a grid graph without main diagonal.
     */
    public void testDijkstra_GGD() {
        cat.info("testDijstra_GGD");
        int n = 4;
        AbstractDijkstra  dijkstra = new Dijkstra(new GGD(n));
        dijkstra.computeOptimalPaths(0, n*n-1);
        List l = dijkstra.getOptimalPaths();
        assertEquals(2, l.size());
        cat.info(l.get(0));
        cat.info(l.get(1));
        assertTrue(dijkstra.getOptimalCost() == (float) n);
    }

    /**
     * Tests SinglePathDijkstra on a grid graph without main diagonal.
     */
    public void testSinglePathDijkstra_GGD() {
        cat.info("testSinglePathDijstra_GGD");
        int n = 500;
        AbstractDijkstra dijkstra = new SinglePathDijkstra(new GGD(n));
        dijkstra.computeOptimalPaths(0, n*n-1);
        assertEquals(1, dijkstra.getOptimalPaths().size());
        assertTrue(dijkstra.getOptimalCost() == (float) n);
    }
}
/*
 * $Log$
 * Revision 1.3  2003/07/30 15:58:55  yan
 * added tests
 *
 * Revision 1.2  2003/07/18 13:57:17  yan
 * added logs
 *
 * Revision 1.1  2003/07/18 13:36:56  yan
 * initial revision
 *
 */
