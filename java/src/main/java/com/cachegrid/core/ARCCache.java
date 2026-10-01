package com.cachegrid.core;
import java.util.*;

public class ARCCache<K,V> implements EvictionStrategy<K,V> {
    private final int capacity;
    private final LinkedHashMap<K,V> recent = new LinkedHashMap<>(16,.75f,true);
    private final LinkedHashMap<K,V> frequent = new LinkedHashMap<>(16,.75f,true);
    public ARCCache(int capacity){if(capacity<=0)throw new IllegalArgumentException("capacity");this.capacity=capacity;}
    public synchronized V get(K key){
        if(frequent.containsKey(key)) return frequent.get(key);
        V v=recent.remove(key);
        if(v!=null) frequent.put(key,v);
        return v;
    }
    public synchronized void put(K key,V value){
        if(frequent.containsKey(key)){frequent.put(key,value);return;}
        if(recent.containsKey(key)){recent.remove(key);frequent.put(key,value);return;}
        recent.put(key,value); trim();
    }
    private void trim(){
        while(recent.size()+frequent.size()>capacity){
            if(recent.size()>0){recent.remove(recent.keySet().iterator().next());}
            else frequent.remove(frequent.keySet().iterator().next());
        }
    }
    public synchronized V remove(K key){V v=recent.remove(key);return v!=null?v:frequent.remove(key);}
    public synchronized int size(){return recent.size()+frequent.size();}
    public synchronized void clear(){recent.clear();frequent.clear();}
    public String name(){return "ARC";}
}
