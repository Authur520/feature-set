package com.kong;

import java.util.concurrent.*;

/**
 * @author: xiangbinkong
 * @Date: 2021/7/18 21:53
 * @Description:
 */
public class ThreadMain {

	public static void main(String[] args) throws ExecutionException, InterruptedException, BrokenBarrierException {

		// 创建新线程， 拿到返回值后，退出主线程
		// 1. 线程池 future 模式
		NewTask t1 = new NewTask();
		final ExecutorService executorService = Executors.newSingleThreadExecutor();
		final Future<Integer> submit = executorService.submit(t1);
		final Integer result1  = submit.get();
		executorService.shutdown();
		System.out.println("result1" + result1);

		// 2. future task 模式
		NewTask t2 = new NewTask();
		FutureTask<Integer> futureTask = new FutureTask<>(t2);
		new Thread(futureTask).start();
		try {
			final Integer result2 = futureTask.get();
			System.out.println("result2:" + result2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		// 3. join 方法
		final Integer[] result3 = {null};
		Thread t3 = new Thread(() -> result3[0] = getZero());
		t3.start();
		t3.join();
		System.out.println("result3:" + result3[0]);

		// 4. Semaphore 控制
		final Integer[] result4 = {null};
		Semaphore semaphore = new Semaphore(0);
		Thread t4 = new Thread(() -> {
			result4[0] = getZero();
			semaphore.release();
		});
		t4.start();
		semaphore.acquire();
		System.out.println("result4:" + result4[0]);

		// 5. CyclicBarrier 控制
		CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
		final Integer[] result5 = {null};
		Thread t5 = new Thread(() -> {
			result5[0] = getZero();
			try {
				cyclicBarrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		});
		t5.start();
		cyclicBarrier.await();
		System.out.println("result5:" + result5[0]);

		// 6. CountDownLatch 控制
		CountDownLatch countdownLatch = new CountDownLatch(1);
		final Integer[] result6 = {null};
		Thread t6 = new Thread(() -> {
			result6[0] = getZero();
			countdownLatch.countDown();
		});
		t6.start();
		countdownLatch.await();
		System.out.println("result6:" + result6[0]);


		// 7. wait notify 控制
		final Integer[] result7 = {null};
		Thread t7 = new Thread(() -> {
			synchronized(result7){
				result7[0] = getZero();
				result7.notify();
			}
		});
		t7.start();
		synchronized(result7){
			result7.wait();
		}
		System.out.println("result7:" + result7[0]);

		// 8. Condition



	}

	private static Integer getZero() {
		return 0;
	}
}
