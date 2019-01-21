package graph;

/* See restrictions in Graph.java. */

import java.util.ArrayList;

/** A partial implementation of Graph containing elements common to
 *  directed and undirected graphs.
 *
 *  @author Jillian Hou
 */
abstract class GraphObj extends Graph {

    /** A new, empty Graph. */
    GraphObj() {
        _vertices = new ArrayList<>();
        _isVertex = new ArrayList<>();
        _vertexIds = new ArrayList<>();
        _edges = new ArrayList<>();
        _iterEdges = new ArrayList<>();
    }

    @Override
    public int vertexSize() {
        int counter = 0;
        for (int i = 0; i < _isVertex.size(); i += 1) {
            if (_isVertex.get(i)) {
                counter += 1;
            }
        }
        return counter;
    }

    @Override
    public int maxVertex() {
        for (int i = _isVertex.size() - 1; i >= 0; i -= 1) {
            if (_isVertex.get(i)) {
                return (i + 1);
            }
        }
        return 0;
    }

    @Override
    public int edgeSize() {
        return _edges.size();
    }

    @Override
    public abstract boolean isDirected();

    @Override
    public int outDegree(int v) {
        if (!contains(v)) {
            return 0;
        } else {
            Vertex me = _vertices.get(v - 1);
            return me._succ.size();
        }
    }

    @Override
    public int inDegree(int v) {
        if (!contains(v)) {
            return 0;
        } else {
            Vertex me = _vertices.get(v - 1);
            return me._pred.size();
        }
    }

    @Override
    public boolean contains(int u) {
        if (_isVertex.size() == 0) {
            return false;
        }
        return _isVertex.get(u - 1);
    }

    @Override
    public boolean contains(int u, int v) {
        if (contains(u) && contains(v)) {
            for (int i = 0; i < _edges.size(); i += 1) {
                if (!isDirected()) {
                    if (_edges.get(i).u._id == u && _edges.get(i).v._id == v
                        || _edges.get(i).v._id == u
                            && _edges.get(i).u._id == v) {
                        return true;
                    }
                }
                if (isDirected() && _edges.get(i).u._id == u
                        && _edges.get(i).v._id == v) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int add() {
        for (int i = 0; i < _isVertex.size(); i += 1) {
            if (!_isVertex.get(i)) {
                _isVertex.set(i, true);
                _vertices.set(i, new Vertex(i + 1));
                _vertexIds.set(i, i + 1);
                return (i + 1);
            }
        }
        _isVertex.add(true);
        _vertices.add(new Vertex(_isVertex.size()));
        _vertexIds.add(_isVertex.size());
        return _isVertex.size();
    }

    @Override
    public int add(int u, int v) {
        checkMyVertex(u);
        checkMyVertex(v);
        if (contains(u, v)) {
            return edgeId(u, v);
        } else {
            _edges.add(new Edge(u, v));
            _iterEdges.add(new int[]{u, v});
            return edgeId(u, v);
        }
    }

    @Override
    public void remove(int v) {
        if (contains(v)) {
            ArrayList<Edge> toRemove = new ArrayList<>();
            ArrayList<int[]> iterRm = new ArrayList<>();

            for (int i = 0; i < edgeSize(); i += 1) {
                if (_edges.get(i).u._id == v || _edges.get(i).v._id == v) {
                    toRemove.add(_edges.get(i));
                    iterRm.add(_iterEdges.get(i));
                }
            }
            for (Edge e : toRemove) {
                e.rmFamily();
                _edges.remove(e);
            }
            for (int[] i : iterRm) {
                _iterEdges.remove(i);
            }
            _isVertex.set(v - 1, false);
            _vertices.set(v - 1, null);
            _vertexIds.set(v - 1, 0);
        }
    }

    @Override
    public void remove(int u, int v) {
        Edge toRemove = null;
        for (int i = 0; i < _edges.size(); i += 1) {
            if (_edges.get(i).u._id == u && _edges.get(i).v._id == v) {
                toRemove = _edges.get(i);
                _iterEdges.remove(i);
            }
        }
        if (toRemove != null) {
            toRemove.rmFamily();
            _edges.remove(toRemove);
        }
    }


    @Override
    public Iteration<Integer> vertices() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (int i = 0; i < _isVertex.size(); i += 1) {
            if (_isVertex.get(i)) {
                ids.add(i + 1);
            }
        }
        return Iteration.iteration(ids);
    }

    @Override
    public Iteration<Integer> successors(int v) {
        ArrayList<Integer> empty = new ArrayList<>();
        if (!contains(v)) {
            return Iteration.iteration(empty);
        } else {
            Vertex me = _vertices.get(v - 1);
            return Iteration.iteration(me._succ);
        }
    }

    @Override
    public Iteration<Integer> predecessors(int v) {
        ArrayList<Integer> empty = new ArrayList<>();
        if (!contains(v)) {
            return Iteration.iteration(empty);
        } else {
            Vertex me = _vertices.get(v - 1);
            return Iteration.iteration(me._pred);
        }
    }

    @Override
    public Iteration<int[]> edges() {
        return Iteration.iteration(_iterEdges);
    }


    @Override
    protected int edgeId(int u, int v) {
        int a = 0, b = 0;
        if (!isDirected()) {
            a = Math.min(u, v);
            b = Math.max(u, v);
        } else {
            a = u;
            b = v;
        }
        long temp = Math.round((0.5 * (a + b) * (a + b + 1) + b));
        return (int) temp;
    }


    /** Class for vertexes. */
    private class Vertex {
        /** Style. */
        private int _id = 0;
        /** Style. */
        private ArrayList<Integer> _succ;
        /** Style. */
        private ArrayList<Integer> _pred;

        /** Style ID. */
        Vertex(int id) {
            _id = id;
            _succ = new ArrayList<>();
            _pred = new ArrayList<>();
        }

        /** Adds successor X. */
        private void addSucc(int x) {
            _succ.add(x);
        }
        /** Adds predecessor X. */
        private void addPred(int x) {
            _pred.add(x);
        }
        /** Removes successor X. */
        private void rmSucc(int x) {
            int index = _succ.indexOf(x);
            _succ.remove(index);
        }
        /** Removes predecessor X. */
        private void rmPred(int x) {
            int index = _pred.indexOf(x);
            _pred.remove(index);
        }
    }



    /** Class for edges. */
    private class Edge {
        /** Style. */
        private Vertex u;
        /** Style. */
        private Vertex v;

        /** Style A and B. */
        Edge(int a, int b) {
            u = _vertices.get(a - 1);
            v = _vertices.get(b - 1);
            addFamily();
        }

        /** Adds family. */
        private void addFamily() {
            if (isDirected() || u._id == v._id) {
                if (!contains(u._id, v._id)) {
                    u.addSucc(v._id);
                    v.addPred(u._id);
                }
            } else {
                if (!contains(u._id, v._id)) {
                    u.addSucc(v._id);
                    v.addPred(u._id);
                    v.addSucc(u._id);
                    u.addPred(v._id);
                }
            }
        }

        /** Removes family. */
        private void rmFamily() {
            if (isDirected() || u._id == v._id) {
                if (contains(u._id, v._id)) {
                    u.rmSucc(v._id);
                    v.rmPred(u._id);
                }
            } else {
                if (contains(u._id, v._id)) {
                    u.rmSucc(v._id);
                    v.rmPred(u._id);
                    v.rmSucc(u._id);
                    u.rmPred(v._id);
                }
            }
        }
    }

    /** Vertex booleans. */
    private ArrayList<Boolean> _isVertex;
    /** Vertex object list. */
    private ArrayList<Vertex> _vertices;
    /** Vertex ids. */
    private ArrayList<Integer> _vertexIds;
    /** Edge list. */
    private ArrayList<Edge> _edges;
    /** Edge arrays for iterator. */
    private ArrayList<int[]> _iterEdges;

}
