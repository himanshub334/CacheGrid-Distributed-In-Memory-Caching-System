package com.cachegrid.core;
import java.util.*;

public class LFUCache<K,V> implements EvictionStrategy<K,V> {
    private static class Entry<V>{ V value; int freq=1; long seq; Entry(V v,long s){value=v;seq=s;} }
    private final int capacity; private long sequence;
    private final Map<K,Entry<V>> data = new HashMap<>();
    public LFUCache(int capacity){ if(capacity<=0)throw new IllegalArgumentException("capacity"); this.capacity=capacity; }
    public synchronized V get(K key){ Entry<V> e=data.get(key); if(e==null)return null; e.freq++; e.seq=++sequence; return e.value; }
    public synchronized void put(K key,V value){
        Entry<V> old=data.get(key);
        if(old!=null){old.value=value;old.freq++;old.seq=++sequence;return;}
        if(data.size()>=capacity){
            K victim=null; Entry<V> best=null;
            for(var x:data.entrySet()) if(best==null || x.getValue().freq<best.freq ||
                (x.getValue().freq==best.freq && x.getValue().seq<best.seq)){victim=x.getKey();best=x.getValue();}
            data.remove(victim);
        }
        data.put(key,new Entry<>(value,++sequence));
    }
    public synchronized V remove(K key){Entry<V> e=data.remove(key);return e==null?null:e.value;}
    public synchronized int size(){return data.size();}
    public synchronized void clear(){data.clear();}
    public String name(){return "LFU";}
}
