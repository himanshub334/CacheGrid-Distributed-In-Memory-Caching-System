package com.cachegrid.core;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class ConsistentHashRing<N> {
    private final SortedMap<Long,N> ring=new TreeMap<>();
    private final int virtualNodes;
    public ConsistentHashRing(int virtualNodes){if(virtualNodes<1)throw new IllegalArgumentException("virtualNodes");this.virtualNodes=virtualNodes;}
    public void addNode(N node){for(int i=0;i<virtualNodes;i++)ring.put(hash(node.toString()+"#"+i),node);}
    public void removeNode(N node){ring.entrySet().removeIf(e->e.getValue().equals(node));}
    public N locate(String key){
        if(ring.isEmpty())throw new IllegalStateException("No nodes");
        long h=hash(key); var tail=ring.tailMap(h); return (tail.isEmpty()?ring:tail).get((tail.isEmpty()?ring:tail).firstKey());
    }
    private long hash(String s){try{
        byte[] b=MessageDigest.getInstance("SHA-256").digest(s.getBytes(StandardCharsets.UTF_8));
        long x=0;for(int i=0;i<8;i++)x=(x<<8)|(b[i]&255);return x&Long.MAX_VALUE;
    }catch(NoSuchAlgorithmException e){throw new IllegalStateException(e);}}
}
