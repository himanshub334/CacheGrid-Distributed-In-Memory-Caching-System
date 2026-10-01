package com.cachegrid.core;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CacheTest {
    @Test void lruEvictsLeastRecentlyUsed(){
        var c=new LRUCache<String,Integer>(2);c.put("a",1);c.put("b",2);assertEquals(1,c.get("a"));c.put("c",3);
        assertNull(c.get("b"));assertEquals(1,c.get("a"));
    }
    @Test void factoryCreatesStrategies(){
        assertEquals("LRU",CacheFactory.create("lru",2).name());
        assertEquals("LFU",CacheFactory.create("LFU",2).name());
        assertEquals("ARC",CacheFactory.create("arc",2).name());
    }
    @Test void ringRoutesKeys(){
        var r=new ConsistentHashRing<String>(16);r.addNode("a");r.addNode("b");
        assertNotNull(r.locate("key"));
    }
}
