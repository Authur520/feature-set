package com.authur.li.juc.unsafe;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListTest {

    public static void main(String[] args) {

//        List<Object> list = new ArrayList<>();
//        List<Object> synchronizedList = Collections.synchronizedList(new ArrayList<>());
//        List<Object> vector = new Vector<>();
        CopyOnWriteArrayList<Object> onWriteArrayList = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                onWriteArrayList.add(UUID.randomUUID().toString().substring(0,5));
                System.out.println(onWriteArrayList);
            }).start();
        }
    }
}
