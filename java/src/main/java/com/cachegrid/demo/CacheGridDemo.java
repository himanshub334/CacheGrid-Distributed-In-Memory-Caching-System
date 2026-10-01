package com.cachegrid.demo;
import com.cachegrid.core.*;
public class CacheGridDemo {
    public static void main(String[] args){
        EvictionStrategy<String,String> cache=CacheFactory.create("LRU",3);
        cache.put("a","1");cache.put("b","2");cache.put("c","3");
        System.out.println("a="+cache.get("a"));
        cache.put("d","4");
        System.out.println("b after eviction="+cache.get("b"));
        ConsistentHashRing<String> ring=new ConsistentHashRing<>(64);
        ring.addNode("node-a");ring.addNode("node-b");ring.addNode("node-c");
        System.out.println("owner(user-42)="+ring.locate("user-42"));
    }
}
