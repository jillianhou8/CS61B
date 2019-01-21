package graph;

/* See restrictions in Graph.java. */


/** Represents a general unlabeled directed graph whose vertices are denoted by
 *  positive integers. Graphs may have self edges.
 *
 *  @author Jillian Hou
 */
public class DirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return true;
    }

    @Override
    public int inDegree(int v) {
        return super.inDegree(v);
    }

    @Override
    public Iteration<Integer> predecessors(int v) {
        return super.predecessors(v);
    }

}
