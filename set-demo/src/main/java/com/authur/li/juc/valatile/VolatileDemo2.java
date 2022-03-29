package com.authur.li.juc.valatile;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 不保证原子性
 */
public class VolatileDemo2 {
//    private volatile static int num = 0;
    private volatile static AtomicInteger num = new AtomicInteger();
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    add();
                }
            }).start();
        }

        while (Thread.activeCount() > 2){
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName()+"  "+num);
    }

    private static void add() {
//        num++;
        num.getAndIncrement();
    }
}
