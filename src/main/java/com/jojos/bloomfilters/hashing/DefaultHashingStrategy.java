package com.jojos.bloomfilters.hashing;

import com.jojos.bloomfilters.ProbabilityHelper;
import org.apache.commons.codec.digest.MurmurHash3;

import java.util.ArrayList;
import java.util.List;

import static com.jojos.bloomfilters.ProbabilityHelper.DEFAULT_PROBABILITY;
import static com.jojos.bloomfilters.ProbabilityHelper.optimalBitArraySize;

/**
 * Default implementation of the {@link HashingStrategy}
 * using the {@link MurmurHash3#hash32x86} implementation from apache's commons-codec library
 *
 * @see com.jojos.bloomfilters.hashing.MurMur3HashFunction
 * @see com.jojos.bloomfilters.hashing.HashingStrategy
 */
public class DefaultHashingStrategy implements HashingStrategy {
    static final int DEFAULT_HASH_SEED = (int) System.currentTimeMillis() % 10;

    private final double probability;
    private final int arraySize;
    private final int hashFunctionsSize;
    private final List<HashFunction<String, Integer>> hashFunctions;

    public DefaultHashingStrategy(int inserts) {
        this(inserts, DEFAULT_PROBABILITY);
    }

    public DefaultHashingStrategy(int inserts, double falsePositiveProbability) {
        probability = falsePositiveProbability;
        arraySize = optimalBitArraySize(inserts, probability);
        hashFunctionsSize = ProbabilityHelper.optimalNumOfHashFunctions(inserts, arraySize());
        hashFunctions = new ArrayList<>(hashFunctionsSize);

        int seed = DEFAULT_HASH_SEED;
        for (int i = 1; i < hashFunctionsSize(); i++) {
            seed += 877; // just a prime
            hashFunctions.add(new MurMur3HashFunction(seed));
        }
    }

    @Override
    public double probabilityOfFalsePositives() {
        return probability;
    }

    @Override
    public int arraySize() {
        return arraySize;
    }

    @Override
    public int hashFunctionsSize() {
        return hashFunctionsSize;
    }

    @Override
    public List<HashFunction<String, Integer>> hashFunctions() {
        return hashFunctions;
    }
}
