package com.jojos.bloomfilters;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The formulas that are tested are the following:
 *      - optimal size of bitmap array:
 *          -inserts * ln(probability) / (ln(2)^2)
 * and
 *      - The number of hash functions to be taken, to minimize the false positive probability,
 *      if total bits (arraySize) and expected insertions are taken as constant then:
 *          (arraySize / inserts) * ln(2)
 */
public class ProbabilityHelperTest {
    @Test
    void optimalBitArraySizeShouldThrowIllegalArgumentExceptionWhenProbabilityZeroTest() {
        assertThrows(
                IllegalArgumentException.class,
                () -> ProbabilityHelper.optimalBitArraySize(100, 0));
    }

    @Test
    void optimalBitArraySizeShouldThrowIllegalArgumentExceptionWhenProbabilityOneTest() {
        assertThrows(
                IllegalArgumentException.class,
                () -> ProbabilityHelper.optimalBitArraySize(100, 1));
    }

    @Test
    void optimalBitArraySizeShouldThrowIllegalArgumentExceptionWhenInsertsLessThanOrEqualToZeroTest() {
        assertThrows(
                IllegalArgumentException.class,
                () -> ProbabilityHelper.optimalBitArraySize(0, 0.1));
    }

    @Test
    void optimalBitArraySizeShouldReturn9Test() {
        int size = ProbabilityHelper.optimalBitArraySize(1, 0.01);
        assertEquals(9, size);
    }

    @Test
    void optimalBitArraySizeShouldReturn958Test() {
        int size = ProbabilityHelper.optimalBitArraySize(100, 0.01);
        assertEquals(958, size);
    }

    @Test
    void optimalBitArraySizeShouldReturn9_585_058Test() {
        int size = ProbabilityHelper.optimalBitArraySize(1_000_000, 0.01);
        assertEquals(9_585_058, size);
    }

    @Test
    void optimalBitArraySizeShouldReturn12_090_970Test() {
        int size = ProbabilityHelper.optimalBitArraySize(1_000_000, 0.003);
        assertEquals(12_090_970, size);
    }

    @Test
    void optimalBitArraySizeShouldReturn814_236_333Test() {
        int size = ProbabilityHelper.optimalBitArraySize(100_000_000, 0.02);
        assertEquals(814_236_333, size);
    }

    @Test
    void optimalBitArraySizeShouldReturn1_907_139_106Test() {
        int size = ProbabilityHelper.optimalBitArraySize(1_000_000_000, 0.4);
        assertEquals(1_907_139_106, size);
    }

    @Test
    void optimalNumOfHashFunctionsShouldThrowIllegalArgumentExceptionWhenInsertsNotPositiveTest() {
        assertThrows(
                IllegalArgumentException.class,
                () -> ProbabilityHelper.optimalNumOfHashFunctions(-1, 0));
    }

    @Test
    void optimalNumOfHashFunctionsShouldThrowIllegalArgumentExceptionWhenArraySizeNotPositiveTest() {
        assertThrows(
                IllegalArgumentException.class,
                () -> ProbabilityHelper.optimalNumOfHashFunctions(0, -1));
    }

    @Test
    void optimalNumOfHashFunctionsShouldReturn6Test() {
        int amount = ProbabilityHelper.optimalNumOfHashFunctions(1, 9);
        assertEquals(6, amount);
    }

    @Test
    void optimalNumOfHashFunctionsShouldReturn2Test() {
        int amount = ProbabilityHelper.optimalNumOfHashFunctions(4, 9);
        assertEquals(2, amount);
    }

    @Test
    void optimalNumOfHashFunctionsShouldReturn1Test() {
        int amount = ProbabilityHelper.optimalNumOfHashFunctions(15, 18);
        assertEquals(1, amount);
    }

    @Test
    void optimalNumOfHashFunctionsWhenInsertsGreaterThanArraySizeTest() {
        int amount = ProbabilityHelper.optimalNumOfHashFunctions(28, 18);
        assertEquals(1, amount);
    }

    protected static boolean isRelativelyEqual(double d0, double fixed) {
        final double delta = fixed * 0.2;
        return d0 == fixed ? true : Math.abs(d0 - fixed) < delta;
    }

}
