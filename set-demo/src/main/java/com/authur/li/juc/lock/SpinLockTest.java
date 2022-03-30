package com.authur.li.juc.lock;

import java.time.temporal.Temporal;
import java.util.concurrent.TimeUnit;

public class SpinLockTest {
    public static void main(String[] args) {
        SpinLockDemo lock = new SpinLockDemo();

        new Thread(()->{
            lock.MyLock();
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.MyUnLock();
            }
        },"T1").start();

        new Thread(()->{
            lock.MyLock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.MyUnLock();
            }
        },"T2").start();
    }
}
