package com.authur.test;

import com.authur.template.TemplateApplication;
import com.authur.template.batchSync.BatchService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

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

    private static final int THREAD_NUM = 10000;

    private CountDownLatch countDownLatch = new CountDownLatch(THREAD_NUM);

    public void TestInterface() {
        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(()->{

            });

        }
    }


}
