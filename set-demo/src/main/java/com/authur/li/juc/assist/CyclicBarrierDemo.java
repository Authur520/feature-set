package com.authur.li.juc.assist;

import com.authur.li.juc.callable.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 加法计数器
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            //当7个线程都执行完之后，执行这个里面的任务
            System.out.println("已唤醒程序！");
        });

//        System.out.println(user.getName()+user.getAge());
        for (int i = 1; i <= 7; i++) {
            int temp = i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"收集"+temp +"线程");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
