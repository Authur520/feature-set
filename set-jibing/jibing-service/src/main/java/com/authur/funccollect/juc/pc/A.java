package com.authur.funccollect.juc.pc;

import java.sql.SQLOutput;

/**
 * @Description: 线程间的通信      等待唤醒，停止唤醒
 * 线程交替执行 A  B  操作同一个变量
 * @Author: jibing.Li
 * @Date: 2021/11/7 17:42
 */
public class A {
    public static void main(String[] args) {
        Data data = new Data();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"A").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"B").start();




    }

}
class Data{
    private int number = 0;

    public synchronized  void increment() throws InterruptedException {

        if (number!=0){//if 改成while
            //等待
            this.wait();
        }
        number++;
        //通知其他线程，我+1完毕了
        System.out.println(Thread.currentThread().getName()+ "=" +number);
        this.notifyAll();
    }

    public synchronized  void decrement() throws InterruptedException {
        if (number!=0){//if 改成while
            //等待
            this.wait();
        }
        number++;
        //通知其他线程，我-1完毕了
        System.out.println(Thread.currentThread().getName()+ "=" +number);
        this.notifyAll();
    }


}