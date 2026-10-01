package com.cachegrid.core;
import java.util.*;

public class LRUCache<K,V> implements EvictionStrategy<K,V> {
    private final int capacity;
    private final LinkedHashMap<K,V> map;
    public LRUCache(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity");
        this.capacity = capacity;
        this.map = new LinkedHashMap<>(16, .75f, true);
    }
    public synchronized V get(K key){ return map.get(key); }
    public synchronized void put(K key,V value){
        map.put(key,value);
        if(map.size()>capacity) map.remove(map.keySet().iterator().next());
    }
    public synchronized V remove(K key){ return map.remove(key); }
    public synchronized int size(){ return map.size(); }
    public synchronized void clear(){ map.clear(); }
    public String name(){ return "LRU"; }
}
