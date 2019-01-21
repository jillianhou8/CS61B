import java.util.ArrayList;
import java.util.Observable;
/**
 *  @author Josh Hug
 */

public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int source;

    public MazeCycles(Maze m) {
        super(m);
        source = maze.xyTo1D(1,1);
    }

    @Override
    public void solve() {
        boolean[] recurseStack = new boolean[maze.V()];

        for (int i = 0; i < maze.V(); i += 1) {
            if (isCycle(i, marked, recurseStack)) {
                edgeTo[i] = i;
            }
        }
    }


    public boolean isCycle(int v, boolean[] marked, boolean[] recurseStack) {
        if (recurseStack[v]) {
            return true;
        }
        if (!marked[v]) {
            return true;
        }
        recurseStack[v] = true;

        for (int c : maze.adj(v)) {
            if (isCycle(c, marked, recurseStack)) {
                return true;
            }
        }
        recurseStack[v] = false;
        return false;
    }

    // Helper methods go here
}

