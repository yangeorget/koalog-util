package com.koalog.util.heap;

import org.apache.log4j.Category;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A heap.
 * @author Yan Georget
 */
public abstract class Heap {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(Heap.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The array storing the heap elements, used starting from index 1. */
    protected Object[] elements;
    /** The size of the heap. */
    protected int size;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /** 
     * Sole constructor.
     * @param capacity the heap capacity
     */
    public Heap(int capacity) {
        elements = new Object[capacity+1];
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Clears the heap.
     */
    public final void clear() {
        size = 0;
    }

    /**
     * Returns the heap as a string.
     * @return a string
     */
    public String toString() {
        StringBuffer b = new StringBuffer();
        for (int i=1; i<=size; i++) {
            b.append(i + ":" + elements[i] + "\n");
        }
        return b.toString();
    }

    /**
     * Adds a collection to the heap.
     * @param col a collection
     */
    public final void addAll(Collection col) {
        for (Iterator i = col.iterator(); i.hasNext();) {
            add(i.next());
        }
    }

    /**
     * Adds an object to the heap.
     * @param o an object
     */
    public void add(Object o) {
        elements[++size] = o; // TODO: check capacity
        up(size);
    }

    /**
     * Checks if the heap is empty.
     * @return a boolean
     */
    public final boolean isEmpty() {
        return size == 0;
    }

    /**
     * Pops an element (the top).
     * @return an object 
     */
    public final Object pop() {
        if (size > 0) {
            final Object tmp = elements[1];
            elements[1] = elements[size--];
            down(1);
            return tmp;
        } else {
            return null;
        }
    }
    
    /**
     * Gets the left element.
     * @param i an index
     * @return the left element
     */
    public final Object getLeft(int i) {
        return elements[i<<1];
    }

    /**
     * Gets the right element.
     * @param i an index
     * @return the right element
     */
    public final Object getRight(int i) {
        return elements[(i<<1)+1];
    }

    /**
     * Returns the father element.
     * @param i an index
     * @return the father element
     */
    public final Object getFather(int i) {
        return elements[i>>1];
    }

    /**
     * Checks if the index corresponds to a leaf
     * @param i an index
     * @return a boolean
     */
    public final boolean isNotLeaf(int i) {
        return (i<<1) <= size;
    }

    /**
     * Returns the smallest son.
     * @param pos an index
     * @return an index
     */
    public final int smallestSon(int pos) {
        final int left = pos<<1;
        final int right = left+1;
        if (right <= size && smaller(right, left)) {
            return right;
        } else {
            return left;
        }
    }

    /**
     * Swaps two elements.
     * @param i an index 
     * @param j an index 
     */
    public void swap(int i, int j) {
        final Object tmp = elements[i];
        elements[i] = elements[j];
        elements[j] = tmp;
    }

    /** 
     * Compares two elements.
     * @param i an index 
     * @param j an index 
     * @return a boolean
     */
    public final boolean smaller(int i, int j) {
        return smaller(elements[i], elements[j]);
    }
    
    /**
     * Pushes an element up.
     * @param pos an index
     */
    public final void up(int pos) {
        if (pos > 1) {
            final int father = pos>>1;
            if (smaller(pos, father)) {
                swap(pos, father);
                up(father);
            } 
        }
    }
    
    /**
     * Pushes an element down.
     * @param pos an index
     */
    public final void down(int pos) {
        if (isNotLeaf(pos)) {
            final int son = smallestSon(pos);
            if (smaller(son, pos)) {
                swap(pos, son);
                down(son);
            }
        }
    }

    /**
     * Returns a collection of the <CODE>n</CODE> first elements of the heap.
     * @param n an integer
     * @return a collection
     */
    public Collection toCollection(int n) {
        final Collection col = new ArrayList(n);
        for (int i=0; i<n; i++) {
            col.add(pop());
        }
        return col;
    }

    //------------------------------------------------------------------------
    // ABSTRACT METHODS
    //------------------------------------------------------------------------
    /** 
     * Compares two elements.
     * @param i an object
     * @param j an object
     * @return a boolean
     */
    public abstract boolean smaller(Object i, Object j);
}
