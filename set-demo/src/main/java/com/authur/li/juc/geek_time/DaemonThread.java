package com.authur.li.juc.geek_time;

/**
 * @Description:
 * @Author: jibing.Li
 * @Date: 2022/4/8 16:21
 */
public class DaemonThread {
    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread t = Thread.currentThread();
            System.out.println("当前线程:" + t.getName());
        };
        Thread thread = new Thread(task);
        thread.setName("test-thread-1");
        thread.setDaemon(true);
        thread.start();

//        Thread.sleep(5500);
    }
}
