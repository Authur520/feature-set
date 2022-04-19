package com.authur.li.juc.geek_time;

import org.springframework.util.StopWatch;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * @author authur
 * @description:
 */
public class LockSupportDemo {
    static int num ;
    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("treeTime:");
        AtomicInteger atomicInteger = new AtomicInteger(10);
        System.out.println("atomicInteger="+atomicInteger.getAndIncrement());
        int andIncrement = atomicInteger.getAndIncrement();
        System.out.println("andIncrement="+andIncrement);

        LockSupport.parkNanos(20000);
        num++;
        System.out.println("LockSupportTest="+num);
        LockSupport.unpark(Thread.currentThread());
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }
}
