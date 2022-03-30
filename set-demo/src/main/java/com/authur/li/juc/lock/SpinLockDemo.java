package com.authur.li.juc.lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁
 */
public class SpinLockDemo {

    AtomicReference<Thread> atomicReference= new AtomicReference<>();

    //加锁
    public void MyLock(){
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"==> MyLock");
        while (!atomicReference.compareAndSet(null, thread)){

        }

    }

    //解锁
    public void MyUnLock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "==> MyUnLock");
        atomicReference.compareAndSet(thread, null);
    }
}
