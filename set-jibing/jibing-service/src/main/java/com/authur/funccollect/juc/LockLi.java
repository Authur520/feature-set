package com.authur.funccollect.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description:
 * @Author: jibing.Li
 * @Date: 2021/11/7 17:10
 */
public class LockLi {
    public static void main(String[] args) {

        Ticket ticket = new Ticket();

        new Thread(()->{
            for (int i = 1; i < 40; i++) ticket.sale();
        },"A").start();

        new Thread(()->{
            for (int i = 1; i < 40; i++) ticket.sale();
        },"B").start();

        new Thread(()->{
            for (int i = 1; i < 40; i++) ticket.sale();
        },"C").start();

    }
}
class Ticket{
    private int number = 30;

    /**
     * 公平锁：十分公平，遵循先来后到
     * 非公平锁：十分不公平，可以插队（默认）
     * 区别：Synchronized 内置java关键字、无法判断锁状态、自动释放锁、阻塞会一直等、非公平锁
     *      Lock java类、可判断是否获取到锁、手动释放锁（死锁）、阻塞不一定一直等 、公平|非公平锁可手动设置
     */
    Lock lock = new ReentrantLock();

    public void sale(){
        lock.lock();

//        lock.tryLock();//尝试获取锁

        try {
            if (number>0){
                System.out.println(Thread.currentThread().getName()+"卖出了"+(number--)+"票，剩余："+number);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }


    }
}
