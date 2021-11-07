package com.authur.funccollect.controller;

/**
 * @Description:
 * @Author: jibing.Li
 * @Date: 2021/11/7 16:37
 */
public class RunTime {
    public static void main(String[] args) {
        //获取电脑的cpu核数
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
