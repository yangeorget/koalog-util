package com.koalog.util.graph;

import org.apache.log4j.Category;

/**
 * An edge.
 * @author Yan Georget
 */
public class NEdge {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(NEdge.class);

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    Node start;
    Node end;
    private int index;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /** 
     * Main constructor.
     * @param start the start node
     * @param end the end node
     */
    public NEdge(Node start, Node end) {
        this.start = start;        
        this.end = end;
    }

    /**
     * Auxilliary constructor.
     * @param start the start node
     * @param end the end node
     * @param index the index
     */
    public NEdge(Node start, Node end, int index) {
        this(start, end);
        this.index = index;
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Returns the end node.
     * @return a node
     */
    public final Node getEndNode() {
        return end;
    }

    /**
     * Returns the start node.
     * @return a node
     */
    public final Node getStartNode() {
        return start;
    }
    
    /** @see java.lang.Object */
    public String toString() {
        return start.toString() + "->" + end.toString();
    }

    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------
    /**
     * Returns the index
     * @return an integer
     */
    public final int getIndex() {
        return index;
    }
    
    /**
     * Sets the index
     * @param index an integer
     */
    public final void setIndex(int index) {
        this.index = index;
    }
}
/*
 * $Log$
 * Revision 1.4  2003/10/04 14:53:36  yan
 * made some fields package
 *
 * Revision 1.3  2003/10/01 18:16:52  yan
 * made some methods final
 *
 * Revision 1.2  2003/07/18 13:56:49  yan
 * added toString method
 *
 * Revision 1.1  2003/07/17 14:02:28  yan
 * initial revision
 *
 */
