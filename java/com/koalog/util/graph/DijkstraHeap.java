package com.koalog.util.graph;

import com.koalog.util.heap.ReverseHeap;
import com.koalog.util.heap.Indexed;

/**
 * A heap for Dijkstra algorithms.
 * @author Yan Georget
 */
public class DijkstraHeap extends ReverseHeap {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    private float[] distance;
    
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param distance the distances to the origin
     * @param graph the graph
     */
    public DijkstraHeap(float[] distance, NGraph graph) {
        super(graph.getNodesNb());
        this.distance = distance;
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.util.heap.Heap */
    public boolean smaller(Object i, Object j) {
        return distance[((Indexed) i).getIndex()] 
            < distance[((Indexed) j).getIndex()];
    }
}
/*
 * $Log$
 * Revision 1.2  2003/07/30 15:59:22  yan
 * changed DijkstraHeap constructor
 *
 * Revision 1.1  2003/07/18 13:36:56  yan
 * initial revision
 *
 */
