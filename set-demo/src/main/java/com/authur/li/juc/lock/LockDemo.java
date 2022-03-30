package com.authur.li.juc.lock;

import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {
    public static void main(String[] args) {
        Phone2 phone2 = new Phone2();
        new Thread(()->{
            phone2.sms();
        },"A").start();
        new Thread(()->{
            phone2.sms();
        },"B").start();
    }



}

class Phone2{

    ReentrantLock lock = new ReentrantLock();

    public void sms(){

        lock.lock();

        try {
            System.out.println(Thread.currentThread().getName()+"sms");
            call();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
             lock.unlock();
        }

    }

    private void call() {
        lock.lock();

        try {
            System.out.println(Thread.currentThread().getName()+"call");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}