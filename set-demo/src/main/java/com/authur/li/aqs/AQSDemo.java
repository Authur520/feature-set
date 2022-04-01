package com.authur.li.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * AQS
 *   state + LCH（双端队列）
 *   Node SHARED EXCLUSIVE waitState
 *   头、尾、前、后
 *
 *   头：是一个空的Node
 */
public class AQSDemo {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock(true);
        //ReentrantLock 公平锁｜非公平锁（通过CAS判断是不是等于0）

        new Thread(()->{
            lock.lock();
            try {
                System.out.println("----------B thread come in");
                TimeUnit.MINUTES.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        },"A").start();

        new Thread(()->{

            lock.lock();
            try {
                System.out.println("----------B thread come in");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        },"B").start();

        new Thread(()->{
            lock.lock();
            try {
                System.out.println("----------B thread come in");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        },"C").start();


    }
}
