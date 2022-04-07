package com.authur.li.juc.single;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @Description: 利用反射破坏枚举：失败
 * @Author: jibing.Li
 * @Date: 2022/4/6 16:27
 */
public class HelloTest {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Hello hello = Hello.PLATINUM;
        //        Constructor<Hello> constructor = Hello.class.getDeclaredConstructor(null);//没有这个方法
        Constructor<Hello> constructor = Hello.class.getDeclaredConstructor(String.class, int.class);
        constructor.setAccessible(true);
        Hello hello1 = constructor.newInstance();
        System.out.println(hello1);
        Hello hello2 = constructor.newInstance();

        System.out.println(hello1);
        System.out.println(hello2);


    }
}
