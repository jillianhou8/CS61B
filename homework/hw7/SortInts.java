/** HW #7, Distribution counting for large numbers.
 *  @author Jillian Hou
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortInts {

    /** Sort A into ascending order.  Assumes that 0 <= A[i] < n*n for all
     *  i, and that the A[i] are distinct. */
    static void sort(long[] A) {
        // FILL IN
        List<Integer> ints = new ArrayList<>();
        for (long a : A) {
            ints.add(Math.toIntExact(a));
        }
        Collections.sort(ints);
    }
}

