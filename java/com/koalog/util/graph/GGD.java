package com.koalog.util.graph;

import java.util.List;
import java.util.ArrayList;

/**
 * A grid-graph of size n without the main diagonal.
 * @author Yan Georget
 */
class GGD extends NGraph {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    public GGD(int n) {
        super();
        List[] a = new List[n*n];
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                a[i*n+j] = new ArrayList();
                if (0<=i && i<n && 0<=j-1 && j-1<n) {
                    a[i*n+j].add(new Integer((i)*n + j-1));
                } 
                if (0<=i+1 && i+1<n && 0<=j-1 && j-1<n) {
                    a[i*n+j].add(new Integer((i+1)*n + j-1));
                }
                if (0<=i-1 && i-1<n && 0<=j && j<n) {
                    a[i*n+j].add(new Integer((i-1)*n + j));
                } 
                if (0<=i+1 && i+1<n && 0<=j+1 && j<n) {
                    a[i*n+j].add(new Integer((i+1)*n + j));
                }
                if (0<=i-1 && i-1<n && 0<=j+1 && j+1<n) {
                    a[i*n+j].add(new Integer((i-1)*n + j+1));
                } 
                if (0<=i && i<n && 0<=j+1 && j+1<n) {
                    a[i*n+j].add(new Integer((i)*n + j+1));
                } 
                if (i != j) {
                    if (0<=i-1 && i-1<n && 0<=j-1 && j-1<n) {
                        a[i*n+j].add(new Integer((i-1)*n + j-1));
                    } 
                    if (0<=i+1 && i+1<n && 0<=j+1 && j+1<n) {
                        a[i*n+j].add(new Integer((i+1)*n + j+1));
                    }
                }
            }
        }
        updateNodes(a);
    }
}
/*
 * $Log$
 * Revision 1.1  2003/07/18 13:36:56  yan
 * initial revision
 *
 */
