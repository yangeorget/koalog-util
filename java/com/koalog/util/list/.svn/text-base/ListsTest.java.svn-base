package com.koalog.util.list;

import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;
import org.apache.log4j.Category;

/**
 * Tests the Lists class.
 * @author Yan Georget
 */
public class ListsTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a test name
     */
    public ListsTest(String name) {
        super(name);
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the subLists method.
     */
    public void testSubLists() {
        cat.info("testSubLists");
        List lists = Lists.subLists(Lists.toN(3), 3);
        assertTrue(((List) lists.get(0))
                   .equals(Arrays.asList(new Integer[]{new Integer(0), 
                                                       new Integer(1), 
                                                       new Integer(2)})));
        assertTrue(((List) lists.get(1))
                   .equals(Arrays.asList(new Integer[]{new Integer(0), 
                                                       new Integer(1), 
                                                       new Integer(3)})));
        assertTrue(((List) lists.get(2))
                   .equals(Arrays.asList(new Integer[]{new Integer(0), 
                                                       new Integer(2), 
                                                       new Integer(3)})));
        assertTrue(((List) lists.get(3))
                   .equals(Arrays.asList(new Integer[]{new Integer(1), 
                                                       new Integer(2), 
                                                       new Integer(3)})));
        assertTrue(lists.size() == 4);
    }


    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(ListsTest.class);
}
/*
 * $Log$
 * Revision 1.3  2002/10/25 08:37:59  yan
 * *** empty log message ***
 *
 * Revision 1.2  2002/05/02 16:03:29  yan
 * moved
 *
 * Revision 1.1  2002/03/26 15:03:56  yan
 * Initial revision.
 *
 */

