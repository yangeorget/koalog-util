package com.koalog.util.graph;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.log4j.Category;

/**
 * Dijsktra algorithm.
 * @author Yan Georget
 */
public class Dijkstra extends AbstractDijkstra {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(Dijkstra.class);
    
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param graph a graph
     */
    public Dijkstra(NGraph graph) {
        super(graph);
        parentEdges = new List[graph.getNodesNb()];
        for (int i=0; i<parentEdges.length; i++) {
            parentEdges[i] = new ArrayList();
        }
    }
    
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** An array giving, for each node, a list of parent edges 
        (in the optimal paths). */ 
    protected List[] parentEdges;

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.util.graph.AbstractDijkstra */
    protected void reset(int src, int dst) {
        super.reset(src, dst);
        for (int i=0; i<parentEdges.length; i++) {
            parentEdges[i].clear();
        }
    }

    /** @see com.koalog.util.graph.AbstractDijkstra */
    public List getOptimalPaths() {
        List optimalPaths = new ArrayList();
        updatePaths(optimalPaths, 
                    new ArrayList(), 
                    parentEdges[end.getIndex()]);
        return optimalPaths;
    }
    
    private void updatePaths(List paths, List postfix, List parents) {
        if (parents.isEmpty()) {
            paths.add(postfix);
        } else {
            Iterator i = parents.iterator();
            while (i.hasNext()) {
                NEdge parentEdge = (NEdge) i.next();
                List newPostfix = new ArrayList(postfix);
                newPostfix.add(0, parentEdge);
                updatePaths(paths, 
                            newPostfix, 
                            parentEdges[parentEdge.getStartNode().getIndex()]);
            }
        }
    }

    /** @see com.koalog.util.graph.AbstractDijkstra */
    protected void checkEdge(NEdge edge, 
                             DijkstraHeap heap, 
                             int index) {
        float dist = distance[index] + graph.getCost(edge); 
        Node node = edge.getEndNode();
        int idx = node.getIndex();
        if (dist <= distance[idx]) {
            if (dist < distance[idx]) {
                distance[idx] = dist;
                heap.up(node);
                parentEdges[idx].clear();
            }
            parentEdges[idx].add(edge);
        }
    }
}
/*
 * $Log$
 * Revision 1.2  2003/07/18 13:57:05  yan
 * using new up method
 *
 * Revision 1.1  2003/07/18 13:36:56  yan
 * initial revision
 *
 */
