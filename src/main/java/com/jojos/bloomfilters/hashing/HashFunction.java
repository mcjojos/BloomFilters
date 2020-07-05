package com.jojos.bloomfilters.hashing;

import java.util.function.Function;

/**
 * Marker Interface.
 *
 * Implementors should provide a way of generating hash values for a particular String Input
 *
 * @see java.util.function.Function
 *
 */
public interface HashFunction<S, Integer> extends Function<S, Integer> {
}
