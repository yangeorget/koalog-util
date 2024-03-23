package com.koalog.util.graph;

import java.util.List;
import junit.framework.TestCase;
import org.apache.log4j.Category;

/**
 * Tests the FlowGraph class.
 * @author Yan Georget
 */
public class FlowGraphTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public FlowGraphTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    private FlowGraph regin() {
        FlowGraph g = new FlowGraph(14);
        g.addEdge(0, 13, 0, Integer.MAX_VALUE);
        for (int i=1; i<=7; i++) {
            g.addEdge(i, 0, 0, 1);
        }
        g.addEdge(8, 1, 0, 1);
        g.addEdge(8, 2, 0, 1);
        g.addEdge(8, 3, 0, 1);
        g.addEdge(8, 4, 0, 1);
        g.addEdge(8, 5, 0, 1);
        g.addEdge(9, 1, 0, 1);
        g.addEdge(9, 2, 0, 1);
        g.addEdge(9, 3, 0, 1);
        g.addEdge(9, 4, 0, 1);
        g.addEdge(9, 5, 0, 1);
        g.addEdge(9, 6, 0, 1);
        g.addEdge(10, 5, 0, 1);
        g.addEdge(10, 6, 0, 1);
        g.addEdge(10, 7, 0, 1);
        g.addEdge(11, 6, 0, 1);
        g.addEdge(12, 6, 0, 1);
        g.addEdge(12, 7, 0, 1);
        g.addEdge(13, 8, 1, 2);
        g.addEdge(13, 9, 1, 2);
        g.addEdge(13, 10, 1, 1);
        g.addEdge(13, 11, 0, 2);
        g.addEdge(13, 12, 0, 2);
        return g;
    }

    /**
     * Tests updateResidualGraphAndCapacity.
     */
    public void testUpdateResidualGraph() {
        // creating the graph
        FlowGraph g = regin();
        // creating an infeasible flow
        g.flow[13][8] = 2;
        g.flow[13][9] = 2;
        g.flow[13][10] = 0;
        g.flow[13][11] = 1;
        g.flow[13][12] = 1;
        g.flow[8][1] = 1;
        g.flow[8][3] = 1;
        g.flow[9][2] = 1;
        g.flow[9][5] = 1;
        g.flow[11][6] = 1;
        g.flow[12][7] = 1;
        g.flow[1][0] = 1;
        g.flow[2][0] = 1;
        g.flow[3][0] = 1;
        g.flow[5][0] = 1;
        g.flow[6][0] = 1;
        g.flow[7][0] = 1;
        g.flow[0][13] = 6;
        g.updateResidualGraph();
        Graph r = g.getResidualGraph();
        List l;
        // node 0
        l = r.getEdges(0);
        assertEquals(7, l.size());
        assertTrue(l.contains(new Integer(13)));
        assertTrue(l.contains(new Integer(1)));
        assertTrue(l.contains(new Integer(2)));
        assertTrue(l.contains(new Integer(3)));
        assertTrue(l.contains(new Integer(5)));
        assertTrue(l.contains(new Integer(6)));
        assertTrue(l.contains(new Integer(7)));
        assertTrue(g.plus(0,13));
        assertTrue(g.minus(0,1));
        assertTrue(g.minus(0,2));
        assertTrue(g.minus(0,3));
        assertTrue(g.minus(0,5));
        assertTrue(g.minus(0,6));
        assertTrue(g.minus(0,7));
        // node 1
        l = r.getEdges(1);
        assertEquals(1, l.size());
        assertTrue(l.contains(new Integer(8)));
        assertTrue(g.minus(1, 8));
        // node 2
        l = r.getEdges(2);
        assertEquals(1, l.size());
        assertTrue(l.contains(new Integer(9)));
        assertTrue(g.minus(2, 9));
        // node 3
        l = r.getEdges(3);
        assertEquals(1, l.size());
        assertTrue(l.contains(new Integer(8)));
        assertTrue(g.minus(3, 8));
        // node 4
        l = r.getEdges(4);
        assertEquals(1, l.size());
        assertTrue(l.contains(new Integer(0)));
        assertTrue(g.plus(4, 0));
        // node 5
        l = r.getEdges(5);
        assertEquals(1, l.size());
        assertTrue(l.contains(new Integer(9)));
        assertTrue(g.minus(5, 9));
        // node 6
        l = r.getEdges(6);
        assertEquals(1, l.size());
        assertTrue(l.contains(new Integer(11)));
        assertTrue(g.minus(6,11));
        // node 7
        l = r.getEdges(7);
        assertEquals(1, l.size());
        assertTrue(l.contains(new Integer(12)));
        assertTrue(g.minus(7, 12));
        // node 8
        l = r.getEdges(8);
        assertEquals(4, l.size());
        assertTrue(l.contains(new Integer(2)));
        assertTrue(l.contains(new Integer(4)));
        assertTrue(l.contains(new Integer(5)));
        assertTrue(l.contains(new Integer(13)));
        assertTrue(g.plus(8, 2));
        assertTrue(g.plus(8, 4));
        assertTrue(g.plus(8, 5));
        assertTrue(g.minus(8, 13));
        // node 9
        l = r.getEdges(9);
        assertEquals(5, l.size());
        assertTrue(l.contains(new Integer(1)));
        assertTrue(l.contains(new Integer(3)));
        assertTrue(l.contains(new Integer(4)));
        assertTrue(l.contains(new Integer(6)));
        assertTrue(l.contains(new Integer(13)));
        assertTrue(g.plus(9,1));
        assertTrue(g.plus(9,3));
        assertTrue(g.plus(9,4));
        assertTrue(g.plus(9,6));
        assertTrue(g.minus(9,13));
        // node 10
        l = r.getEdges(10);
        assertEquals(3, l.size());
        assertTrue(l.contains(new Integer(5)));
        assertTrue(l.contains(new Integer(6)));
        assertTrue(l.contains(new Integer(7)));
        assertTrue(g.plus(10,5));
        assertTrue(g.plus(10,6));
        assertTrue(g.plus(10,7));
        // node 11
        l = r.getEdges(11);
        assertEquals(1, l.size());
        assertTrue(l.contains(new Integer(13)));
        assertTrue(g.minus(11,13));
        // node 12
        l = r.getEdges(12);
        assertEquals(2, l.size());
        assertTrue(l.contains(new Integer(6)));
        assertTrue(l.contains(new Integer(13)));
        assertTrue(g.plus(12,6));
        assertTrue(g.minus(12,13));
        // node 13
        l = r.getEdges(13);
        assertEquals(4, l.size());
        assertTrue(l.contains(new Integer(0)));
        assertTrue(l.contains(new Integer(10)));
        assertTrue(l.contains(new Integer(11)));
        assertTrue(l.contains(new Integer(12)));
        assertTrue(g.minus(13,0));
        assertTrue(g.plus(13,10));
        assertTrue(g.plus(13,11));
        assertTrue(g.plus(13,12));
    }

    /**
     * Tests augmentingPath.
     */
    public void testAugmentingPath() {
        // creating the graph
        FlowGraph g = regin();
        // creating an infeasible flow
        g.flow[13][8] = 2;
        g.flow[13][9] = 2;
        g.flow[13][10] = 0;
        g.flow[13][11] = 1;
        g.flow[13][12] = 1;
        g.flow[8][1] = 1;
        g.flow[8][3] = 1;
        g.flow[9][2] = 1;
        g.flow[9][5] = 1;
        g.flow[11][6] = 1;
        g.flow[12][7] = 1;
        g.flow[1][0] = 1;
        g.flow[2][0] = 1;
        g.flow[3][0] = 1;
        g.flow[5][0] = 1;
        g.flow[6][0] = 1;
        g.flow[7][0] = 1;
        g.flow[0][13] = 6;
        g.updateResidualGraph();
        List path = g.augmentingPath(13, 0);
        assertEquals(5, path.size()); 
        assertEquals(13, ((Integer) path.get(0)).intValue());
        assertEquals(10, ((Integer) path.get(1)).intValue());
        assertEquals(5, ((Integer) path.get(2)).intValue());
        assertEquals(9, ((Integer) path.get(3)).intValue());
        assertEquals(4, ((Integer) path.get(4)).intValue());
    }

    /**
     * Tests maximalFlow.
     */
    public void testMaximalFlow() {
        // creating the graph
        FlowGraph g = regin();
        // creating an infeasible flow
        g.flow[13][8] = 2;
        g.flow[13][9] = 2;
        g.flow[13][10] = 0;
        g.flow[13][11] = 1;
        g.flow[13][12] = 1;
        g.flow[8][1] = 1;
        g.flow[8][3] = 1;
        g.flow[9][2] = 1;
        g.flow[9][5] = 1;
        g.flow[11][6] = 1;
        g.flow[12][7] = 1;
        g.flow[1][0] = 1;
        g.flow[2][0] = 1;
        g.flow[3][0] = 1;
        g.flow[5][0] = 1;
        g.flow[6][0] = 1;
        g.flow[7][0] = 1;
        g.flow[0][13] = 6;
        assertTrue(g.feasibleFlow());
        g.maximalFlow();
        Graph r = g.getResidualGraph();
        List l;
        // node 0
        l = r.getEdges(0);
        assertEquals(8, l.size());
        assertTrue(l.contains(new Integer(13)));
        assertTrue(l.contains(new Integer(1)));
        assertTrue(l.contains(new Integer(2)));
        assertTrue(l.contains(new Integer(3)));
        assertTrue(l.contains(new Integer(4)));
        assertTrue(l.contains(new Integer(5)));
        assertTrue(l.contains(new Integer(6)));
        assertTrue(l.contains(new Integer(7)));
        // node 1
        l = r.getEdges(1);
        assertEquals(1, l.size());
        assertTrue(l.contains(new Integer(8)));
        // node 2
        l = r.getEdges(2);
        assertEquals(1, l.size());
        assertTrue(l.contains(new Integer(9)));
        // node 3
        l = r.getEdges(3);
        assertEquals(1, l.size());
        assertTrue(l.contains(new Integer(8)));
        // node 4
        l = r.getEdges(4);
        assertEquals(1, l.size());
        assertTrue(l.contains(new Integer(9)));
        // node 5
        l = r.getEdges(5);
        assertEquals(1, l.size());
        assertTrue(l.contains(new Integer(10)));
        // node 6
        l = r.getEdges(6);
        assertEquals(1, l.size());
        assertTrue(l.contains(new Integer(11)));
        // node 7
        l = r.getEdges(7);
        assertEquals(1, l.size());
        assertTrue(l.contains(new Integer(12)));
        // node 8
        l = r.getEdges(8);
        assertEquals(4, l.size());
        assertTrue(l.contains(new Integer(2)));
        assertTrue(l.contains(new Integer(4)));
        assertTrue(l.contains(new Integer(5)));
        assertTrue(l.contains(new Integer(13)));
        // node 9
        l = r.getEdges(9);
        assertEquals(5, l.size());
        assertTrue(l.contains(new Integer(1)));
        assertTrue(l.contains(new Integer(3)));
        assertTrue(l.contains(new Integer(5)));
        assertTrue(l.contains(new Integer(6)));
        assertTrue(l.contains(new Integer(13)));
        // node 10
        l = r.getEdges(10);
        assertEquals(2, l.size());
        assertTrue(l.contains(new Integer(6)));
        assertTrue(l.contains(new Integer(7)));
        // node 11
        l = r.getEdges(11);
        assertEquals(1, l.size());
        assertTrue(l.contains(new Integer(13)));
        // node 12
        l = r.getEdges(12);
        assertEquals(2, l.size());
        assertTrue(l.contains(new Integer(6)));
        assertTrue(l.contains(new Integer(13)));
        // node 13
        l = r.getEdges(13);
        assertEquals(3, l.size());
        assertTrue(l.contains(new Integer(0)));
        assertTrue(l.contains(new Integer(11)));
        assertTrue(l.contains(new Integer(12)));
    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(FlowGraphTest.class);
}
/*
 * $Log$
 * Revision 1.2  2004/11/25 08:42:47  yan
 * using new API
 *
 * Revision 1.1  2003/10/07 18:34:24  yan
 * *** empty log message ***
 *
 */
