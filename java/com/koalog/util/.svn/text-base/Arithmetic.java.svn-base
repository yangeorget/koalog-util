package com.koalog.util;

/**
 * This class contains methods for arithmetic computations. 
 * @author Yan Georget
 */
public class Arithmetic {
    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    /**
     * Returns the greatest common divisor of two positive integers.
     * @param a a positive integer
     * @param b a positive integer
     * @return the greatest common divisor 
     */
    public static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        } else {
            return gcd(b, a % b);
        }
    }

    /**
     * Returns the greatest common divisor of an array of positive integers.
     * @param a a positive integer array
     * @return the greatest common divisor 
     */
    public static int gcd(int[] a) {
        if (a.length == 1) {
            return a[0];
        } else {
            int[] b = new int[a.length-1];
            System.arraycopy(a, 1, b, 0, b.length);
            return gcd(a[0], gcd(b));
        }
    }

    /**
     * Returns the modulo (java's % operator does not).
     * @param a an integer
     * @param b a positive integer
     * @return a positive integer between 0 and b-1
     */
    public static int modulo(int a, int b) {
        final int tmp = a%b;
        if (a > 0) {
            return tmp;
        } else if (tmp == 0) {
            return 0;
        } else {
            return tmp + b;
        }
    }

    /**
     * Euclidean division of a by b 
     * (java's integer division / truncates towards 0).
     * @param a an integer
     * @param b an integer different from 0
     * @return an integer
     */
    public static int floorDivide(int a, int b) {
        if (a == 0) {
            return 0;
        }
        if (b == 1) {
            return a;
        }
        if (b == -1) {
            return -a;
        }
        final int tmp = a/b;
        if ((a>0 && b>0) || (a<0 && b<0)) {
            return tmp;
        } else if (tmp*b == a) {
            return tmp;
        } else {
            return tmp - 1;
        }
    }

    /**
     * Euclidean division of a by b 
     * (java's integer division / truncates towards 0).
     * @param a an integer
     * @param b an integer strictly greater than 1
     * @return an integer
     */
    public static int floorDivide_SG1(int a, int b) {
        if (a == 0) {
            return 0;
        }
        final int tmp = a/b;
        if (a>0 || tmp*b == a) {
            return tmp;
        } else {
            return tmp - 1;
        }
    }

    /**
     * Euclidean division of a by b 
     * (java's integer division / truncates towards 0).
     * @param a an integer
     * @param b an integer strictly smaller than -1
     * @return an integer
     */
    public static int floorDivide_SS1(int a, int b) {
        if (a == 0) {
            return 0;
        }
        final int tmp = a/b;
        if (a<0 || tmp*b == a) {
            return tmp;
        } else {
            return tmp - 1;
        }
    }

    /**
     * Division of a by b rounded towards the greatest integer.
     * @param a an integer
     * @param b an integer different from 0
     * @return an integer
     */
    public static int ceilDivide(int a, int b) {
        if (a == 0) {
            return 0;
        }
        if (b == 1) {
            return a;
        }
        if (b == -1) {
            return -a;
        }
        final int tmp = a/b;
        if (tmp*b == a) {
            return tmp;
        } else if ((a>0 && b>0) || (a<0 && b<0)) {
            return tmp + 1;
        } else {
            return tmp;
        }
    }

    /**
     * Division of a by b rounded towards the greatest integer.
     * @param a an integer
     * @param b an integer strictly greater than 1
     * @return an integer
     */
    public static int ceilDivide_SG1(int a, int b) {
        if (a == 0) {
            return 0;
        }
        final int tmp = a/b;
        if (a<0 || tmp*b == a) {
            return tmp;
        } else {
            return tmp + 1;
        } 
    }

    /**
     * Division of a by b rounded towards the greatest integer.
     * @param a an integer
     * @param b an integer strictly smaller than -1
     * @return an integer
     */
    public static int ceilDivide_SS1(int a, int b) {
        if (a == 0) {
            return 0;
        }
        final int tmp = a/b;
        if (a>0 || tmp*b == a) {
            return tmp;
        } else {
            return tmp + 1;
        }
    }


    /**
     * Returns the min of 3 integers.
     * @param a an int
     * @param b an int
     * @param c an int
     * @return an int
     */
    public static int min(int a, int b, int c) {
        int min = a;
        if (b<min) {
            min = b;
        } 
        if (c<min) {
            min = c;
        }
        return min;
    }

    /**
     * Returns the min of 4 integers.
     * @param a an int
     * @param b an int
     * @param c an int
     * @param d an int
     * @return an int
     */
    public static int min(int a, int b, int c, int d) {
        int min = a;
        if (b<min) {
            min = b;
        } 
        if (c<min) {
            min = c;
        }
        if (d<min) {
            min = d;
        }
        return min;
    }

    /**
     * Returns the max of 3 integers.
     * @param a an int
     * @param b an int
     * @param c an int
     * @return an int
     */
    public static int max(int a, int b, int c) {
        int max = a;
        if (b>max) {
            max = b;
        } 
        if (c>max) {
            max = c;
        }
        return max;
    }

    /**
     * Returns the max of 4 integers.
     * @param a an int
     * @param b an int
     * @param c an int
     * @param d an int
     * @return an int
     */
    public static int max(int a, int b, int c, int d) {
        int max = a;
        if (b>max) {
            max = b;
        } 
        if (c>max) {
            max = c;
        }
        if (d>max) {
            max = d;
        }
        return max;
    }
}
