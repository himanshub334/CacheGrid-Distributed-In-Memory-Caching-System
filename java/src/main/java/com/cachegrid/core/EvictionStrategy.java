package com.cachegrid.core;
public interface EvictionStrategy<K,V> extends Cache<K,V> {
    String name();
}
