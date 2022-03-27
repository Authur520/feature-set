package com.authur.li.juc.pool;

import java.util.concurrent.*;

public class ThreadPoolDemo {
    public static void main(String[] args) {
//        Executors.newSingleThreadExecutor();//单个线程
//        Executors.newFixedThreadPool(5);//固定线程
//        Executors.newCachedThreadPool();//根据cpu确定有几个线程

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,
                5,
                3,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
//                new ThreadPoolExecutor.AbortPolicy());//抛出异常RejectedExecutionException
//                new ThreadPoolExecutor.CallerRunsPolicy());//哪来的去哪里，main线程会执行他
//                new ThreadPoolExecutor.DiscardPolicy());//不会抛异常，丢掉任务
                new ThreadPoolExecutor.DiscardOldestPolicy());//不会抛异常尝试跟最早的线程去竞争

        try {
            for (int i = 0; i < 13; i++) {
                executor.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"ok");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

    }

}
