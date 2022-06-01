package com.authur.funccollect.controller;

import java.util.ArrayList;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Description:
 * @Author: jibing.Li
 * @Date: 2022/2/27 18:55
 */
public class Demo03 {


    public static void main(String[] args) {
        String str = new StringBuilder("liji").toString();
        System.out.println(str);
        System.out.println(str.intern());
        System.out.println(str.equals(str.intern()));

        String jav = new StringBuilder("java").toString();
        System.out.println(jav);
        System.out.println(jav.intern());
        System.out.println(jav.equals(jav.intern()));

        IntStream.range(0, 1111).boxed();
        System.out.println();
    }

}
