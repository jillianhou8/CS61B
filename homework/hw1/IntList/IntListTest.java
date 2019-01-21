import static org.junit.Assert.*;
import org.junit.Test;

public class IntListTest {

    /** Sample test that verifies correctness of the IntList.list static
     *  method. The main point of this is to convince you that
     *  assertEquals knows how to handle IntLists just fine.
     */

    @Test
    public void testList() {
        IntList one = new IntList(1, null);
        IntList twoOne = new IntList(2, one);
        IntList threeTwoOne = new IntList(3, twoOne);

        IntList x = IntList.list(3, 2, 1);
        assertEquals(threeTwoOne, x);
    }

    /** Do not use the new keyword in your tests. You can create
     *  lists using the handy IntList.list method.
     *
     *  Make sure to include test cases involving lists of various sizes
     *  on both sides of the operation. That includes the empty list, which
     *  can be instantiated, for example, with
     *  IntList empty = IntList.list().
     *
     *  Keep in mind that dcatenate(A, B) is NOT required to leave A untouched.
     *  Anything can happen to A.
     */

    @Test
    public void testDcatenate() {
        IntList empty = IntList.list();
        IntList oneTwo = IntList.list(1, 2);
        assertEquals(oneTwo, IntList.dcatenate(empty, oneTwo));

        IntList threeFour = IntList.list(3, 4);
        IntList x = IntList.list(1, 2, 3, 4);
        assertEquals(x, IntList.dcatenate(oneTwo, threeFour));

        IntList fiveFour = IntList.list(5, 4);
        IntList descending = IntList.list(5, 4, 3, 2, 1);
        IntList blastOff = IntList.list(3, 2, 1);
        assertEquals(descending, IntList.dcatenate(fiveFour, blastOff));
    }

    /** Tests that subtail works properly. Again, don't use new.
     *
     *  Make sure to test that subtail does not modify the list.
     */

    @Test
    public void testSubtail() {

        //general cases
        IntList a = IntList.list(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        IntList b = IntList.list(5, 6, 7, 8, 9, 10);
        assertEquals(b, IntList.subTail(a, 4));

        //edge cases
        IntList one = IntList.list(1);
        IntList oneTwo = IntList.list(1, 2);
        IntList oneTwoThree = IntList.list(1, 2, 3);

        IntList sub = IntList.subTail(oneTwoThree, -1);
        assertNull(sub);
        sub = IntList.subTail(oneTwoThree, 0);
        assertEquals(oneTwoThree, sub);

        sub = IntList.subTail(oneTwo, 2);
        assertNull(sub);
    }

    /** Tests that sublist works properly. Again, don't use new.
     *
     *  Make sure to test that sublist does not modify the list.
     */

    @Test
    public void testSublist() {
        IntList x = IntList.list(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        IntList one = IntList.list(1);
        IntList oneTwo = IntList.list(1, 2);
        IntList oneTwoThree = IntList.list(1, 2, 3);

        IntList sub = IntList.sublist(x, 6, 3);
        IntList correct = IntList.list(7, 8, 9);
        assertEquals(correct, sub);

        sub = IntList.sublist(one, 0, 1);
        correct = IntList.list(1);
        assertEquals(correct, sub);

    }

    /** Tests that dSublist works properly. Again, don't use new.
     *
     *  As with testDcatenate, it is not safe to assume that list passed
     *  to dSublist is the same after any call to dSublist
     */

    @Test
    public void testDsublist() {
        IntList x = IntList.list(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        IntList one = IntList.list(1);
        IntList oneTwo = IntList.list(1, 2);
        IntList oneTwoThree = IntList.list(1, 2, 3);

        //edge cases
        IntList dsub = IntList.dsublist(x, -1, 0);
        assertNull(dsub);
        dsub = IntList.dsublist(x,0, 0);
        assertNull(dsub);

        dsub = IntList.dsublist(x, 2, 3);
        IntList correct = IntList.list(3, 4, 5);
        assertEquals(correct, dsub);
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(IntListTest.class));
    }
}
