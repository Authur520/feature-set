package com.authur.li.juc.cas;

import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * CAS：比较当前工作内存重的值和驻内存的值，如果这个是期望的，那么执行操作，如果不是期望的值就一直循环
 * 缺点：1.循环耗时
 *      2.一次性只能保证一个共享变量的原子性
 *      3.ABA问题：两个线程A+1=2 B=2-1=1 A这是又判断=1导致他拿到1又变成2   //自旋锁导致的
 *      注意：Integer超过-128～127之间Interger会在IntergerCache.cache中产生，会复用已有对象
 */
public class CASDemo {
    //CAS 比较并交换
    public static void main(String[] args) {

//        AtomicReference<Object> atomicReference = new AtomicReference<>(21);
        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(21, 22);

        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            System.out.println("a1=>"+stamp);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicStampedReference.compareAndSet(21, 22,
                    atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1));
            System.out.println("a2=>"+stamp);

            System.out.println(atomicStampedReference.compareAndSet(22, 21,
                    atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1));
            System.out.println("a3=>"+stamp);
        },"a").start();

        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            System.out.println("b1=>"+stamp);

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"b").start();

    }

}
