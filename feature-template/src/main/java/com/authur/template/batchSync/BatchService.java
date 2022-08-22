package com.authur.template.batchSync;




import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author authur
 * @description:
 */
public class BatchService {



    LinkedBlockingDeque<Request> queue = new LinkedBlockingDeque<>(20000);

    public Map<String, Object> queryBatch(String id){
        String serialNo = UUID.randomUUID().toString();
        //监听结果线程
        CompletableFuture<Map<String, Object>> future = new CompletableFuture<>();
        //绑定业务线程
        Request request = new Request();
        request.serialNo = serialNo;
        request.id = id ;
        request.future = future;

        return null;
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
                ArrayList<Object> params = new ArrayList<>();
                List<Request> objects = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    Request request = queue.poll();
                    Map<String, String> map = new HashMap<>();
//                    map.put("",)
                }
            }
        }, 100, 10, TimeUnit.MILLISECONDS);
//        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(1);
//        threadPool.scheduleAtFixedRate(()-> {
//            @Override
//            public void run() {
//                int size = queue.size();
//                if (size == 0) return;
//                //根据接口来封装批量参数
//                ArrayList<Object> params = new ArrayList<>();
//                List<Request> objects = new ArrayList<>();
//                for (int i = 0; i < size; i++) {
//                    Request request = queue.poll();
//                    Map<String, String> map = new HashMap<>();
////                    map.put("",)
//                }
//
//                System.out.println();
//            }
//        },100,10,TimeUnit.MILLISECONDS);


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

