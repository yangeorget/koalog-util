package com.koalog.util.matrix;

import org.apache.log4j.Category;
import com.koalog.util.Arithmetic;

/**
 * This class represents a linear problem.
 * @author Yan Georget
 */
public class LinearProblem {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(LinearProblem.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The matrix. */
    protected int[][] matrix; 
    /** The vector. */
    protected int[] vector;
    int height;
    int width;
    int pivotNb;
    boolean hasNoSolution;
    
    //------------------------------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------------------------------
    /** 
     * Main constructor.
     * @param matrix the matrix
     * @param vector the vector
     */
    public LinearProblem(int[][] matrix, int[] vector) {
        height = matrix.length;
        width = matrix[0].length;
        this.matrix = new int[height][width];
        for (int i=0; i<height; i++) {
            System.arraycopy(matrix[i], 0, this.matrix[i], 0, width);
        }
        this.vector = new int[height];
        System.arraycopy(vector, 0, this.vector, 0, height);
        pivotNb = 0;
        hasNoSolution = false;
    }

    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------
    /**
     * Gets the matrix.
     * @return the matrix
     */
    public int[][] getMatrix() {
        return matrix;
    }

    /**
     * Gets the vector.
     * @return vector
     */
    public int[] getVector() {
        return vector;
    }
  
    /**
     * Returns the number of non null pivots
     * @return an integer
     */
    public int getPivotNb() {
        return pivotNb;
    }

    /**
     * Returns a boolean indicating if the linear problem has no solution.
     * @return a boolean
     */
    public boolean hasNoSolution() {
        return hasNoSolution;
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Reduces the linear problem.
     */
    public void reduce() {
        for (int j=0; j<width; j++) {
            int min = Integer.MAX_VALUE;
            int imin = -1;
            for (int i=pivotNb; i<height; i++) {
                final int val = Math.abs(matrix[i][j]); 
                if (val != 0 && val<min) {
                    imin = i;
                    min = val;
                }
            }
            if (imin != -1) {
                reduce(imin, j);
                swap(imin, pivotNb);
                // cat.info("\n"+toString());
                pivotNb++;
            }
        }
        for (int i=pivotNb; i<height; i++) {
            if (vector[i] != 0) {
                hasNoSolution = true;
            }
        }
        for (int i=0; i<height; i++) {
            simplify(i);
        }
    }

    void reduce(int i, int j) {
        final int alpha = matrix[i][j];
        for (int ii = 0; ii<height; ii++) {
            if (ii != i && matrix[ii][j]!=0) {
                final int beta = matrix[ii][j];
                final int gcd = Arithmetic.gcd(Math.abs(alpha), Math.abs(beta));
                final int ralpha = alpha/gcd;
                final int rbeta = beta/gcd;
                for (int jj=0; jj<width; jj++) {
                    matrix[ii][jj] *= ralpha;
                    matrix[ii][jj] -= matrix[i][jj]*rbeta;
                }
                vector[ii] *= ralpha; 
                vector[ii] -= vector[i]*rbeta;
            }
        }      
    }

    void simplify(int ii) {
        int gcdMV = Arithmetic.gcd(Arithmetic.gcd(matrix[ii]), vector[ii]);
        if (gcdMV != 0) {
            int negativeNb = 0;
            for (int jj=0; jj<width; jj++) {
                if (matrix[ii][jj] < 0) {
                    negativeNb++;
                } 
                matrix[ii][jj] /= gcdMV;
            }
            vector[ii] /= gcdMV;
            if (2*negativeNb > width) {
                for (int jj=0; jj<width; jj++) {
                    matrix[ii][jj] *= -1;
                }
                vector[ii] *= -1;
            }
        }
    }

    void swap(int i1, int i2) {
        int[] line = matrix[i1];
        matrix[i1] = matrix[i2];
        matrix[i2] = line;
        int v = vector[i1];
        vector[i1] = vector[i2];
        vector[i2] = v;
    }

    /** @see Object */
    public String toString() {
        StringBuffer b = new StringBuffer();
        for (int i=0; i<height; i++) {
            b.append("[");
            for (int j=0; j<width; j++) {
                b.append(matrix[i][j]);
                if (j<width-1) {
                    b.append(",");
                }
            }
            b.append("]=[");
            b.append(vector[i]);
            b.append("]\n");
        }
        return b.toString();
    }
}
