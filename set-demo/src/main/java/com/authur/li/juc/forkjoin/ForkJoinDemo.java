package com.authur.li.juc.forkjoin;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 *
 */
public class ForkJoinDemo extends RecursiveTask<Long> {

    private Long start;
    private Long end;

    //临界值
    private Long temp = 10000L;

    public ForkJoinDemo(Long start, Long end){
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if ((end-start) > temp){
            Long sum = 0L;
            for (Long i = start; i <= end ; i++) {
                sum += i;
            }
            return sum;
        }else {
            long middle = (start+end)/2;
            ForkJoinDemo tast1 = new ForkJoinDemo(start, middle);
            tast1.fork();
            ForkJoinDemo tast2 = new ForkJoinDemo(middle+1, end);
            tast2.fork();
            return tast1.join() + tast2.join();
        }
    }
}
