package com.authur.template.batchSync;




import org.apache.ibatis.ognl.ObjectElementsAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author authur
 * @description:
 */
@Service
public class BatchService {

    @Autowired
    UserService userService;

    LinkedBlockingDeque<Request> queue = new LinkedBlockingDeque<>(20000);

    public Map<String, Object> queryBatch(String id) throws ExecutionException, InterruptedException {
        String serialNo = UUID.randomUUID().toString();
        //监听结果线程
        CompletableFuture<Map<String, Object>> future = new CompletableFuture<>();
        //绑定业务线程
        Request request = new Request();
        request.serialNo = serialNo;
        request.id = id ;
        request.future = future;
        queue.add(request);
        return future.get();
    }

    //定时任务处理器（线程池的定时任务）
    @PostConstruct
    public void doBusince(){
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(1);
        threadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                int size = queue.size();
                if (size == 0) return;
                //根据接口来封装批量参数
                List<Map<String, Object>> params = new ArrayList<>();
                List<Request> requests = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    Request request = queue.poll();
                    Map<String, Object> map = new HashMap<>();
                    map.put("serialNo", request.serialNo);
                    map.put("id", request.id);
                    params.add(map);
                    requests.add(request);
                }
                List<Map<String, Object>> userList = userService.getUser(params);
                for (int i = 0; i < requests.size(); i++) {
                    for (int j = 0; j < userList.size(); j++) {
                        Map<String, Object> userMap = userList.get(j);
                        if (requests.get(i).serialNo.equals(userMap.get("serialNo"))){
                            requests.get(i).future.complete(userMap);
                            break;
                        }
                    }
                }
            }
        }, 100, 10, TimeUnit.MILLISECONDS);


    }

    public String Batch(){
        System.out.println();
        return "spring";
    }
}
class Requset{
    String serialNo;
    CompletableFuture<Map<String, Object>> future;
    String id;
}

