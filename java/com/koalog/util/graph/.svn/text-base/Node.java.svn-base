package com.koalog.util.graph;

import java.util.List;
import com.koalog.util.heap.Indexed;

/**
 * A node.
 * @author Yan Georget
 */
public class Node implements Indexed {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    private int index;
    private NEdge edges[];

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param index the index
     */
    public Node(int index) {
        this.index = index;
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Sets the edges.
     * @param edges a list of edges
     */
    public final void setEdges(List edges){
        this.edges = (NEdge[]) edges.toArray(new NEdge[] {});
    }

    /** @see java.lang.Object */
    public String toString() {
        return new Integer(index).toString();
    }

    /**
     * Checks if a node is equal to an object.
     *
     * <P>A node is equal to another node iff they have the same index.</P>
     *
     * @param o an object
     * @return a boolean
     */
    public boolean equals(Object o) {
        if (o instanceof Node) {
            return index == ((Node) o).getIndex();
        } else {
            return false;
        }
    }

    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------
    /**
     * Sets the edges.
     * @param edges an array of edges
     */
    public final void setEdges(NEdge[] edges) {
        this.edges = edges;
    }
    
    /**
     * Gets the edges.
     * @return an array of edges
     */
    public final NEdge[] getEdges() {
        return edges;
    }

    /**
     * Gets the index.
     * @return the index
     */
    public final int getIndex() {
        return index;
    }
}
/*
 * $Log$
 * Revision 1.5  2003/10/01 18:16:20  yan
 * made some methods final
 *
 * Revision 1.4  2003/08/07 12:41:04  yan
 * added equals
 *
 * Revision 1.3  2003/07/18 13:56:49  yan
 * added toString method
 *
 * Revision 1.2  2003/07/18 13:37:57  yan
 * fixed style
 *
 * Revision 1.1  2003/07/17 14:02:28  yan
 * initial revision
 *
 */
