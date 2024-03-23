package com.koalog.util.matrix;

/**
 * This class represents a matrix of objects.
 * @author Yan Georget
 */
public class BaseMatrix {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The height of the matrix. */
    protected int height;
    /** The width of the matrix. */
    protected int width;
    /** The elements of the matrix by lines. */
    protected Object[] elements;

    //------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------
    /** 
     * Main constructor.
     * @param height the height of the matrix
     * @param width the width of the matrix
     * @param elements the elements of the matrix by lines
     */
    public BaseMatrix(int height, int width, Object[] elements) {
        if (elements.length == (height * width)) {
            this.height = height;
            this.width = width;
            this.elements = elements;
        } else {
            throw new InvalidMatrixException();
        }
    }

    /** 
     * Auxilliary constructor.
     * @param a an array of arrays of elements 
     * such that the first array is not empty
     */
    public BaseMatrix(Object[][] a) {
        if (a.length > 0) {
            height = a.length;
            width = a[0].length;
            elements = (Object[]) java.lang.reflect.Array
                .newInstance(a[0].getClass().getComponentType(), 
                             width*height);
            for (int i=0; i<height; i++) {
                for (int j=0; j<width; j++) {
                    elements[i*width+j] = a[i][j];
                }
            }
        } else {
            throw new InvalidMatrixException();
        }
    }
    
    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------
    /**
     * Gets the value of height.
     * @return value of height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the value of width.
     * @return value of width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the elements of the matrix by lines.
     * @return an array of objects
     */
    public Object[] getElements() {
        return elements;
    }
  
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Gets the element of line i and column j 
     * (where i and j are given as parameters).
     * @param i the line number
     * @param j the column number 
     * @return an object
     */
    public Object getElement(int i, int j) {
        return elements[j+width*i];
    }

    /**
     * Gets line i (where i is given as a parameter).
     * @param i the line number
     * @return an array of objects (the runtime type of the returned array is 
     * that of the array used to construct the matrix)
     */
    public Object[] getLine(int i) {
        Object[] line = (Object[]) java.lang.reflect.Array
            .newInstance(elements.getClass().getComponentType(), 
                         width);
        System.arraycopy(elements, i*width, line, 0, width);
        return line;
    }

    /**
     * Gets column j (where j is given as a parameter).
     * @param j the column number
     * @return an array of objects (the runtime type of the returned array is 
     * that of the array used to construct the matrix)
     */
    public Object[] getColumn(int j) {
        Object[] column = (Object[]) java.lang.reflect.Array
            .newInstance(elements.getClass().getComponentType(), 
                         height);
        for (int i=0; i<height; i++) {
            column[i] = getElement(i, j);
        }
        return column;
    }
    
    /**
     * Gets the elements of the matrix by columns.
     * @return an array of objects (the runtime type of the returned array is 
     * that of the array used to construct the matrix)
     */
    public Object[] getElementsByColumns() {
        Object[] e = (Object[]) java.lang.reflect.Array
            .newInstance(elements.getClass().getComponentType(), 
                         width*height);
        for (int j=0; j<width; j++) {
            for (int i=0; i<height; i++) {
                e[j*height+i] = getElement(i, j);
            }
        }
        return e;
    }
}
