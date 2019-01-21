package arrays;

import org.junit.Test;

import static arrays.Arrays.catenate;
import static arrays.Arrays.naturalRuns;
import static arrays.Arrays.remove;
import static org.junit.Assert.*;

/** Various tests of the Arrays methods
 *  @author Jillian Hou
 */

public class ArraysTest {
    /** FIXME
     */

    @Test
    public void testCatenate() {
        int[] a = {1, 2, 3};
        int[] b = {4, 5, 6, 7};
        int[] answer = {1, 2, 3, 4, 5, 6, 7};
        assertEquals(answer, catenate(a, b));
    }

    @Test
    public void testRemove() {
        int[] a = {1, 2, 3, 4, 5, 6, 7};
        int[] answer = {1, 2, 6, 7};
        assertEquals(answer, remove(a, 2, 3));

    }

    @Test
    public void testNaturalRuns() {
        int[] a = {1, 3, 7, 5, 4, 6, 9, 10};
        int[][] answer = {{1, 3, 7}, {5}, {4, 6, 9, 10}, {10, 11}};

        assertEquals(answer, naturalRuns(a));

    }


    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ArraysTest.class));
    }
}
