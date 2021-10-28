package com.authur.funccollect.controller;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description:
 * @Author: jibing.Li
 * @Date: 2021/9/15 15:27
 */
public class ReentrantLockTest {
    static ReentrantLock reentrantLock = new ReentrantLock();//默认 非公平锁

    static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public interface  Subject {
        void addObserver();
    }
//    public interface Observ

    public static void main(String[] args) {
        threadLocal.set("li");
        String s = ReentrantLockTest.threadLocal.get();
        System.out.println("sss:"+s);;
        threadLocal.remove();
        System.out.println("ss22:"+ReentrantLockTest.threadLocal.get());

    }
}
