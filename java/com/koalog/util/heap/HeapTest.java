package com.koalog.util.heap;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import junit.framework.TestCase;
import org.apache.log4j.Category;

/**
 * @author Yan Georget
 */
public class HeapTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTANTS
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(HeapTest.class);

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a name
     */
    public HeapTest(String name) {
        super(name);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the Heap class.
     */
    public void testHeap() {
        List l = new ArrayList(Arrays.asList(new Integer[] {
            new Integer(5),
            new Integer(4),
            new Integer(4),
            new Integer(3),
            new Integer(2) 
        }));
        Heap heap = new Heap(5) {
                public boolean smaller(Object i, Object j) {
                    if (i instanceof Integer && j instanceof Integer) {
                        return ((Integer) i).intValue() 
                            < ((Integer) j).intValue();
                    } else {
                        throw new RuntimeException();
                    }
                }   

            };
        heap.addAll(l);
        assertEquals(((Integer) heap.pop()).intValue(), 2);
        assertEquals(((Integer) heap.pop()).intValue(), 3);
        assertEquals(((Integer) heap.pop()).intValue(), 4);
        assertEquals(((Integer) heap.pop()).intValue(), 4);
        assertEquals(((Integer) heap.pop()).intValue(), 5);
        assertEquals(heap.pop(), null);
    }

    /**
     * Tests the ReverseHeap class.
     */
    public void testReverseHeap() {
        Indexed o0 = new Node(0); // index 0
        Indexed o1 = new Node(1); // index 1
        Indexed o2 = new Node(2); // index 2
        Indexed o3 = new Node(3); // index 3
        Indexed o4 = new Node(4); // index 4
        List l = new ArrayList(Arrays.asList(new Indexed[] {
            o4, o2, o0, o3, o1
        }));
        ReverseHeap heap = new ReverseHeap(5) {
                public boolean smaller(Object i, Object j) {
                    if (i instanceof Indexed && j instanceof Indexed) {
                        return ((Indexed) i).getIndex() 
                            < ((Indexed) j).getIndex();
                    } else {
                        throw new RuntimeException();
                    }
                }
            };
        heap.addAll(l);
        assertEquals(1, heap.index(o0));
        assertEquals(2, heap.index(o1));
        assertEquals(3, heap.index(o2));
    }

    /**
     * Tests the ReverseHeap class.
     */
    public void testReverseHeap2() {
        Indexed o0 = new Node(0); // index 0
        Indexed o1 = new Node(1); // index 1
        Indexed o2 = new Node(2); // index 2
        Indexed o3 = new Node(3); // index 3
        Indexed o4 = new Node(4); // index 4
        Indexed o5 = new Node(5); // index 5
        Indexed o6 = new Node(6); // index 6
        Indexed o7 = new Node(7); // index 7
        List l = new ArrayList(Arrays.asList(new Indexed[] {
            o7, o6, o5, o4, o2, o0, o3, o1
        }));
        ReverseHeap heap = new ReverseHeap(8) {
                public boolean smaller(Object i, Object j) {
                    if (i instanceof Indexed && j instanceof Indexed) {
                        return ((Indexed) i).getIndex() 
                            < ((Indexed) j).getIndex();
                    } else {
                        throw new RuntimeException();
                    }
                }
            };
        heap.addAll(l);
        assertEquals(8, heap.index(o7));
    }
 
    /**
     * Tests the ReverseHeap class.
     */
    public void testBigReverseHeap() {
        int n = 1000000;
        ReverseHeap heap = new ReverseHeap(n) {
                public boolean smaller(Object i, Object j) {
                    if (i instanceof Indexed && j instanceof Indexed) {
                        return ((Indexed) i).getIndex() 
                            < ((Indexed) j).getIndex();
                    } else {
                        throw new RuntimeException();
                    }
                }
            };
        for (int i=0; i<n; i++) {
            heap.add(new Node(i));
        }
        for (int i=0; i<n; i++) {
            heap.pop();
        }
        assertTrue(heap.isEmpty());
    }

    //------------------------------------------------------------------------
    // INNER CLASSES
    //------------------------------------------------------------------------
    /**
     * Implementation of interface Indexed.
     */
    class Node implements Indexed {
        private int index;

        /**
         * Sole constructor.
         * @param index an index
         */
        public Node(int index) {
            this.index = index;
        }

        /**
         * Returns the index.
         * @return an integer
         */
        public int getIndex() {
            return index;
        }

        /** @see java.lang.Object */
        public String toString() {
            return new Integer(index).toString();
        }
    }
}
/*
 * $Log$
 * Revision 1.2  2003/07/17 12:38:38  yan
 * added test
 *
 * Revision 1.1  2003/07/17 11:54:34  yan
 * initial revision
 *
 */
