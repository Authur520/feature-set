package com.authur.li.juc.threadlocal;

/**
 * @Description:
 * @Author: jibing.Li
 * @Date: 2022/8/5 15:03
 */
public class ThreadLocalDemo {

    ThreadLocal<String> t1 = new ThreadLocal<>();

    private  String content;

    public String getContent() {
//        return content;
        return t1.get();
    }

    public void setContent(String content) {
//        this.content = content;
        t1.set(content);
    }


    public static void main(String[] args) {
        ThreadLocalDemo threadLocalDemo = new ThreadLocalDemo();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    threadLocalDemo.setContent(Thread.currentThread().getName()+"的数据");
                    System.out.println("---------------------");
                    System.out.println(Thread.currentThread().getName() + "---->" + threadLocalDemo.getContent());
                }
            });

            thread.setName("线程"+i);
            thread.start();
        }
    }


}
