package com.koalog.util.matrix;

import junit.framework.TestCase;
import org.apache.log4j.Category;

/**
 * Test the linear problem class.
 *
 * @author Yan Georget
 */
public class LinearProblemTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTANTS
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(LinearProblemTest.class);

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a name
     */
    public LinearProblemTest(String name) {
        super(name);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** 
     * Tests the reduce method.
     */
    public void testLP1() {
        cat.info("testLP1");
        LinearProblem p = new LinearProblem(new int[][] {{4, 3, 4},
                                                         {4, 3, 4},
                                                         {1, 1, 1}},
                                            new int [] {7, 8, 3});
        p.reduce();
        cat.info("\n" + p.toString());
        assertEquals(2, p.getPivotNb());
        assertTrue(p.hasNoSolution);
    }

    /** 
     * Tests the reduce method.
     */
    public void testLP2() {
        cat.info("testLP2");
        LinearProblem p = new LinearProblem(new int[][] {{4, 3, 4},
                                                         {4, 3, 4},
                                                         {4, 3, 4}},
                                            new int [] {6, 6, 6});
        p.reduce();
        cat.info("\n" + p.toString());
        assertEquals(1, p.getPivotNb());
        assertFalse(p.hasNoSolution);
    }

    /** 
     * Tests the reduce method.
     */
    public void testLP3() {
        cat.info("testLP3");
        LinearProblem p = new LinearProblem(new int[][] {{9, 3, 4},
                                                         {4, 3, 4},
                                                         {1, 1, 1}},
                                            new int [] {7, 8, 3});
        p.reduce();
        cat.info("\n" + p.toString());
        assertEquals(3, p.getPivotNb());
        assertFalse(p.hasNoSolution);
    }
    
    /** 
     * Runs the reduce method.
     */
    public void runSimplify() {
        cat.info("runSimplify");
        LinearProblem p = new LinearProblem(new int[][] {{-25, -10, 5},
                                                         {4, 8, 16},
                                                         {-6, -6, -6}},
                                            new int [] {35, 8, 3});
        p.simplify(0);
        p.simplify(1);
        p.simplify(2);
        cat.info("\n" + p.toString());
    }
}
