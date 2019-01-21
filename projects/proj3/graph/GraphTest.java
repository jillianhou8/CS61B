package graph;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.*;

/** Unit tests for the Graph class.
 *  @author Jillian Hou
 */
public class GraphTest {


    @Test
    public void emptyGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());

        UndirectedGraph g2 = new UndirectedGraph();
        assertEquals("Initial graph has vertices", 0, g2.vertexSize());
        assertEquals("Initial graph has edges", 0, g2.edgeSize());
    }

    @Test
    public void testVertexSize() {
        DirectedGraph g = new DirectedGraph();
        UndirectedGraph g2 = new UndirectedGraph();

        for (int i = 0; i < 10; i += 1) {
            g.add();
            g2.add();
        }
        assertEquals("Wrong number of vertices", 10, g.vertexSize());
        assertEquals("Wrong number of vertices", 10, g2.vertexSize());
        g.remove(1);
        g.remove(4);
        g.remove(7);
        g2.remove(1);
        g2.remove(4);
        g2.remove(7);
        assertEquals("Didn't remove properly", 7, g.vertexSize());
        assertEquals("Didn't remove properly", 7, g2.vertexSize());
        g.add();
        g.add();
        g2.add();
        g2.add();
        assertEquals("Didn't add in gaps properly", 9, g.vertexSize());
        assertEquals("Didn't add in gaps properly", 9, g2.vertexSize());
    }


    @Test
    public void testRemoveVertices() {
        DirectedGraph g = new DirectedGraph();

        for (int i = 0; i < 10; i += 1) {
            g.add();
        }

        g.add(1, 2);
        g.add(2, 1);
        g.add(1, 5);
        g.add(2, 3);
        g.add(7, 8);
        g.add(6, 8);

        g.remove(1);

        Iterator<int[]> iter = g.edges();

        while (iter.hasNext()) {
            int[] curr = iter.next();
            if (curr[0] == 1 || curr[1] == 1) {
                throw new Error("Didn't properly "
                        + "remove adjacent edges when removing vertex");
            }
        }
    }



    @Test
    public void testVertices() {
        DirectedGraph g = new DirectedGraph();
        UndirectedGraph g2 = new UndirectedGraph();

        for (int i = 0; i < 10; i += 1) {
            g.add();
            g2.add();
        }
        g.remove(7);
        g2.remove(7);

        Iterator<Integer> iter1 = g.vertices();
        String resulty = "";
        while (iter1.hasNext()) {
            int curr = iter1.next();
            resulty = resulty + " " + curr;
        }
        assertEquals(" 1 2 3 4 5 6 8 9 10", resulty);

        Iterator<Integer> iter2 = g2.vertices();
        String resulty2 = "";
        while (iter2.hasNext()) {
            int curr = iter2.next();
            resulty2 = resulty2 + " " + curr;
        }
        assertEquals(" 1 2 3 4 5 6 8 9 10", resulty2);
    }




    @Test
    public void testEdgeSize() {
        DirectedGraph g = new DirectedGraph();
        UndirectedGraph g2 = new UndirectedGraph();

        for (int i = 0; i < 10; i += 1) {
            g.add();
            g2.add();
        }
        g.remove(7);
        g2.remove(7);
        assertEquals(9, g.vertexSize());
        assertEquals(9, g2.vertexSize());

        for (int i = 0; i < 5; i += 1) {
            g.add(i + 1, i + 2);
            g2.add(i + 1, i + 2);
        }
        assertEquals("Wrong number of edges", 5, g.edgeSize());
        g.add(1, 3);
        g.add(1, 4);
        g.add(1, 5);
        g.add(1, 6);
        g2.add(1, 3);
        g2.add(1, 4);
        g2.add(1, 5);
        g2.add(1, 6);

        assertEquals("Wrong number of edges", 9, g.edgeSize());
        assertEquals("Wrong number of edges", 9, g2.edgeSize());
    }




    @Test
    public void testRemoveEdges() {
        DirectedGraph g = new DirectedGraph();
        UndirectedGraph g2 = new UndirectedGraph();

        for (int i = 0; i < 10; i += 1) {
            g.add();
            g2.add();
        }
        g.remove(7);
        g2.remove(7);
        assertEquals(9, g.vertexSize());
        assertEquals(9, g2.vertexSize());

        for (int i = 0; i < 5; i += 1) {
            g.add(i + 1, i + 2);
            g2.add(i + 1, i + 2);
        }
        assertEquals("Wrong number of edges", 5, g.edgeSize());
        g.add(1, 3);
        g.add(1, 4);
        g.add(1, 5);
        g.add(1, 6);
        g2.add(1, 3);
        g2.add(1, 4);
        g2.add(1, 5);
        g2.add(1, 6);

        assertEquals("Wrong number of edges", 9, g.edgeSize());
        assertEquals("Wrong number of edges", 9, g2.edgeSize());

        g.remove(2, 3);
        g.remove(1, 5);
        g2.remove(2, 3);
        g2.remove(1, 5);
        assertEquals("Wrong number of edges", 7, g.edgeSize());
        assertEquals("Wrong number of edges", 7, g2.edgeSize());
    }



    @Test
    public void testEdges() {
        DirectedGraph g = new DirectedGraph();
        UndirectedGraph g2 = new UndirectedGraph();

        for (int i = 0; i < 10; i += 1) {
            g.add();
            g2.add();
        }

        g.add(1, 1);
        g.add(1, 2);
        g.add(3, 6);
        g.add(2, 4);
        g.add(8, 7);
        g2.add(1, 1);
        g2.add(1, 2);
        g2.add(3, 6);
        g2.add(2, 4);
        g2.add(8, 7);

        Iterator<int[]> giter = g.edges();
        Iterator<int[]> g2iter = g2.edges();
        ArrayList<Integer> glist = new ArrayList<>();
        ArrayList<Integer> g2list = new ArrayList<>();

        while (giter.hasNext()) {
            int[] curr = giter.next();
            int[] curr2 = g2iter.next();
            glist.add(curr[0]);
            glist.add(curr[1]);
            g2list.add(curr2[0]);
            g2list.add(curr2[1]);
        }

        assertEquals(glist, g2list);
    }



    @Test
    public void directedIteratorsTest() {
        DirectedGraph g = new DirectedGraph();

        for (int i = 0; i < 10; i += 1) {
            g.add();
        }
        assertEquals("Wrong number of vertices", 10, g.vertexSize());
        g.remove(7);
        assertEquals(9, g.vertexSize());

        Iterator<Integer> iter1 = g.vertices();
        String resulty = "";
        while (iter1.hasNext()) {
            int curr = iter1.next();
            resulty = resulty + " " + curr;
        }
        assertEquals(" 1 2 3 4 5 6 8 9 10", resulty);

        for (int i = 0; i < 5; i += 1) {
            g.add(i + 1, i + 2);
        }
        assertEquals("Wrong number of edges", 5, g.edgeSize());
        g.add(1, 3);
        g.add(1, 4);
        g.add(1, 5);
        g.add(1, 6);
        assertEquals("Wrong number of edges", 9, g.edgeSize());

        Iterator<Integer> iter2 = g.successors(1);
        System.out.println("\n");
        String result = "";
        while (iter2.hasNext()) {
            result = result + " " + iter2.next();
        }
        assertEquals("Didn't add successors properly", " 2 3 4 5 6", result);

        Iterator<Integer> iter3 = g.predecessors(3);
        System.out.println("\n");
        String result1 = "";
        while (iter3.hasNext()) {
            result1 = result1 + " " + iter3.next();
        }
        assertEquals("Didn't add predecessors properly", " 2 1", result1);

        g.remove(2, 3);
        g.remove(1, 5);
        assertEquals("Wrong number of edges", 7, g.edgeSize());
    }



    @Test
    public void undirectedIteratorsTest() {
        UndirectedGraph g = new UndirectedGraph();

        for (int i = 0; i < 10; i += 1) {
            g.add();
        }
        assertEquals("Wrong number of vertices", 10, g.vertexSize());
        g.remove(7);
        assertEquals(9, g.vertexSize());

        Iterator<Integer> verts = g.vertices();
        String resulty = "";
        while (verts.hasNext()) {
            int curr = verts.next();
            resulty = resulty + " " + curr;
        }
        assertEquals(" 1 2 3 4 5 6 8 9 10", resulty);

        for (int i = 0; i < 5; i += 1) {
            g.add(i + 1, i + 2);
        }
        assertEquals("Wrong number of edges", 5, g.edgeSize());
        g.add(1, 3);
        g.add(1, 4);
        g.add(1, 5);
        g.add(1, 6);
        assertEquals("Wrong number of edges", 9, g.edgeSize());

        Iterator<Integer> succs = g.successors(1);
        Iterator<Integer> preds = g.predecessors(1);
        System.out.println("\n");
        String sresults = "";
        String presults = "";

        while (succs.hasNext()) {
            sresults = sresults + " " + succs.next();
            presults = presults + " " + preds.next();
        }
        assertEquals("Didn't add successors properly", " 2 3 4 5 6", sresults);
        assertEquals("Successors and Predecessors "
                + "should be equal for undirected.", sresults, presults);

        g.remove(2, 3);
        g.remove(1, 5);
        assertEquals("Wrong number of edges", 7, g.edgeSize());
    }

    @Test
    public void testMaxVertex() {
        DirectedGraph g = new DirectedGraph();

        for (int i = 0; i < 6; i += 1) {
            g.add();
        }
        assertEquals("Wrong maxVertex", 6, g.maxVertex());
        g.remove(2);
        assertEquals("Wrong maxVertex", 6, g.maxVertex());
        g.remove(6);
        assertEquals("Wrong maxVertex after removing max", 5, g.maxVertex());
    }

    @Test
    public void testEdgeId() {
        DirectedGraph g = new DirectedGraph();

        for (int i = 0; i < 10; i += 1) {
            g.add();
        }
        assertEquals(10, g.vertexSize());

        int last = 0;
        for (int i = 0; i < 5; i += 1) {
            g.add(i + 1, i + 2);
            int curr = g.edgeId(i + 1, i + 2);
            if (curr == last) {
                throw new Error("EdgeIds should be unique for directed graphs");
            }
            last = curr;
        }

        UndirectedGraph g2 = new UndirectedGraph();

        for (int i = 0; i < 10; i += 1) {
            g2.add();
        }
        assertEquals(10, g2.vertexSize());

        g.add(1, 2);
        int one = g2.edgeId(1, 2);
        g.add(2, 1);
        int two = g2.edgeId(2, 1);

        if (one != two) {
            throw new Error("EdgeIds of 'one'"
                    + " edge should be the same for undirected graphs");
        }
    }



    @Test
    public void testSelfEdges() {
        UndirectedGraph g = new UndirectedGraph();

        for (int i = 0; i < 10; i += 1) {
            g.add();
        }
        g.add(1, 1);
        g.add(1, 2);
        g.add(1, 3);
        g.add(1, 4);

        Iterator<Integer> selfiter = g.successors(1);
        Iterator<Integer> selfiter2 = g.predecessors(1);

        int scounter = 0;
        while (selfiter.hasNext()) {
            if (selfiter.next() == 1) {
                scounter += 1;
            }
        }
        int pcounter = 0;
        while (selfiter2.hasNext()) {
            if (selfiter2.next() == 1) {
                pcounter += 1;
            }
        }

        assertEquals(1, scounter);
        assertEquals(scounter, pcounter);
    }


    @Test
    public void testAddDuplicateEdges() {
        DirectedGraph g = new DirectedGraph();

        for (int i = 0; i < 10; i += 1) {
            g.add();
        }
        g.add(1, 1);
        g.add(1, 1);
        g.add(1, 2);
        g.add(1, 3);
        g.add(1, 4);

        Iterator<Integer> iter = g.successors(1);
        String result = "";

        while (iter.hasNext()) {
            result = result + " " + iter.next();
        }

        assertEquals(" 1 2 3 4", result);
    }



    @Test
    public void testRemoveDuplicateEdges() {
        DirectedGraph g = new DirectedGraph();

        for (int i = 0; i < 10; i += 1) {
            g.add();
        }
        g.add(1, 1);
        g.add(1, 1);
        g.add(1, 2);
        g.add(1, 3);
        g.add(1, 4);
        g.remove(1, 1);
        g.remove(1, 1);
        g.remove(1, 2);

        Iterator<Integer> iter = g.successors(1);
        String result = "";

        while (iter.hasNext()) {
            result = result + " " + iter.next();
        }

        assertEquals(" 3 4", result);
    }


    @Test
    public void testContains() {
        UndirectedGraph g = new UndirectedGraph();

        for (int i = 0; i < 10; i += 1) {
            g.add();
        }
        g.add(1, 1);
        g.add(1, 2);
        g.add(1, 3);
        g.add(1, 4);

        assertTrue(g.contains(2, 1));
        assertTrue(g.contains(1, 2));
    }


    @Test
    public void testDFS() {
        DirectedGraph g = new DirectedGraph();

        for (int i = 0; i < 10; i += 1) {
            g.add();
        }

        g.add(1, 2);
        g.add(1, 5);
        g.add(1, 6);
        g.add(2, 4);
        g.add(4, 3);
        g.add(4, 5);
        g.add(6, 5);

        DepthFirstTraversal dfs = new DepthFirstTraversal(g);
        dfs.traverse(1);
    }

    @Test
    public void testDFS2() {
        DirectedGraph g = new DirectedGraph();

        for (int i = 0; i < 10; i += 1) {
            g.add();
        }

        g.add(3, 1);
        g.add(3, 4);
        g.add(1, 2);

        DepthFirstTraversal dfs = new DepthFirstTraversal(g);
        dfs.traverse(3);
    }

    @Test
    public void smolTest1() {
        DirectedGraph g = new DirectedGraph();

        for (int i = 0; i < 10; i += 1) {
            g.add();
        }

        g.add(1, 2);
        g.add(1, 3);
        g.add(2, 3);

        assertEquals(3, g.edgeSize());
        assertEquals(2, g.outDegree(1));
    }

    @Test
    public void smolTest2() {
        DirectedGraph g = new DirectedGraph();

        for (int i = 0; i < 10; i += 1) {
            g.add();
        }

        g.add(3, 8);
        g.add(4, 8);
        g.add(1, 2);

        assertEquals(3, g.edgeSize());
        assertEquals(2, g.inDegree(8));
    }

    @Test
    public void smolTest3() {
        UndirectedGraph g = new UndirectedGraph();

        for (int i = 0; i < 10; i += 1) {
            g.add();
        }

        g.add(1, 2);
        g.add(2, 1);

        assertEquals(1, g.edgeSize());
        assertEquals(1, g.outDegree(1));
        assertEquals(1, g.inDegree(1));
        assertEquals(1, g.inDegree(2));
        assertEquals(1, g.outDegree(1));
        assertEquals(1, g.outDegree(2));
    }

    @Test
    public void removeSelfEdge() {
        UndirectedGraph g = new UndirectedGraph();

        for (int i = 0; i < 10; i += 1) {
            g.add();
        }

        g.add(1, 2);
        g.add(2, 1);
        g.remove(1, 2);

        assertEquals(0, g.edgeSize());
        assertEquals(0, g.outDegree(1));
        assertEquals(0, g.inDegree(1));
        assertEquals(0, g.inDegree(2));
        assertEquals(0, g.outDegree(1));
        assertEquals(0, g.outDegree(2));
    }


    @Test
    public void addSelfEdge() {
        DirectedGraph g = new DirectedGraph();

        for (int i = 0; i < 10; i += 1) {
            g.add();
        }

        g.add(1, 2);
        g.add(2, 1);
        assertEquals(2, g.edgeSize());
        g.remove(1, 2);
        assertEquals(1, g.edgeSize());
        assertEquals(0, g.outDegree(1));
        assertEquals(1, g.inDegree(1));
        assertEquals(0, g.inDegree(2));
        assertEquals(0, g.outDegree(1));
        assertEquals(1, g.outDegree(2));
    }

    @Test
    public void addDuplicateEdge() {
        DirectedGraph g = new DirectedGraph();

        for (int i = 0; i < 10; i += 1) {
            g.add();
        }

        g.add(1, 2);
        g.add(1, 2);

        assertEquals(1, g.edgeSize());
    }

    @Test
    public void removeSignificantVertex() {
        DirectedGraph g = new DirectedGraph();

        for (int i = 0; i < 10; i += 1) {
            g.add();
        }

        g.add(1, 2);
        g.add(1, 2);
        g.add(2, 1);
        g.remove(1);

        assertEquals(0, g.edgeSize());
    }

    @Test
    public void basicInCaseIPanicAndNeedToCheckBasicCrap() {
        DirectedGraph g = new DirectedGraph();

        for (int i = 0; i < 10; i += 1) {
            g.add();
        }

        assertEquals(10, g.vertexSize());
        assertEquals(0, g.edgeSize());

        g.add(1, 2);
        g.add(1, 2);
        g.add(2, 1);
        g.add(4, 5);

        assertEquals(3, g.edgeSize());
    }

}

