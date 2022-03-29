package com.authur.li.juc.single;

/**
 * DCL懒汉式
 */
public class DCLLazyMan {

    private DCLLazyMan(){
        System.out.println(Thread.currentThread().getName()+ "ok");
    }

    private static DCLLazyMan lazyMan;

    public static DCLLazyMan getInstance(){
        if (lazyMan == null){
            synchronized (DCLLazyMan.class){
                if (lazyMan == null){
                    lazyMan = new DCLLazyMan();
                }
            }

        }
        return lazyMan;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                DCLLazyMan.getInstance();}).start();
        }
    }
}
