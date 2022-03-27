package com.authur.li.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SaleTicketDemo {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        new Thread(()->{
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }).start();

        new Thread(()->{
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }).start();

        new Thread(()->{
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }).start();


    }

}

class Ticket{
    private int number = 50;

    Lock lock = new ReentrantLock();

    public synchronized void sale(){

        lock.lock();
        try {
            if (number > 0){
                System.out.println(Thread.currentThread().getName()+"卖出了"+(number--)+"票，剩余："+number);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }
}