package com.authur.li.juc.concurrent_hash_map;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author authur
 * @description:
 * 前置知识：原码、反码、补码
 *
 * JDK1.7 数组就是传参，JDK1.8之后
 */
public class ConcurrentHashMapDemo {
    public static void main(String[] args) {
        new ConcurrentHashMap<>(32);
        new HashMap<>(3).put("22",222);
        new ArrayList<>(1);
        new ArrayDeque();

        final int MAXIMUM_CAPACITY = 1 << 30;
        int a = 30;
        int i = a >>> 1;
        System.out.println(i);
        System.out.println(MAXIMUM_CAPACITY);

    }

}
