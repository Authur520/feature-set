package com.authur.li.juc.function;


import java.util.function.Function;

/**
 * 函数式接口：一个输入参数，一个输出参数
 */
public class FunctionDemo {
    public static void main(String[] args) {
//        Function function = new Function<String,String>(){
//            @Override
//            public String apply(String s) {
//                return s;
//            }
//        };
        Function<String,String> function = str -> {
            return str;
        };

        Object abc = function.apply("abc");
        System.out.println(abc);

    }

}
