package com.authur.template.callable.test;


import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author authur
 * @description:
 */
public class DemoTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long id = 123;
        getUserInfoConc(id);
    }

    public static Object getUserInfoConc(final long userId) throws ExecutionException, InterruptedException {

        final long currentTimeMillis = System.currentTimeMillis();

        //获取用户
        Callable<String> userCallable = new Callable<String>() {
            public String call() throws Exception {
                String user = getUserInfo(userId);
                return user;
            }
        };
        FutureTask userTask = new FutureTask(userCallable);
        new Thread(userTask).start();

        //获取订单
        Callable<String> orderCallable = new Callable<String>() {
            public String call() throws Exception {
                String user = getUserInfo(userId);
                return user;
            }
        };
        FutureTask orderTask = new FutureTask(userCallable);
        new Thread(orderTask).start();

        HashMap<Object, Object> map = new HashMap();
        map.put("user",userTask.get());
        map.put("order",orderTask.get());

        long endTime = System.currentTimeMillis();
        System.out.println("耗时："+(endTime-currentTimeMillis));
        return map;
    }

    public static String getUserInfo(long userId) throws InterruptedException {
        System.out.println(userId);
        TimeUnit.MILLISECONDS.sleep(1000);
        return String.valueOf(userId);
    }

}
