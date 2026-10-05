package com.xxjob.demo.handler;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CacheHandler {

    private Map<String, Object> cache = new ConcurrentHashMap<>();

    public Object getCache(String key) {
        return cache.get(key);
    }
    
    public void setCache(String key, Object value) {
        cache.put(key, value);
    }
}
