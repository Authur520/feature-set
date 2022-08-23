package com.authur.template.batchSync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author authur
 * @description:Controller层做异步
 */
@RestController
@RequestMapping("/user")
public class DemoController {

    @Autowired
    BatchService batchService;

    @RequestMapping("/getUser")
    public Callable<String> DemoQuery() throws ExecutionException, InterruptedException {
        Callable<String> callable = new Callable<String>() {
            public String call() throws Exception {
                //调用service层（业务）
                String batch = batchService.Batch();
                return batch;
            }
        };
        FutureTask demoTask = new FutureTask(callable);
        return callable;
    }

}
