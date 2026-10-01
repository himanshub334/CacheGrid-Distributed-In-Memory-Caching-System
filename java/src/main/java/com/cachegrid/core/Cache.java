package com.cachegrid.core;
public interface Cache<K,V> {
    V get(K key);
    void put(K key, V value);
    V remove(K key);
    int size();
    void clear();
}
