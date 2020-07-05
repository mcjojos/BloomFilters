package com.jojos.bloomfilters;

import com.jojos.bloomfilters.hashing.DefaultHashingStrategy;
import com.jojos.bloomfilters.hashing.HashingStrategy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BloomFiltersWithCustomFalsePositiveProbabilityTest {

    private static BloomFilter bf;
    private static Supplier<Stream<String>> dictSupplier;
    private static HashingStrategy hashingStrategy;
    private static final double PROBABILITY = 0.001;

    /**
     * load the wordList before the tests
     */
    @BeforeAll
    public static void loadWordList() {
        String resourceName = "wordlist.txt";
        ClassLoader classLoader = BloomFiltersWithCustomFalsePositiveProbabilityTest.class.getClassLoader();
        File file = new File(classLoader.getResource(resourceName).getFile());
        String absolutePath = file.getAbsolutePath();

        dictSupplier = () -> {
            try {
                return Files.lines(Paths.get(absolutePath), StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Stream.empty();
        };

        long inserts = dictSupplier.get().count();
        if (inserts > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(String.format("Dictionary can't hold more than %s values", Integer.MAX_VALUE));
        }
        hashingStrategy = new DefaultHashingStrategy(Math.toIntExact(inserts), PROBABILITY);
        bf = new BloomFilter(hashingStrategy);
        dictSupplier.get().forEach(s -> bf.add(s));
        assertTrue(absolutePath.endsWith("wordlist.txt"));
    }

    @Test
    public void testFalsePositiveProbability() {
        long totalCount = dictSupplier.get().count();
        AtomicLong falsePositives = new AtomicLong();
        dictSupplier.get().forEach(s -> {
            String nonExistingString = s + "_test";
            boolean exists = bf.exists(nonExistingString);
            if (exists) {
                falsePositives.incrementAndGet();
            }
        });
        assertTrue(ProbabilityHelperTest.isRelativelyEqual((double)falsePositives.get() / totalCount, PROBABILITY));
        assertEquals(4872305, hashingStrategy.arraySize());
        assertEquals(10, hashingStrategy.hashFunctionsSize());

    }
}
