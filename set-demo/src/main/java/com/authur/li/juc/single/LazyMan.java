package com.authur.li.juc.single;

public class LazyMan {

    private LazyMan(){
        System.out.println(Thread.currentThread().getName()+ "ok");
    }

    private static LazyMan lazyMan;

    public static LazyMan getInstance(){
        if (lazyMan == null){
            lazyMan = new LazyMan();
        }
        return lazyMan;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{LazyMan.getInstance();}).start();
        }
    }
}
