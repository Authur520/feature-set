package com.authur.li.juc.unsafe;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MapTest {
    public static void main(String[] args) {

        Map<Object, Object> map = new HashMap<>();
        Map<Object, Object> synchronizedMap = Collections.synchronizedMap(new HashMap<>());
        ConcurrentHashMap<Object, Object> concurrentHashMap = new ConcurrentHashMap<>();
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                concurrentHashMap.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0, 5));
                System.out.println(concurrentHashMap);
            },String.valueOf(i)).start();
        }
    }
}
