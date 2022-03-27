package com.authur.li.juc.function;

import java.util.function.Consumer;

/**
 * 生产者（供给型接口）：只有入参，没有返回值
 */
public class ConsumerDemo {
    public static void main(String[] args) {
//        Consumer<String> consumer = new Consumer<String>() {
//            @Override
//            public void accept(String str) {
//                System.out.println(str);
//            }
//        };
        Consumer<String> consumer = (str)->{
            System.out.println(str);
        };

        consumer.accept("aaaaa");
    }

}
