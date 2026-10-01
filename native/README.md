# Native memory layer

`cache_memory.c` demonstrates direct allocation of contiguous byte storage outside the JVM heap.

Build on Linux:

```bash
gcc -O2 -shared -fPIC cache_memory.c -o libcachememory.so
```

A production JNI/JNA binding would expose allocation/free and read/write operations to Java. The sample is intentionally standalone so the Java project does not require native compilation to run.
