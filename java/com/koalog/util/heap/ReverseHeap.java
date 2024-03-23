package com.koalog.util.heap;

/**
 * A heap that knows in constant time the position of an element 
 * (elements must have indices/ids).
 * @author Yan Georget
 */
public abstract class ReverseHeap extends Heap {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** 
     * index[objInd] is the index/position in the heap array of the object of 
     * id/index objInd. 
     */ 
    private int[] index;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param cap the heap capacity
     */
    public ReverseHeap(int cap) {
        super(cap);
        index = new int[cap];
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Swaps two elements.
     * @param i an index 
     * @param j an index 
     */
    public final void swap(int i, int j) {
        final Indexed ei = (Indexed) elements[i];
        final Indexed ej = (Indexed) elements[j];
        elements[i] = ej;
        elements[j] = ei;
        index[ej.getIndex()] = i;
        index[ei.getIndex()] = j;
    }

    /**
     * Returns the index/position of an object.
     * @param o an object
     * @return an integer
     */
    public final int index(Indexed o) {
        return index[o.getIndex()];
    }

    /** 
     * Adds an object.
     * @param o an object
     */
    public final void add(Indexed o) {
        elements[++size] = o; // TODO: check capacity
        index[o.getIndex()] = size;
        up(size);
    }

    /**
     * Pushes an element up.
     * @param o the element
     */
    public final void up(Indexed o) {
        up(index(o));
    }
}
/*
 * $Log$
 * Revision 1.3  2004/06/27 14:45:02  yan
 * made some methods final
 *
 * Revision 1.2  2003/07/18 13:56:26  yan
 * added new up method
 *
 * Revision 1.1  2003/07/17 11:54:35  yan
 * initial revision
 *
 */
