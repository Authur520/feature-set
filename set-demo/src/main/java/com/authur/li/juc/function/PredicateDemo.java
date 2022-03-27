package com.authur.li.juc.function;

import java.util.function.Predicate;

/**
 * 断定型接口：一个传参，返回Boolean
 */
public class PredicateDemo {
    public static void main(String[] args) {
//        Predicate<String> predicate = new Predicate<>() {
//
//            @Override
//            public boolean test(String s) {
//                return s.isEmpty();
//            }
//        };

        Predicate<String> predicate = (str)->{return str.isEmpty();};
        System.out.println(predicate.test(""));
    }

}
