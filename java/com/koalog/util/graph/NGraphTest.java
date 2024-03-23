package com.koalog.util.graph;

import java.util.LinkedList;
import junit.framework.TestCase;
import org.apache.log4j.Category;

/**
 * Test the graph.
 *
 * @author Yan Georget
 */
public class NGraphTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTANTS
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(NGraphTest.class);

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a name
     */
    public NGraphTest(String name) {
        super(name);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the constructor.
     */
    public void testConstructor() {
        cat.info("testConstructor");
        NGraph g = new NGraph(new int[][] {
            new int[] {2,3},
            new int[] {2},
            new int[] {3},
            new int[] {1}
        });
        assertEquals(g.getNode(0).getEdges().length, 2);
        assertEquals(g.getNode(1).getEdges().length, 1);
        assertEquals(g.getNode(2).getEdges().length, 1);
        assertEquals(g.getNode(3).getEdges().length, 1);
    }

    /**
     * Tests the SCC.
     */
    public void testSCC1() {
        cat.info("testSCC1");
        NGraph g = new NGraph(new int[][] {
            new int[] {2,3},
            new int[] {2},
            new int[] {3},
            new int[] {1}
        });
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
        NGraph g = new NGraph(new int[][] {
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
        g.computeSCC();
        assertEquals(7, g.getSCCNb());
    }

    /**
     * Tests the SCC.
     */
    public void testSCC3() {
        cat.info("testSCC3");
        NGraph g = new NGraph(new int[][] {
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
        g.computeSCC();
        for (int i=0; i<g.nodes.length; i++) { 
            cat.info(i + "=" + g.getSCC(i));
        }
        assertEquals(6, g.getSCCNb());
    }

    /**
     * Tests computeTremaux.
     */
    public void testComputeTremaux1() {
        cat.info("testComputeTremaux1");
        NGraph g = new NGraph(new int[][] {
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
        for (int i=0; i<g.nodes.length; i++) {
            cat.info(i + "=" + g.getTremaux(i));
        }
        assertEquals(0, g.getTremaux(0));
        assertEquals(5, g.getTremaux(1));
        assertEquals(7, g.getTremaux(2));
        assertEquals(9, g.getTremaux(3));
        assertEquals(13, g.getTremaux(4));
        assertEquals(10, g.getTremaux(5));
        assertEquals(4, g.getTremaux(6));
        assertEquals(12, g.getTremaux(7));
        assertEquals(6, g.getTremaux(8));
        assertEquals(8, g.getTremaux(9));
        assertEquals(11, g.getTremaux(10));
        assertEquals(2, g.getTremaux(11));
        assertEquals(3, g.getTremaux(12));
        assertEquals(1, g.getTremaux(13));
    }

    /**
     * Tests computeTremaux.
     */
    public void testComputeTremaux2() {
        cat.info("testComputeTremaux2");
        NGraph g = new NGraph(new int[][] {
            new int[] {},
            new int[] {},
            new int[] {},
            new int[] {},
            new int[] {}
        });
        g.computeTremaux();
        for (int i=0; i<g.nodes.length; i++) {
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
        NGraph g = new NGraph(new int[][] {
            new int[] {},
            new int[] {},
            new int[] {},
            new int[] {},
            new int[] {}
        });
        LinkedList start = new LinkedList();
        start.add(g.getNode(0));
        assertFalse(g.hasPath(start, 
                              1,
                              new boolean[6], 
                              new NEdge[5]));
        start.clear();
        start.add(g.getNode(0));
        assertEquals(null, g.computePath(start, 1));
    }

    /**
     * Tests hasPath and computePath.
     */
    public void testPath2() {
        cat.info("testPath2");
        NGraph g = new NGraph(new int[][] {
            new int[] {1},
            new int[] {2},
            new int[] {3},
            new int[] {1}
        });
        LinkedList start = new LinkedList();
        start.add(g.getNode(0));
        NEdge[] parent = new NEdge[4];
        assertTrue(g.hasPath(start, 
                             1,
                             new boolean[4], 
                             parent));
        start.clear();
        start.add(g.getNode(0));
        assertEquals(1, g.computePath(start, 1).size());
    }

    
    /**
     * Tests hasPath and computePath.
     */
    public void testPath3() {
        cat.info("testPath3");
        NGraph g = new NGraph(new int[][] {
            new int[] {1},
            new int[] {2},
            new int[] {3},
            new int[] {1}
        });
        LinkedList start = new LinkedList();
        start.add(g.getNode(1));
        NEdge[] parent = new NEdge[4];
        assertTrue(g.hasPath(start, 
                             3,
                             new boolean[4],
                             parent));
        start.clear();
        start.add(g.getNode(1));
        assertEquals(2, g.computePath(start, 3).size());
    }
}
/*
 * $Log$
 * Revision 1.7  2003/10/12 14:11:22  yan
 * fixed style
 *
 * Revision 1.6  2003/10/07 11:02:17  yan
 * changed signatures
 *
 * Revision 1.5  2003/10/05 17:47:07  yan
 * added new methods for computing paths
 *
 * Revision 1.4  2003/10/03 13:26:54  yan
 * various optims (and API changes)
 *
 * Revision 1.3  2003/08/07 12:40:23  yan
 * added tests
 *
 * Revision 1.2  2003/08/05 13:51:31  yan
 * added SCC (tarjan algo)
 *
 * Revision 1.1  2003/07/17 15:17:05  yan
 * initial revision
 *
 */
