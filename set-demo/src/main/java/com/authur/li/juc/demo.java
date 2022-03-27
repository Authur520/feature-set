package com.authur.li.juc;

import java.sql.SQLOutput;
import java.util.concurrent.TimeUnit;

public class demo {
    public static void main(String[] args) throws InterruptedException {
        //获取电脑的cpu核数
        System.out.println(Runtime.getRuntime().availableProcessors());
        TimeUnit.SECONDS.sleep(1);
    }
}
