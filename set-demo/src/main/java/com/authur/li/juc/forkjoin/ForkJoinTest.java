package com.authur.li.juc.forkjoin;

import org.springframework.util.StopWatch;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

public class ForkJoinTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        test1();
//        test2();
        test3();
    }

    public static void test1(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Long sum = 0L;
        for (Long i = 1L; i <= 10_0000_0000 ; i++) {
            sum += i;
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    //ForlJoin
    public static void test2() throws ExecutionException, InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> tast = new ForkJoinDemo(0L, 10_0000_0000L);
        ForkJoinTask<Long> submit = forkJoinPool.submit(tast);
        Long sum = submit.get();

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    //并行流
    public static void test3(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        long reduce = LongStream.rangeClosed(0L, 10_0000_0000L).parallel().reduce(1, Long::sum);

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

}
