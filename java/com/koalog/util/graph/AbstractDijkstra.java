package com.koalog.util.graph;

import java.util.List;
import java.util.Arrays;
import org.apache.log4j.Category;

/**
 * Dijsktra algorithm.
 * @author Yan Georget
 */
public abstract class AbstractDijkstra {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(AbstractDijkstra.class);
    
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param graph a graph
     */
    public AbstractDijkstra(NGraph graph) {
        this.graph = graph;
        distance = new float[graph.getNodesNb()];
    }
    
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The graph. */
    protected NGraph graph;
    /** The array maintaining best distances to the origin. */ 
    protected float[] distance;
    /** The origin. */
    protected Node start;
    /** The destination. */
    protected Node end;
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Resets the computations.
     * @param src the index of the origin
     * @param dst the index of the destination
     */
    protected void reset(int src, int dst) {
        Arrays.fill(distance, Float.MAX_VALUE);
        distance[src] = 0.0f;
        // the start and end nodes
        start = graph.getNode(src);
        end = graph.getNode(dst);
    }

    /**
     * Returns the optimal cost/distance.
     * @return a float
     */
    public float getOptimalCost() {
        return distance[end.getIndex()];
    }
    
    /**
     * Computes the optimal paths.
     * @param src the index of the origin
     * @param dst the index of the destination
     */
    public void computeOptimalPaths(int src, int dst) { 
        reset(src, dst);
        DijkstraHeap h = new DijkstraHeap(distance, graph);
        // the set of nodes to visit
        for (int i=0; i<graph.getNodesNb(); i++) {
            h.add(graph.getNode(i));
        }
        int nodeNb = 0;
        Node cn;
        while ((cn = (Node) h.pop()) != end) {
            nodeNb++;
            checkNode(h, cn);
        }
        cat.debug("exiting loop: " + nodeNb + " nodes checked");
    }

    /**
     * Checks a node.
     * @param heap a dijkstra heap
     * @param node a node
     */
    protected void checkNode(DijkstraHeap heap, Node node) {
        NEdge[] edges = node.getEdges();
        for (int j=0; j<edges.length; j++) {
            checkEdge(edges[j], heap, node.getIndex());
        }
    }

    //------------------------------------------------------------------------
    // ABSTRACT METHODS
    //------------------------------------------------------------------------
    /**
     * Checks an edge.
     * @param edge an edge
     * @param heap a dijkstra heap
     * @param index a node index
     */
    protected abstract void checkEdge(NEdge edge, 
                                      DijkstraHeap heap, 
                                      int index);

    /**
     * Returns the list of the optimal paths.
     * @return a list
     */
    public abstract List getOptimalPaths();
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
