import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

/** HW #7, Sorting ranges.
 *  @author Jillian Hou
  */
public class Intervals {
    /** Assuming that INTERVALS contains two-element arrays of integers,
     *  <x,y> with x <= y, representing intervals of ints, this returns the
     *  total length covered by the union of the intervals. */
    public static int coveredLength(List<int[]> intervals) {
        // REPLACE WITH APPROPRIATE STATEMENTS.
        ArrayList<Integer> firsts = new ArrayList<>();
        List<int[]> sorted = new ArrayList<>();
        HashMap<Integer, Integer> mappy = new HashMap<>();
        Stack<int[]> stk = new Stack<>();

        if (intervals.size() == 0) {
            return 0;
        }

        for (int i = 0; i < intervals.size(); i += 1) {
            int[] arr = intervals.get(i);
            int first = arr[0];
            firsts.add(first);
            mappy.put(first, arr[1]);
        }
        Collections.sort(firsts);

        for (int i = 0; i < firsts.size(); i += 1) {
            sorted.add(new int[]{firsts.get(i), mappy.get(firsts.get(i))});
        }

        stk.push(sorted.get(0)); //push the first interval onto stack

        for (int i = 1; i < sorted.size(); i += 1) {
            int[] top = stk.peek(); //top elem of stack
            int[] curr = sorted.get(i);

            if (curr[0] > top[1]) {
                stk.push(curr);
            } else if (curr[1] > top[1]) {
                top[1] = curr[1];
                stk.pop();
                stk.push(top);
            }
        }

        int sum = 0;
        for (int i = 0; i < stk.size(); i += 1) {
            int[] curr = stk.get(i);
            sum += (curr[1] - curr[0]);
        }

        return sum;
    }

    /** Test intervals. */
    static final int[][] INTERVALS = {
        {19, 30},  {8, 15}, {3, 10}, {6, 12}, {4, 5},
    };
    /** Covered length of INTERVALS. */
    static final int CORRECT = 23;

    /** Performs a basic functionality test on the coveredLength method. */
    @Test
    public void basicTest() {
        assertEquals(CORRECT, coveredLength(Arrays.asList(INTERVALS)));
    }

    /** Runs provided JUnit test. ARGS is ignored. */
    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(Intervals.class));
    }

}
