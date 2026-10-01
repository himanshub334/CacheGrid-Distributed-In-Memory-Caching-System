# Benchmarking

Run:

```bash
python benchmark/run_benchmark.py --operations 100000 --keys 10000 --output results.csv
python benchmark/analyze.py results.csv
```

For Linux hardware counters:

```bash
perf stat -e cache-references,cache-misses,instructions,cycles java ...
```

Record hardware, JVM version, thread count, workload, warm-up and cache capacity with every benchmark.

The resume's specific performance figures are not embedded as guaranteed results; reproduce them on the target machine before publishing them.
