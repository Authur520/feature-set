package com.authur.li.juc;

import sun.misc.Launcher;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class demo {
    public static void main(String[] args) throws InterruptedException {
        //获取电脑的cpu核数
//        System.out.println(Runtime.getRuntime().availableProcessors());
//        TimeUnit.SECONDS.sleep(1);




//        String key = "lij";
//        int h;
//        h = key.hashCode();
//        System.out.println("h::" + +h);
//        int c = (h >>> 16);
//        System.out.println("c:"+  c);
//        int a = (h) ^ (h >>> 16);
//        System.out.println("::"+a+":"+System.currentTimeMillis());
//
//        int n = (18325) ^ (1);
//        int m = 18325 >>> 16;
//        int t = (m) ^ (m >>> 16);
////        int n = (107149) ^ (1);
//        System.out.println("n:"+n+"m:"+m+"t"+t);
//
//        int i = (n - 1) & 4234;
//        int ii = (n - 1) % 4234;
//
//        System.out.println("i:::"+i+"ii:::"+ii);

        int a = 4,b = 2;
        a &= b;
        System.out.println("a: "+a);












//        String key1 = "linjioadfjiaojj";
//        int i = key1.hashCode();
//        int p = i ^ (i >>> 16);
//        System.out.println("i:::"+i+"p:::"+p);

//        1 1010 0010 1000 1101
//        -11100110000000 1000 1001 1110  1111
    }
}
