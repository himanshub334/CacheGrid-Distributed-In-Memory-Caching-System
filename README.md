# CacheGrid — Distributed In-Memory Caching System

A systems-oriented distributed cache project implementing multiple eviction policies, consistent hashing, a low-level C memory layer, and a Python benchmarking harness.

## Architecture

Client → consistent-hash ring → cache node → eviction strategy → memory layer.

### Core
- Generic `Cache<K,V>` interface
- LRU, LFU and ARC-style strategies
- Factory pattern for strategy construction
- Strategy pattern for policy substitution
- Thread-safe cache operations
- Consistent hashing with virtual nodes
- C/JNI-style native memory module for off-heap byte storage

### Benchmarking
The Python harness generates:
- Zipfian workloads
- Uniform workloads
- Burst workloads

It records hit rate, p50/p95/p99 latency and throughput and exports CSV files. The included analysis script produces pandas plots.

## Run

### Java tests
```bash
cd java
mvn test
```

### Demo
```bash
mvn -q exec:java -Dexec.mainClass=com.cachegrid.demo.CacheGridDemo
```

### Python benchmark
```bash
python -m venv .venv
# Windows
.venv\Scripts\activate
pip install -r benchmark/requirements.txt
python benchmark/run_benchmark.py --operations 50000 --keys 5000 --output results.csv
python benchmark/analyze.py results.csv
```

### Native module
The `native/` directory contains a small C allocation layer and build instructions. The Java demo remains runnable without compiling native code.

## Linux perf

For a real before/after measurement:
```bash
perf stat -e cache-misses,cache-references,instructions,cycles java ...
```

Do not treat the resume's 14%→3.1% cache-miss rate or 8µs→1.2µs latency as reproduced by this repository unless the benchmark is actually run on the target hardware. The repository provides the methodology rather than fabricating those measurements.

## Docker

```bash
docker compose up --build
```

This starts the Java demo environment and benchmark container.

## Disclaimer

This is a systems engineering project for learning and benchmarking. Distributed production deployment would additionally require replication, node membership/failure detection, persistence policy, network protocol, authentication and observability.
# CacheGrid-Distributed-In-Memory-Caching-System
