package com.authur.test;

import com.authur.template.TemplateApplication;
import com.authur.template.batchSync.BatchService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

/**
 * @author authur
 * @description:
 */
@RunWith(
        SpringRunner.class
)
@SpringBootTest(classes = TemplateApplication.class)
public class BatchTest {

    @Autowired
    BatchService batchService;

    private static final int THREAD_NUM = 100;

    private CountDownLatch countDownLatch = new CountDownLatch(THREAD_NUM);

    public void TestInterface() {
        for (int i = 0; i < THREAD_NUM; i++) {
            final String id = String.valueOf(100 + i);
            new Thread(()->{
                try {
                    countDownLatch.countDown();
                    countDownLatch.await();
                    Map<String, Object> map = batchService.queryBatch(id);
                    System.out.println("½á¹û£º"+map);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        }
    }


}













