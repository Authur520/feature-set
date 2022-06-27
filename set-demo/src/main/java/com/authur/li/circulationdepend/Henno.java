package com.authur.li.circulationdepend;

import java.util.ArrayList;

/**
 * @Description: 值传递demo
 * @Author: jibing.Li
 * @Date: 2022/6/8 17:49
 */
public class Henno {

//    public static void main(String[] args) {
//
//        //值传递举例
//        Integer num = new Integer(1000);
//        num = 1200;
//        System.out.println("改之前的值:" + num);
//        modify(num);
//        System.out.println("改之后的值:" + num);
//
//        StringBuilder code = new StringBuilder("200");
//        System.out.println("改之前的code："+code);
//        modify1(code);
//        System.out.println("改之后的code："+code);
//
//        ArrayList<Integer> list = new ArrayList<>();
//        list.add(1);
//        System.out.println(list);
//        modify2(list);
//        System.out.println(list);
//    }
public static void main(String[] args) {
    int a = 8160;
    int b = 1300;
    int c = a-b;
    int d = 7600;
    int e = d-c;
    int f = e/2;
    System.out.println("wode:"+c);
    System.out.println("nide:"+e);
    System.out.println("nide:"+f);
}


    private static void modify(Integer num2) {
        num2.intValue();
        num2 = 1100;
    }

    private static void modify1(StringBuilder code) {
        code.append("300");
    }

    private static void modify2(ArrayList<Integer> list) {
        list.add(2);
    }

}
