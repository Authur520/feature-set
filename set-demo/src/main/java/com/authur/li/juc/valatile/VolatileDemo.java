package com.authur.li.juc.valatile;

import java.util.concurrent.TimeUnit;

/**
 * 保证可见性
 */
public class VolatileDemo {
    private volatile static int num = 0;
    public static void main(String[] args) {
        new Thread(()->{
            while (num == 0){


            }
        }).start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        num = 1;
        System.out.println(num);
    }
}
