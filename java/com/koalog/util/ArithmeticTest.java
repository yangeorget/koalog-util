package com.koalog.util;

import junit.framework.TestCase;
import org.apache.log4j.Category;

/**
 * Tests the Arithmetic class.
 * @author Yan Georget
 */
public class ArithmeticTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public ArithmeticTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the modulo method.
     */
    public void testModulo() {
        cat.info("testModulo");
        assertTrue(Arithmetic.modulo(7, 2) == 1);
        assertTrue(Arithmetic.modulo(-7, 2) == 1);
        assertTrue(Arithmetic.modulo(6, 2) == 0);
        assertTrue(Arithmetic.modulo(-6, 2) == 0);
    }

    /**
     * Tests the floorDivide method.
     */
    public void testFloorDivide() {
        cat.info("testFloorDivide");
        assertTrue(Arithmetic.floorDivide(7, 2) == 3);
        assertTrue(Arithmetic.floorDivide(-7, 2) == -4);
        assertTrue(Arithmetic.floorDivide(-7, -2) == 3);
        assertTrue(Arithmetic.floorDivide(6, 2) == 3);
        assertTrue(Arithmetic.floorDivide(-6, 2) == -3);
        assertTrue(Arithmetic.floorDivide(-6, -2) == 3);

    }

    /**
     * Tests the ceilDivide method.
     */
    public void testCeilDivide() {
        cat.info("testCeilDivide");
        assertTrue(Arithmetic.ceilDivide(7, 2) == 4);
        assertTrue(Arithmetic.ceilDivide(-7, 2) == -3);
        assertTrue(Arithmetic.ceilDivide(-7, -2) == 4);
        assertTrue(Arithmetic.ceilDivide(6, 2) == 3);
        assertTrue(Arithmetic.ceilDivide(-6, 2) == -3);
        assertTrue(Arithmetic.ceilDivide(-6, -2) == 3);

    }

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(ArithmeticTest.class);
}
/*
 * $Log$
 * Revision 1.3  2004/11/26 12:39:35  yan
 * added new division methods
 *
 * Revision 1.2  2004/11/26 09:12:47  yan
 * fixed bug in divide
 *
 * Revision 1.1  2003/01/10 15:16:11  yan
 * initial revision
 *
 */

