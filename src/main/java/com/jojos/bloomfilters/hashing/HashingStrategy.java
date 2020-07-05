package com.jojos.bloomfilters.hashing;

import java.util.List;

/**
 * Interface marking the strategy of Hashing.
 *
 * Holds information about things like false positives probability, size of the bit array, number of hashing functions
 * and HashFunction implementation to be deployed by the bloom filter.
 *
 * @see DefaultHashingStrategy for an example of implementor.
 */
public interface HashingStrategy {

    public double probabilityOfFalsePositives();

    public int arraySize();

    public int hashFunctionsSize();

    public List<HashFunction<String, Integer>> hashFunctions();
}
