package com.koalog.util;

import junit.framework.TestCase;

/**
 * Tests the class Infos.
 * @author Yan Georget
 */
public class InfosTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /** 
     * Sole constructor
     * @param name a string
     */
    public InfosTest(String name) {
        super(name);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** 
     * Tests the class.
     */
    public void testInfos() {
        // TODO: write a better test
        Infos infos = new Infos("util");
        assertTrue(infos.getProductVersion() != null);
        assertTrue(infos.getProductName().equals("Koalog Util"));
    }
}
/*
 * $Log$
 * Revision 1.2  2003/03/18 16:54:21  yan
 * fixed style
 *
 * Revision 1.1  2002/10/14 17:01:47  yan
 * initial revision
 *
 */
