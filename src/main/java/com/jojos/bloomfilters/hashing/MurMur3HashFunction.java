package com.jojos.bloomfilters.hashing;

import org.apache.commons.codec.digest.MurmurHash3;

import java.nio.charset.StandardCharsets;

/**
 * Implementation of the {@link HashFunction|. It generates a hash value for an input string
 * by using the {@link MurmurHash3#hash32x86} function from Apache's commons codec digest library
 *
 * It can be instantiated multiple times from the caller by specifying a different {@link #seed}.
 *
 */
public class MurMur3HashFunction implements HashFunction<String, Integer> {

    private final int seed;

    public MurMur3HashFunction(int seed) {
        this.seed = seed;
    }

    /**
     * Generates 32-bit hash from the input string.
     * @param data the input string
     * @return a hash value
     */
    @Override
    public Integer apply(String data) {
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        return MurmurHash3.hash32x86(bytes, 0, bytes.length, seed);
    }
}
