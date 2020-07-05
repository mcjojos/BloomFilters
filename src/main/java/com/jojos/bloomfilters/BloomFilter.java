package com.jojos.bloomfilters;

import com.jojos.bloomfilters.hashing.HashFunction;
import com.jojos.bloomfilters.hashing.HashingStrategy;

import java.util.BitSet;

/**
 * Implementation of the <a href="https://en.wikipedia.org/wiki/Bloom_filter">bloom filters</a>
 *
 * Java's {@link BitSet} implementation is used to internally store the array of bits
 *
 * @author karanikasg@gmail.com
 */
public class BloomFilter {

    private final BitSet bitSet;
    private final HashingStrategy strategy;

    /**
     * Public constructor of the Bloom Filters implementation
     *
     * @param hashingStrategy the {@link HashingStrategy} to be used
     */
    public BloomFilter(HashingStrategy hashingStrategy) {
        this.strategy = hashingStrategy;
        this.bitSet = new BitSet(strategy.arraySize());

    }

    /**
     * Add and map the input string in our dictionary
     * @param data the input string
     */
    public void add(String data) {
        for (HashFunction<String, Integer> hashFunction : strategy.hashFunctions()) {
            int hash = hashFunction.apply(data);
            bitSet.set(Math.floorMod(hash, strategy.arraySize()));
        }
    }

    /**
     * Check to see if the provided string exists in the dictionary.
     * If the answer is false then we are 100% sure the input is not contained
     * If the answer is true then it probably exists. There might be a chance that it doesn't exist
     * and in that case it is referred as a false positive. The probability of a false positive is controlled
     * by the HashingStrategy implementation and is a function of the size of the bit array together with
     * the number of hashing functions that are used.
     * There are optimal values for both of them.
     *
     * @see ProbabilityHelper for more information about the mathematical representation of the optimal values.
     *
     * @param data the input value to be checked against our dictionary
     * @return false if the input doesn't exist (100% accuracy), true otherwise (not 100%)
     */
    public boolean exists(String data) {
        for (HashFunction<String, Integer> hashFunction : strategy.hashFunctions()) {
            int hash = hashFunction.apply(data);
            if (!bitSet.get(Math.floorMod(hash, strategy.arraySize()))) {
                return false;
            }
        }
        return true;
    }

}
