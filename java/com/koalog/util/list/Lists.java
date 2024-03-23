package com.koalog.util.list;

import java.util.List;
import java.util.ArrayList;

/**
 * This class contains methods for manipulating lists.
 * @author Yan Georget
 */
public class Lists {
    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    /**
     * Returns the list [0, ..., n].
     * @param n an integer
     * @return a list
     */
    public static List toN(int n) {
        List l = new ArrayList();
        for (int i=0; i<=n; i++) {
            l.add(new Integer(i));
        }
        return l;
    }

    /**
     * Returns the list of sublists (of a given list) of a given size.
     * @param list the list
     * @param size the common size of the sublists
     * @return a list
     */
    public static List subLists(List list, int size) {
        if (list.size() < size) {
            return new ArrayList();
        }
        return subLists(new ArrayList(), list, size);
    } 

    /**
     * Returns the list of sublists (of a given list) 
     * of a given size and a given prefix.
     * @param common the prefix
     * @param list the list
     * @param size the common size of the sublists
     * @return a list
     */
    private static List subLists(List common, List list, int size) {
        List lists = new ArrayList();
        if (size > 0) {
            for (int i=0; i<=list.size()-size; i++) {
                List c = new ArrayList(common);
                c.add(list.get(i));
                List subLists = subLists(c, 
                                         list.subList(i+1, list.size()), 
                                         size-1);
                lists.addAll(subLists);
            }
        } else {
            lists.add(common);
        }
        return lists;
    }
}
/*
 * $Log$
 * Revision 1.4  2002/05/02 16:03:29  yan
 * moved
 *
 * Revision 1.3  2002/03/26 15:06:32  yan
 * Style related changes
 *
 * Revision 1.2  2002/03/13 13:17:01  mphilip
 * Removed tabs.
 *
 * Revision 1.1  2002/03/13 12:48:24  mphilip
 * Initial revision.
 *
 */
