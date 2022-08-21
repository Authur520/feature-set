package com.authur.template.callable.demo;


import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author authur
 * @description:多线程的方式调用接口
 */
public class CallableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long id = 123;
//        getUserInfoConc(id);
        getuser(id);
    }

    private static void getuser(long id) throws InterruptedException {
        long currentTimeMillis = System.currentTimeMillis();
        getUserInfo(id);
        getUserInfo(id);
        long currentTimeMillis1 = System.currentTimeMillis();
        System.out.println("耗时："+(currentTimeMillis1-currentTimeMillis));
    }

    public static Object getUserInfoConc(final long userId) throws ExecutionException, InterruptedException {

        final long currentTimeMillis = System.currentTimeMillis();

        //获取用户
        Callable<JSONObject> userCallable = new Callable<JSONObject>() {
            public JSONObject call() throws Exception {
                String user = getUserInfo(userId);
                JSONObject userInfo = JSONObject.parseObject(user);
                return userInfo;
            }
        };
        FutureTask userTask = new FutureTask(userCallable);
        new Thread(userTask).start();

        //获取订单
        Callable<JSONObject> orderCallable = new Callable<JSONObject>() {
            public JSONObject call() throws Exception {
                String user = getUserInfo(userId);
                JSONObject userInfo = JSONObject.parseObject(user);
                return userInfo;
            }
        };
        FutureTask orderTask = new FutureTask(userCallable);
        new Thread(orderTask).start();

        HashMap<Object, Object> map = new HashMap();
        map.put("user",userTask.get());
        map.put("order",orderTask.get());

        long endTime = System.currentTimeMillis();
        System.out.println(endTime-currentTimeMillis);
        return map;
    }


    public static String getUserInfo(long userId) throws InterruptedException {
        System.out.println(userId);
        TimeUnit.SECONDS.sleep(1);
        return String.valueOf(userId);
    }

}
