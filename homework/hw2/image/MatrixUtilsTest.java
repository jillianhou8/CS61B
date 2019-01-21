package image;

import org.junit.Test;

import static image.MatrixUtils.accumulateVertical;
import static org.junit.Assert.*;

/** FIXME
 *  @author FIXME
 */

public class MatrixUtilsTest {
    /** FIXME
     */

    @Test
    public void testAccumulateVertical() {
        double[][] original = {{1000000, 1000000, 1000000, 1000000},
                {1000000, 75990, 30003, 1000000}, {1000000, 30002, 103046, 1000000}, {1000000, 29515, 38273, 1000000},
                {1000000, 73403, 35399, 1000000}, {1000000, 1000000, 1000000, 1000000}};
        double[][] result = {{1000000, 1000000, 1000000, 1000000}, {2000000, 1075990, 1030003, 2000000},
                {2075990, 1060005, 1133049, 2030003}, {2060005, 1089520, 1098278, 2133049}, {2089520, 1162923, 1124919, 2098278},
                {2162923, 2124919, 2124919, 2124919}};
        assertEquals(result, accumulateVertical(original));
    }

    @Test
    public void testAccumulate() {
        double[][] original = {{1000000, 1000000, 1000000, 1000000},
                {1000000, 75990, 30003, 1000000}, {1000000, 30002, 103046, 1000000}, {1000000, 29515, 38273, 1000000},
                {1000000, 73403, 35399, 1000000}, {1000000, 1000000, 1000000, 1000000}};
        double[][] result = {{1000000, 1000000, 1000000, 1000000}, {2000000, 1075990, 1030003, 2000000},
                {2075990, 1060005, 1133049, 2030003}, {2060005, 1089520, 1098278, 2133049}, {2089520, 1162923, 1124919, 2098278},
                {2162923, 2124919, 2124919, 2124919}};
        assertEquals(result, accumulateVertical(original));

    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(MatrixUtilsTest.class));
    }
}
