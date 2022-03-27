package com.authur.li.juc.unsafe;

import org.springframework.util.StopWatch;

import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

public class SetTest {

    public static void main(String[] args) {
//        HashSet<Object> set = new HashSet<>();
//        Set<Object> synchronizedSet = Collections.synchronizedSet(new HashSet<>());
        CopyOnWriteArraySet<Object> copyOnWriteArraySet = new CopyOnWriteArraySet<>();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                copyOnWriteArraySet.add(UUID.randomUUID().toString().substring(0,5));
                System.out.println(copyOnWriteArraySet);
            }).start();
        }
        stopWatch.stop();
        System.out.println("时长："+stopWatch.prettyPrint());
    }
}
