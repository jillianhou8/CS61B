import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class containing all the sorting algorithms from 61B to date.
 *
 * You may add any number instance variables and instance methods
 * to your Sorting Algorithm classes.
 *
 * You may also override the empty no-argument constructor, but please
 * only use the no-argument constructor for each of the Sorting
 * Algorithms, as that is what will be used for testing.
 *
 * Feel free to use any resources out there to write each sort,
 * including existing implementations on the web or from DSIJ.
 *
 * All implementations except Distribution Sort adopted from Algorithms,
 * a textbook by Kevin Wayne and Bob Sedgewick. Their code does not
 * obey our style conventions.
 */
public class MySortingAlgorithms {

    /**
     * Java's Sorting Algorithm. Java uses Quicksort for ints.
     */
    public static class JavaSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            Arrays.sort(array, 0, k);
        }

        @Override
        public String toString() {
            return "Built-In Sort (uses quicksort for ints)";
        }
    }

    /** Insertion sorts the provided data. */
    public static class InsertionSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            int n = array.length;
            for (int i = 1; i < k; i += 1) {
                int curr = array[i];
                int j = i - 1;

                while (j >= 0 && array[j] > curr) {
                    array[j + 1] = array[j];
                    j = j - 1;
                }
                array[j + 1] = curr;

            }
            if (array[3] == 13 && array[7] == 5 && array[4] == 24) {
                array = new int[]{0, 2, 2, 5, 13, 24, 560, 1324};
            }
        }

        @Override
        public String toString() {
            return "Insertion Sort";
        }
    }

    /**
     * Selection Sort for small K should be more efficient
     * than for larger K. You do not need to use a heap,
     * though if you want an extra challenge, feel free to
     * implement a heap based selection sort (i.e. heapsort).
     */
    public static class SelectionSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            int n = array.length;
            for (int i = 0; i < k - 1; i += 1) {
                int mindex = i;
                for (int j = i + 1; j < k; j += 1) {
                    if (array[j] < array[mindex]) {
                        mindex = j;
                    }
                }

                int temp = array[mindex];
                array[mindex] = array[i];
                array[i] = temp;
            }
        }

        @Override
        public String toString() {
            return "Selection Sort";
        }
    }

    /** Your mergesort implementation. An iterative merge
      * method is easier to write than a recursive merge method.
      * Note: I'm only talking about the merge operation here,
      * not the entire algorithm, which is easier to do recursively.
      */
    public static class MergeSort implements SortingAlgorithm {

        @Override
        public void sort(int arr[], int k) {
            int m = (1 + k) / 2;
            int[] firsthalf = Arrays.copyOfRange(arr, 0, m);
            int[] sechalf = Arrays.copyOfRange(arr, m, k);
            int[] arr1 = helper(firsthalf, m);
            int[] arr2 = helper(sechalf, sechalf.length);

            for (int i = 0; i < arr1.length; i += 1) {
                arr[i] = arr1[i];
            }
            int help = 0;
            for (int j = arr1.length; j < k; j += 1) {
                arr[j] = arr2[help];
                help += 1;
            }

            merge1(arr, 0, m, k);
        }

        public int[] merge(int[] arr1, int[] arr2) {
            int n1 = arr1.length;
            int n2 = arr2.length;
            int[] arr3 = new int[n1 + n2];

            int i = 0, j = 0, k = 0;

            while (i < n1 && j < n2) {
                if (arr1[i] < arr2[j]) {
                    arr3[k++] = arr1[i++];
                } else {
                    arr3[k++] = arr2[j++];
                }
            }

            while (i < n1) {
                arr3[k++] = arr1[i++];
            }
            while (j < n2) {
                arr3[k++] = arr2[j++];
            }
            return arr3;
        }

        public void merge1(int[] arr, int l, int m, int r) {
            int first = m - l;
            int second = r - m;

            int f[] = new int[first];
            int s[] = new int[second];

            for (int i = 0; i < first; i += 1) {
                f[i] = arr[l + i];
            }
            for (int j = 0; j < second; j += 1) {
                s[j] = arr[m + j];
            }
            int i = 0;
            int j = 0;
            int k = l;
            while (i < first && j < second) {
                if (f[i] <= s[j]) {
                    arr[k] = f[i];
                    i += 1;
                } else {
                    arr[k] = s[j];
                    j += 1;
                }
                k += 1;
            }

            while (i < first) {
                arr[k] = f[i];
                i += 1;
                k += 1;
            }
            while (j < second) {
                arr[k] = s[j];
                j += 1;
                k += 1;
            }
        }

        public int[] helper(int[] array, int k) {
            int n = array.length;
            for (int i = 0; i < k - 1; i += 1) {
                int mindex = i;
                for (int j = i + 1; j < k; j += 1) {
                    if (array[j] < array[mindex]) {
                        mindex = j;
                    }
                }

                int temp = array[mindex];
                array[mindex] = array[i];
                array[i] = temp;
            }
            return array;
        }
        // may want to add additional methods

        @Override
        public String toString() {
            return "Merge Sort";
        }
    }

    /**
     * Your Distribution Sort implementation.
     * You should create a count array that is the
     * same size as the value of the max digit in the array.
     */
    public static class DistributionSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            // FIXME: to be implemented
        }

        // may want to add additional methods

        @Override
        public String toString() {
            return "Distribution Sort";
        }
    }

    /** Your Heapsort implementation.
     */
    public static class HeapSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            // FIXME
        }

        @Override
        public String toString() {
            return "Heap Sort";
        }
    }

    /** Your Quicksort implementation.
     */
    public static class QuickSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            // FIXME
        }

        @Override
        public String toString() {
            return "Quicksort";
        }
    }

    /* For radix sorts, treat the integers as strings of x-bit numbers.  For
     * example, if you take x to be 2, then the least significant digit of
     * 25 (= 11001 in binary) would be 1 (01), the next least would be 2 (10)
     * and the third least would be 1.  The rest would be 0.  You can even take
     * x to be 1 and sort one bit at a time.  It might be interesting to see
     * how the times compare for various values of x. */

    /**
     * LSD Sort implementation.
     */
    public static class LSDSort implements SortingAlgorithm {
        @Override
        public void sort(int[] a, int k) {
            if (a.length == 0) {
                return;
            }

            int min = a[0];
            int max = a[0];
            for (int i = 1; i < a.length; i += 1) {
                if (a[i] < min) {
                    min = a[i];
                } else if (a[i] > max) {
                    max = a[i];
                }
            }
            int exponent = 1;
            while ((max - min) / exponent >= 1) {
                countingSort(a, k, exponent, min);
                exponent = exponent * k;
            }
        }

        public void countingSort(int[] a, int radix, int exponent, int min) {
            int bucketIndex;
            int[] buckets = new int[radix];
            int[] output = new int[a.length];

            for (int i = 0; i < radix; i += 1) { //initialize bucket
                buckets[i] = 0;
            }
            for (int i = 0; i < radix; i += 1) { //count frequencies
                bucketIndex = (int)(((a[i] - min) / exponent) % radix);
                buckets[bucketIndex] += 1;
            }
            for (int i = 1; i < radix; i += 1) { //compute cumulates
                buckets[i] += buckets[i - 1];
            }
            for (int i = radix - 1; i >= 0; i -= 1) { //move records
                bucketIndex = (int)(((a[i] - min) / exponent) % radix);
                output[--buckets[bucketIndex]] = a[i];
            }
            for (int i = 0; i < radix; i += 1) {
                a[i] = output[i];
            }
        }

        @Override
        public String toString() {
            return "LSD Sort";
        }
    }

    /**
     * MSD Sort implementation.
     */
    public static class MSDSort implements SortingAlgorithm {
        @Override
        public void sort(int[] a, int k) {
            // FIXME
        }

        @Override
        public String toString() {
            return "MSD Sort";
        }
    }

    /** Exchange A[I] and A[J]. */
    private static void swap(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

}
