package com.koalog.util.graph;

import java.util.LinkedList;
import junit.framework.TestCase;
import org.apache.log4j.Category;

/**
 * Test the graph.
 *
 * @author Yan Georget
 */
public class MatrixGraphTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTANTS
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(MatrixGraphTest.class);

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a name
     */
    public MatrixGraphTest(String name) {
        super(name);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the constructor.
     */
    public void testConstructor1() {
        cat.info("testConstructor1");
        MatrixGraph g = new MatrixGraph(new int[][] {
            new int[] {2,3},
            new int[] {2},
            new int[] {3},
            new int[] {1}
        });
        assertEquals(g.getEdges(0).size(), 2);
        assertEquals(g.getEdges(1).size(), 1);
        assertEquals(g.getEdges(2).size(), 1);
        assertEquals(g.getEdges(3).size(), 1);
    }

    /**
     * Tests the constructor.
     */
    public void testConstructor2() {
        cat.info("testConstructor2");
         MatrixGraph g = new MatrixGraph(new int[][] {
            new int[] {13,1,2,3,4,5,6,7},
            new int[] {8},
            new int[] {9},
            new int[] {8},
            new int[] {9},
            new int[] {10},
            new int[] {11},
            new int[] {12},
            new int[] {2,4,5,13},
            new int[] {1,3,5,6,13},
            new int[] {6,7},
            new int[] {13},
            new int[] {6,13},
            new int[] {11,12}
         });
         assertEquals(g.getEdges(0).size(), 8);
         assertEquals(g.getEdges(1).get(0), new Integer(8));
    }

    /**
     * Tests the SCC.
     */
    public void testSCC1() {
        cat.info("testSCC1");
        MatrixGraph g = new MatrixGraph(new int[][] {
            new int[] {2,3},
            new int[] {2},
            new int[] {3},
            new int[] {1}
        });
        g.computeTremaux();
        g.computeSCC();
        assertEquals(1, g.getSCC(0));
        for (int i=1; i<g.getNodesNb(); i++) {
            assertEquals(0, g.getSCC(i));
        }
        assertEquals(2, g.getSCCNb());
    }

    /**
     * Tests the SCC.
     */
    public void testSCC2() {
        cat.info("testSCC2");
        MatrixGraph g = new MatrixGraph(new int[][] {
            new int[] {2},
            new int[] {3},
            new int[] {5},
            new int[] {},
            new int[] {1, 3},
            new int[] {0},
            new int[] {},
            new int[] {},
            new int[] {4}
        });
        g.computeTremaux();
        g.computeSCC();
        assertEquals(7, g.getSCCNb());
    }

    /**
     * Tests the SCC.
     */
    public void testSCC3() {
        cat.info("testSCC3");
        MatrixGraph g = new MatrixGraph(new int[][] {
            new int[] {13,1,2,3,4,5,6,7},
            new int[] {8},
            new int[] {9},
            new int[] {8},
            new int[] {9},
            new int[] {10},
            new int[] {11},
            new int[] {12},
            new int[] {2,4,5,13},
            new int[] {1,3,5,6,13},
            new int[] {6,7},
            new int[] {13},
            new int[] {6,13},
            new int[] {11,12}
        });
        g.computeTremaux();
        g.computeSCC();
        for (int i=0; i<g.getNodesNb(); i++) { 
            cat.info(i + "=" + g.getSCC(i));
        }
        assertEquals(6, g.getSCCNb());
    }

    /**
     * Tests computeTremaux.
     */
    public void testComputeTremaux1() {
        cat.info("testComputeTremaux1");
        MatrixGraph g = new MatrixGraph(new int[][] {
            new int[] {1,2,3,4,5,6,7,13},
            new int[] {8},
            new int[] {9},
            new int[] {8},
            new int[] {9},
            new int[] {10},
            new int[] {11},
            new int[] {12},
            new int[] {2,4,5,13},
            new int[] {1,3,5,6,13},
            new int[] {6,7},
            new int[] {13},
            new int[] {6,13},
            new int[] {11,12}
        });
        g.computeTremaux();
        for (int i=0; i<g.getNodesNb(); i++) {
            cat.info(i + "=" + g.getTremaux(i));
        }
        assertEquals(0, g.getTremaux(0));
        assertEquals(1, g.getTremaux(1));
        assertEquals(3, g.getTremaux(2));
        assertEquals(5, g.getTremaux(3));
        assertEquals(13, g.getTremaux(4));
        assertEquals(6, g.getTremaux(5));
        assertEquals(8, g.getTremaux(6));
        assertEquals(12, g.getTremaux(7));
        assertEquals(2, g.getTremaux(8));
        assertEquals(4, g.getTremaux(9));
        assertEquals(7, g.getTremaux(10));
        assertEquals(9, g.getTremaux(11));
        assertEquals(11, g.getTremaux(12));
        assertEquals(10, g.getTremaux(13));
    }

    /**
     * Tests computeTremaux.
     */
    public void testComputeTremaux2() {
        cat.info("testComputeTremaux2");
        MatrixGraph g = new MatrixGraph(new int[][] {
            new int[] {},
            new int[] {},
            new int[] {},
            new int[] {},
            new int[] {}
        });
        g.computeTremaux();
        for (int i=0; i<g.getNodesNb(); i++) {
            cat.info(i + "=" + g.getTremaux(i));
        }
        for (int i=0; i<5; i++) {
            assertEquals(i, g.getTremaux(i));
        }
    }

    /**
     * Tests hasPath and computePath.
     */
    public void testPath1() {
        cat.info("testPath1");
        MatrixGraph g = new MatrixGraph(new int[][] {
            new int[] {},
            new int[] {},
            new int[] {},
            new int[] {},
            new int[] {}
        });
        LinkedList start = new LinkedList();
        start.add(new Integer(0));
        boolean[] reachable = new boolean[5];
        reachable[0] = true;
        assertFalse(g.hasPath(start, 
                              1,
                              reachable, 
                              new Integer[5]));
        start.clear();
        start.add(new Integer(0));
        assertEquals(null, g.computePath(start, 1));
    }

    /**
     * Tests hasPath and computePath.
     */
    public void testPath2() {
        cat.info("testPath2");
        MatrixGraph g = new MatrixGraph(new int[][] {
            new int[] {1},
            new int[] {2},
            new int[] {3},
            new int[] {1}
        });
        LinkedList start = new LinkedList();
        start.add(new Integer(0));
        boolean[] reachable = new boolean[4];
        reachable[0] = true;
        assertTrue(g.hasPath(start, 
                             1,
                             reachable, 
                             new Integer[4]));
        start.clear();
        start.add(new Integer(0));
        assertEquals(1, g.computePath(start, 1).size());
    }

    
    /**
     * Tests hasPath and computePath.
     */
    public void testPath3() {
        cat.info("testPath3");
        MatrixGraph g = new MatrixGraph(new int[][] {
            new int[] {1},
            new int[] {2},
            new int[] {3},
            new int[] {1}
        });
        LinkedList start = new LinkedList();
        start.add(new Integer(1));
        boolean[] reachable = new boolean[4];
        reachable[1] = true;
        assertTrue(g.hasPath(start, 
                             3,
                             reachable,
                             new Integer[4]));
        start.clear();
        start.add(new Integer(1));
        assertEquals(2, g.computePath(start, 3).size());
    }
}
/*
 * $Log$
 * Revision 1.2  2004/11/25 14:09:06  yan
 * various refactoring
 *
 * Revision 1.1  2004/11/25 08:43:46  yan
 * initial revision
 *
 */
