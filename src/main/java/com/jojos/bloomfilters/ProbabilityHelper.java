package com.jojos.bloomfilters;

/**
 * Utility class providing a couple of bloom filter related optimization functions
 */
public class ProbabilityHelper {

    public static final double DEFAULT_PROBABILITY = 0.03;

    /**
     * Return the optimal BitSet's size of the bloom filter.
     *
     * size = -insertions * ln(probability) / (ln(2)^2)
     *
     * Check http://en.wikipedia.org/wiki/Bloom_filter#Probability_of_false_positives.
     *
     * @param inserts expected insertions > 0
     * @param probability false positive rate 0 < probability < 1
     */
    public static int optimalBitArraySize(int inserts, double probability) {
        expectArgument(inserts > 0, "insertions must be > 0");
        expectArgument(probability > 0 && probability < 1, "Allowed probability value should be between 0 < probability < 1");
        return (int) (-inserts * Math.log(probability) / (Math.log(2) * Math.log(2)));
    }

    /**
     * Return the number of hash functions to be taken, to minimize the false positive probability,
     * if the bit array size and inserts are taken as constants
     *
     * @param inserts the number of expected insertions
     * @param arraySize (possibly optimal) size of the bit array in bloom filters
     */
    public static int optimalNumOfHashFunctions(long inserts, long arraySize) {
        expectArgument(inserts >= 0, "insertions must be >= 0");
        expectArgument(arraySize >= 0, "arraySize must be >= 0");
        if (inserts == 0) {
            inserts = 1;
        }
        return Math.max(1, (int) Math.round(((double) arraySize / inserts) * Math.log(2)));
    }

    private static void expectArgument(boolean condition, String errorMsg) {
        if (!condition) {
            throw new IllegalArgumentException(errorMsg);
        }
    }

}
