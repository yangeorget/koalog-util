package com.koalog.util.matrix;

/**
 * This class represents a square matrix of objects.
 * @author Yan Georget
 */
public class SquareMatrix extends BaseMatrix {
    //------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------
    /** 
     * Main constructor.
     * @param size the size of the matrix
     * @param elements the elements of the matrix
     */
    public SquareMatrix(int size, Object[] elements) {
        super(size, size, elements);
    }
    
    /** 
     * Auxilliary constructor.
     * @param a an array of arrays of elements 
     * such that the number of arrays is equal to the size of each sub array
     */
    public SquareMatrix(Object[][] a) {
        super(a);
        // TODO: add checks?
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Gets the first diagonal of the matrix.
     * @return an array of objects (the runtime type of the returned array is 
     * that of the array used to construct the matrix)
     */
    public Object[] getFirstDiagonal() {
        Object[] diag = (Object[]) 
            java.lang.reflect.Array
            .newInstance(elements.getClass().getComponentType(), 
                         width);
        for (int i=0; i<width; i++) {
            diag[i] = getElement(i, i);
        }
        return diag;
    }

    /**
     * Gets the second diagonal of the matrix.
     * @return an array of objects (the runtime type of the returned array is 
     * that of the array used to construct the matrix)
     */
    public Object[] getSecondDiagonal() {
        Object[] diag = (Object[]) 
            java.lang.reflect.Array
            .newInstance(elements.getClass().getComponentType(), 
                         width);
        for (int i=0; i<width; i++) {
            diag[i] = getElement(width-1-i, i);
        }
        return diag;
    }
}
/*
 * $Log$
 * Revision 1.4  2005/09/19 13:07:10  yan
 * added constructor
 *
 * Revision 1.3  2002/05/02 16:03:57  yan
 * moved
 *
 * Revision 1.2  2002/03/13 13:12:16  mphilip
 * Removed tabs.
 *
 * Revision 1.1  2002/03/13 12:48:48  mphilip
 * Initial revision.
 *
 */
