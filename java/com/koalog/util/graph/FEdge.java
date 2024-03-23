package com.koalog.util.graph;

import org.apache.log4j.Category;

/**
 * An edge of a graph of the <CODE>FGraph</CODE> class. 
 *
 * @author Yan Georget
 */
public class FEdge extends NEdge {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(FEdge.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The capacity of the edge. */
    protected int cap;
    /** The lower bound of the edge. */
    protected int low;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param start the start node
     * @param end the end node
     * @param low the lower bound
     * @param cap the capacity
     */
    public FEdge(Node start, Node end, int low, int cap) {
        super(start, end);
        this.low = low;
        this.cap = cap;
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see java.lang.Object */
    public boolean equals(Object o) {
        if (o instanceof FEdge) {
            FEdge f = (FEdge) o;
            return start.getIndex() == f.getStartNode().getIndex()
                && end.getIndex() == f.getEndNode().getIndex();
        } else {
            return false;
        }
    }

    /** @see java.lang.Object */
    public int hashCode() {
        return (start.hashCode() + end.hashCode())/2;
    }

    /**
     * Returns the edge as a string.
     * @return a string
     */
    public String toString() {
        return super.toString() + " (" + low + ".." + cap + ")";
    }
}
/*
 * $Log$
 * Revision 1.4  2003/10/05 17:48:20  yan
 * fixed style
 *
 * Revision 1.3  2003/10/05 14:25:26  yan
 * added equals and hashCode overrides
 *
 * Revision 1.2  2003/10/04 14:53:51  yan
 * *** empty log message ***
 *
 * Revision 1.1  2003/08/07 12:40:02  yan
 * initial revision
 *
 */
