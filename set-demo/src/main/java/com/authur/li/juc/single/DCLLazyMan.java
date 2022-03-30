package com.authur.li.juc.single;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * DCL懒汉式单例模式
 */
public class DCLLazyMan {

    private DCLLazyMan(){
        System.out.println(Thread.currentThread().getName()+ "ok");
    }

    private volatile static DCLLazyMan lazyMan;//volatile防止指令重排

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

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
//
        //            DCLLazyMan instance = DCLLazyMan.getInstance();
        Constructor<DCLLazyMan> declaredConstructor = DCLLazyMan.class.getDeclaredConstructor(null);
        declaredConstructor.setAccessible(true);
        DCLLazyMan instance1 = declaredConstructor.newInstance();
        DCLLazyMan instance = declaredConstructor.newInstance();

        System.out.println(instance);
        System.out.println(instance1);
    }
}
