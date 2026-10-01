# Architecture

## Request routing
A consistent-hash ring maps keys to cache nodes. Virtual nodes distribute positions around the hash ring.

## Eviction
`Cache<K,V>` is the stable abstraction. `CacheFactory` chooses an implementation at construction. Each implementation can be supplied wherever the interface is expected, enabling policy substitution without changing callers.

## Native memory
The C module demonstrates contiguous off-heap allocation. It is intentionally isolated so the Java cache can be benchmarked independently.

## Production extensions
Replication, node health, membership changes, network serialization, TTLs, persistence and authentication are intentionally left as extension points.
