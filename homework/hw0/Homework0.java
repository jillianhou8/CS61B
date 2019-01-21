import java.util.Arrays;

public class Homework0 {


  public static int whileMax(int[] a) {
    int max = a[0];
    int i = 0;
    while (i < a.length) {
      if (a[i] > max) {
        max = a[i];
        i++;
      }
    }
    return max;
  }

  public static int forMax(int[] a) {
    int max = a[0];
    for (int i = 0; i < a.length; i++) {
      if (a[i] > max) {
        max = a[i];
      }
    }
    return max;
  }

  public static boolean ithreeSum(int[] a) {
    for (int i = 0; i < a.length - 2; i++) {
      int left = i + 1;
      int right = a.length - 1;

      while (left < right) {
        int sum = a[i] + a[left] + a[right];
        if (sum == 0) {
          return true;
        }
        else {
          right--;
        }
      }
    }
    return false;
  }

//probably this one
  public static boolean threeSum(int[] a) {
    Arrays.sort(a);
    for (int i = 0; i < a.length; i++) {
      for (int j = i + 1; j < a.length; j++) {
        for (int k = j + 1; k < a.length; k++) {
          if ((a[i] + a[j] + a[k]) == 0) {
            return true;
          }
        }
      }
    }
    return false;
  }


  public static boolean threeSumDistinct(int[] a) {
    Arrays.sort(a);
    for (int i = 0; i < a.length; i++) {
      for (int j = i + 1; j < a.length; j++) {
        for (int k = j + 1; k < a.length; k++) {
          if ((a[i] + a[j] + a[k]) == 0) {
            return true;
          }
        }
      }
    }
    return false;
  }




}
