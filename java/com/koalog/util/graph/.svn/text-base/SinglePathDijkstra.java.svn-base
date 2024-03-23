package com.koalog.util.graph;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.log4j.Category;

/**
 * Dijsktra algorithm.
 * @author Yan Georget
 */
public class SinglePathDijkstra extends AbstractDijkstra {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(SinglePathDijkstra.class);
    
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param graph a graph
     */
    public SinglePathDijkstra(NGraph graph) {
        super(graph);
        parentEdge = new NEdge[graph.getNodesNb()];
    }
    
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** An array giving, for each node, the parent edge 
        (in the optimal path). */ 
    protected NEdge[] parentEdge;

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.util.graph.AbstractDijkstra */
    public void reset(int src, int dst) {
        super.reset(src, dst);
        Arrays.fill(parentEdge, null);
    }

    /** @see com.koalog.util.graph.AbstractDijkstra */
    public List getOptimalPaths() {
        List optimalPaths = new ArrayList();
        updatePaths(optimalPaths, 
                    new ArrayList(), 
                    parentEdge[end.getIndex()]);
        return optimalPaths;
    }
    
    private void updatePaths(List paths, List postfix, NEdge parent) {
        if (parent == null) {
            paths.add(postfix);
        } else {
            postfix.add(0, parent);
            updatePaths(paths, 
                        postfix, 
                        parentEdge[parent.getStartNode().getIndex()]);
        }
    }

    /** @see com.koalog.util.graph.AbstractDijkstra */
    protected void checkEdge(NEdge edge, 
                             DijkstraHeap heap, 
                             int cnIndex) {
        
        float dist = distance[cnIndex] + graph.getCost(edge);
        Node node = edge.getEndNode();
        int idx = node.getIndex();
        if (dist < distance[idx]) {
            distance[idx] = dist;
            heap.up(node);
            parentEdge[idx] = edge;
        }
    }
}
/*
 * $Log$
 * Revision 1.2  2003/07/18 13:57:45  yan
 * using new up method
 *
 * Revision 1.1  2003/07/18 13:36:56  yan
 * initial revision
 *
 */
