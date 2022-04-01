package com.authur.li.aqs;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description:
 * @Author: jibing.Li
 * @Date: 2022/4/1 16:40
 * 可重入锁：同一个线程可以重入他的同一把锁
 * wait和notify必须锁的同一个对象
 */
public class LockSupportDemo {

    static Object objeckLock = new Object();
    static ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    public static void main(String[] args) {
        new Thread(()->{

        lock.lock();

        try {
            System.out.println(Thread.currentThread().getName()+"\t"+"-----------come in");

            try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"\t"+"-----------被唤醒");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        },"A").start();

        new Thread(()->{

            lock.lock();
            try {
                condition.signal();
                System.out.println(Thread.currentThread().getName()+"\t"+"-----------通知");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        },"B").start();



    }

    private static void synchronziedwaitnotfiydemo() {
        new Thread(()->{

            synchronized (objeckLock){
                System.out.println(Thread.currentThread().getName()+"\t"+"-----------come in");
                try {
                    objeckLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"\t"+"-----------被唤醒");
            }

        },"A").start();

        new Thread(()->{

            synchronized (objeckLock){
                objeckLock.notify();
                System.out.println(Thread.currentThread().getName()+"\t"+"-----------通知");
            }

        },"B").start();
    }
}
