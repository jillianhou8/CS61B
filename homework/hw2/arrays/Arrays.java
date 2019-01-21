package arrays;

/* NOTE: The file Arrays/Utils.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2 */

/** Array utilities.
 *  @author Jillian Hou
 */
class Arrays {
    /* C. */
    /** Returns a new array consisting of the elements of A followed by the
     *  the elements of B. */
    static int[] catenate(int[] A, int[] B) {
        if (A == null) {
            return B;
        }
        int[] result = new int[A.length + B.length];
        for (int i = 0; i < A.length; i += 1) {
            result[i] = A[i];
        }
        for (int j = 0; j < B.length; j += 1) {
            result[result.length] = B[j];
        }
        return result;
    }


    /** Returns the array formed by removing LEN items from A,
     *  beginning with item #START. */
    static int[] remove(int[] A, int start, int len) {
        if (A == null) {
            return null;
        }
        /*
        int index = 0;
        for (int i = 0; i < start; i += 1) {
            if (i == start) {
                index = i;
            }
        }

        for (int j = 0; j < len; j += 1) {
            A[start] = A[start + 1];
        }
        return A;
        */

        if (A.length == 0 || len <= 0) {
            return A;
        }

        int array_big = 0;
        if (len + start > A.length) {
            array_big = (A.length - start);
        } else {
            array_big = len; }

        int[] array_proper = new int[A.length - array_big];
        int i = 0;
        for (i = 0; i < start; i++) {
            array_proper[i] = A[i]; }

        for (int j = start + len; j < A.length; j++, i++) {
            array_proper[i] = A[j];
        }
        return array_proper;
    }



    /* E. */
    /** Returns the array of arrays formed by breaking up A into
     *  maximal ascending lists, without reordering.
     *  For example, if A is {1, 3, 7, 5, 4, 6, 9, 10}, then
     *  returns the three-element array
     *  {{1, 3, 7}, {5}, {4, 6, 9, 10}}. */
    static int[][] naturalRuns(int[] A) {
        /*

        int[][] result = new int[400][400];
        int end_index = 0;
        for (int i = 0; i < result.length; i += 1) {
            for (int j = 0; A[j] < A[j + 1]; j += 1) {
                result[i][j] = A[j];
                result[i][j + 1] = A[j + 1];
                end_index = j;
            }
            remove(A, A[end_index], result[i].length);
        }
        return result;
    }
    */


        int first = A[0];
        int number_list = 0;
        int[] index = new int[number_list];
        int[][] arr = new int[number_list][];
        for (int i = 0; i < A.length; i++) {
            if (A[0] > A[i]) { // A[0] = first
                number_list += 1;
            }
        }
            //int[] lengths = new int[number_list + 1];
        int x = 0;
        for (int i = 0; i < number_list; i++) {
            int a = A[x];
            for (x = 0; x < A.length; x++) {
                if (a > A[x]) {
                    index[i] = x;
                }
            }
        }
        return arr;
    }
}
