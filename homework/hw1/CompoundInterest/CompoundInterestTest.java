import static org.junit.Assert.*;
import org.junit.Test;

public class CompoundInterestTest {

    @Test
    public void testNumYears() {
        assertEquals(0, CompoundInterest.numYears(2018));
        assertEquals(1, CompoundInterest.numYears(2019));
        assertEquals(10, CompoundInterest.numYears(2028));


        /** Sample assert statement for comparing integers.

        assertEquals(0, 0);*/
    }

    @Test
    public void testFutureValue() {
        double tolerance = 0.01;
        assertEquals(12.544, CompoundInterest.futureValue(10, 12, 2020), tolerance);
        assertEquals(161.051, CompoundInterest.futureValue(100, 10, 2023), tolerance);
        assertEquals(14626.304, CompoundInterest.futureValue(5000, 5, 2040), tolerance);
        assertEquals(7.744, CompoundInterest.futureValue(10, -12, 2020), tolerance);
        assertEquals(59.049, CompoundInterest.futureValue(100, -10, 2023), tolerance);
        assertEquals(1617.6677249, CompoundInterest.futureValue(5000, -5, 2040), tolerance);
    }

    @Test
    public void testFutureValueReal() {
        double tolerance = 0.01;
        assertEquals(11.8026496, CompoundInterest.futureValueReal(10, 12, 2020, 3), tolerance);
        assertEquals(124.6181934, CompoundInterest.futureValueReal(100, 10, 2023, 5), tolerance);
        assertEquals(2335.9675063, CompoundInterest.futureValueReal(5000, 5, 2040, 8), tolerance);
        assertEquals(7.2863296, CompoundInterest.futureValueReal(10, -12, 2020, 3), tolerance);
        assertEquals(45.6909906, CompoundInterest.futureValueReal(100, -10, 2023, 5), tolerance);
        assertEquals(258.357767, CompoundInterest.futureValueReal(5000, -5, 2040, 8), tolerance);
    }





    @Test
    public void testTotalSavings() {
        double tolerance = 0.01;
        assertEquals(16550, CompoundInterest.totalSavings(5000, 2020, 10), tolerance);
        assertEquals(7598.837181456538101887206322134319104, CompoundInterest.totalSavings(1000, 2036, -12), tolerance);
        assertEquals(1448.6562465909833728, CompoundInterest.totalSavings(100, 2027, 8), tolerance);
    }

    @Test
    public void testTotalSavingsReal() {
        double tolerance = 0.01;
        assertEquals(15571.895, CompoundInterest.totalSavingsReal(5000, 2020, 10, 3), tolerance);
        assertEquals(3644.4276572, CompoundInterest.totalSavingsReal(1000, 2036, -12, 4), tolerance);
        assertEquals(913.0147443, CompoundInterest.totalSavingsReal(100, 2027, 8, 5), tolerance);
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(CompoundInterestTest.class));
    }
}
