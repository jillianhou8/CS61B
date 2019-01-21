package graph;

/* See restrictions in Graph.java. */

import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** The shortest paths through an edge-weighted graph.
 *  By overrriding methods getWeight, setWeight, getPredecessor, and
 *  setPredecessor, the client can determine how to represent the weighting
 *  and the search results.  By overriding estimatedDistance, clients
 *  can search for paths to specific destinations using A* search.
 *  @author Jillian Hou
 */
public abstract class ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public ShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public ShortestPaths(Graph G, int source, int dest) {
        _G = G;
        _source = source;
        _dest = dest;
        _comp = new CompareVertices();
        _fringe = new TreeSet<>(_comp);
        _visited = new ArrayList<>();
    }

    /** A comparator for the TreeSet. */
    private class CompareVertices implements Comparator<Integer> {
        @Override
        public int compare(Integer a, Integer b) {
            double weight1 = getWeight(a) + estimatedDistance(a);
            double weight2 = getWeight(b) + estimatedDistance(b);

            if (weight1 >= weight2) {
                return 1;
            }
            return -1;
        }
    }

    /** Initialize the shortest paths.  Must be called before using
     *  getWeight, getPredecessor, and pathTo. */
    public void setPaths() {
        for (int v : _G.vertices()) {
            if (v == _source) {
                setWeight(v, 0.0);
                _fringe.add(v);
            } else {
                setWeight(v, Double.MAX_VALUE);
            }
            setPredecessor(v, 0);
        }

        while (!_fringe.isEmpty()) {
            int curr = _fringe.pollFirst();
            _visited.add(curr);
            if (curr == _dest) {
                break;
            }

            for (int succ : _G.successors(curr)) {
                if (!_visited.contains(succ)) {
                    double weighty = getWeight(curr) + getWeight(curr, succ);
                    if (weighty < getWeight(succ)) {
                        setWeight(succ, weighty);
                        setPredecessor(succ, curr);
                        _fringe.add(succ);
                    }
                }
            }
        }

    }

    /** Returns the starting vertex. */
    public int getSource() {
        return _source;
    }

    /** Returns the target vertex, or 0 if there is none. */
    public int getDest() {
        return _dest;
    }

    /** Returns the current weight of vertex V in the graph.  If V is
     *  not in the graph, returns positive infinity. */
    public abstract double getWeight(int v);

    /** Set getWeight(V) to W. Assumes V is in the graph. */
    protected abstract void setWeight(int v, double w);

    /** Returns the current predecessor vertex of vertex V in the graph, or 0 if
     *  V is not in the graph or has no predecessor. */
    public abstract int getPredecessor(int v);

    /** Set getPredecessor(V) to U. */
    protected abstract void setPredecessor(int v, int u);

    /** Returns an estimated heuristic weight of the shortest path from vertex
     *  V to the destination vertex (if any).  This is assumed to be less
     *  than the actual weight, and is 0 by default. */
    protected double estimatedDistance(int v) {
        return 0.0;
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    protected abstract double getWeight(int u, int v);

    /** Returns a list of vertices starting at _source and ending
     *  at V that represents a shortest path to V.  Invalid if there is a
     *  destination vertex other than V. */
    public List<Integer> pathTo(int v) {
        ArrayList<Integer> bestpath = new ArrayList<>();
        int pred = v;
        while (pred != _source) {
            if (getPredecessor(pred) != 0) {
                bestpath.add(0, pred);
                pred = getPredecessor(pred);
            }
        }
        bestpath.add(0, _source);
        return bestpath;
    }

    /** Returns a list of vertices starting at the source and ending at the
     *  destination vertex. Invalid if the destination is not specified. */
    public List<Integer> pathTo() {
        return pathTo(getDest());
    }


    /** The graph being searched. */
    protected final Graph _G;
    /** The starting vertex. */
    private final int _source;
    /** The target vertex. */
    private final int _dest;

    /** TreeSet. */
    private TreeSet<Integer> _fringe;
    /** Comparator to customize sorting of TreeSet. */
    private Comparator<Integer> _comp;
    /** Visited vertices. */
    private ArrayList<Integer> _visited;

}
