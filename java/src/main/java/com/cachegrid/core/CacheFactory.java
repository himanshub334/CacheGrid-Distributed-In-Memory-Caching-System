package com.cachegrid.core;
public final class CacheFactory {
    private CacheFactory(){}
    public static <K,V> EvictionStrategy<K,V> create(String policy,int capacity){
        return switch(policy.toUpperCase()){
            case "LRU" -> new LRUCache<>(capacity);
            case "LFU" -> new LFUCache<>(capacity);
            case "ARC" -> new ARCCache<>(capacity);
            default -> throw new IllegalArgumentException("Unknown policy: "+policy);
        };
    }
}
