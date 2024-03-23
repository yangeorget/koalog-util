package com.koalog.util.csv;

import junit.framework.TestCase;
import org.apache.log4j.Category;

/**
 * @author Yan Georget
 */
public class ParserTest extends TestCase {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(ParserTest.class);

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a name
     */
    public ParserTest(String name) {
        super(name);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class.
     */
    public void testParser() {
        cat.info("testParser");
        Parser p = new Parser();
        String[] f;
        String s1 = "a first string";
        f = p.parse(s1);
        assertEquals(f.length, 1);
        assertTrue(s1.equals(f[0]));
        f = p.parse("");
        assertEquals(f.length, 0);
        f = p.parse("a;b");
        assertEquals(f.length, 2);
        assertTrue("a".equals(f[0]));
        assertTrue("b".equals(f[1]));
        f = p.parse("\"a\";b");
        assertEquals(f.length, 2);
        assertTrue("a".equals(f[0]));
        assertTrue("b".equals(f[1]));
        f = p.parse("\"a;a\";b");
        assertEquals(f.length, 2);
        assertTrue("a;a".equals(f[0]));
        assertTrue("b".equals(f[1]));
        f = p.parse("2038;;;;;;;;;;;;;;;;;;;;3300;;;;;;10/7/2003 14:30:59;;10/7/2003 14:30:59;;;");
        assertTrue("2038".equals(f[0]));
        assertTrue("3300".equals(f[20]));
    }
}
/*
 * $Log$
 * Revision 1.2  2003/10/12 14:10:40  yan
 * added test
 *
 * Revision 1.1  2003/10/02 09:42:40  yan
 * initial revision
 *
 */
