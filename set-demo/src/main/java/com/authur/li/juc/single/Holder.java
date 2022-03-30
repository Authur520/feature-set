package com.authur.li.juc.single;

/**
 * 静态内部类
 * 枚举没有无参构造，只有一个两个参数的有参构造
 */
public class Holder {

    private Holder(){

    }

    public static Holder getInstance(){
        return InnerClass.HOLDER;
    }

    public static class InnerClass{
        private static final Holder HOLDER = new Holder();
    }
}
