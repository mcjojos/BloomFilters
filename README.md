# Bloom Filter
A probabilistic data structure 

Simple implementation of Bloom Filter that can be used as a library. Bloom filter is used to test whether an element is a member of a set.
False positive matches are possible, but false negatives are not – in other words, a query returns either "possibly in set" or "definitely not in set."
Elements can be added to the set, but not removed; the more items added, the larger the probability of false positives. 

As a default hashing function it is using the 32-bit hash function MurmurHash3_x86_32 from Austin Applyby's original MurmurHash3 c++ code in SMHasher.

One can define different HashingFunction implementations by implementing the analogous interface. 

The default implementation is also trying to optimize the algorithm in terms of the bit array size and the number of hash functions that are used based on the following:
- <http://en.wikipedia.org/wiki/Bloom_filter#Probability_of_false_positives>
and 
- <https://en.wikipedia.org/wiki/Bloom_filter#Optimal_number_of_hash_functions>

One can run the unit tests by the simple command
```bash
mvn clean test
```
