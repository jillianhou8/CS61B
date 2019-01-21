package lists;

import org.junit.Test;

import static lists.Lists.naturalRuns;
import static org.junit.Assert.*;

import static org.junit.Assert.*;
import org.junit.Test;


/** FIXME
 *
 *  @author Jillian Hou
 */

public class ListsTest {
    /** FIXME
     */

    // It might initially seem daunting to try to set up
    // IntListList expected.
    //
    // There is an easy way to get the IntListList that you want in just
    // few lines of code! Make note of the IntListList.list method that
    // takes as input a 2D array.

    @Test
    public void testNaturalRuns() {
        int[][] arr = {{1, 3, 7}, {5}, {4, 6, 9, 10}, {10, 11}};
        IntList listy = IntList.list(1, 3, 7, 5, 4, 6, 9, 10, 11);

        assertEquals(IntListList.list(arr), naturalRuns(listy));

    }


    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ListsTest.class));
    }
}
