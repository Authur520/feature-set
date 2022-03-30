package com.authur.li.juc.function;

import java.util.function.Supplier;

/**
 * 消费者（供给型接口）：没有入参，只有返回值
 */
public class SupplierDemo {
    public static void main(String[] args) {
        Supplier<String> supplier = new Supplier<String>() {
            @Override
            public String get() {
                return "1234";
            }
        };
//        Supplier<String> supplier = ()->{return "1234";};
        System.out.println(supplier.get());
    }
}
