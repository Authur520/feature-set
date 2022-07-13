package com.authur.li.kafka.consumer;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * @Description:
 * @Author: jibing.Li
 * @Date: 2022/6/20 17:52
 */

@Component
@Slf4j
public class KafkaConsumer {


//    @KafkaListener(topics = {"Request_AccountId_DealerId"})
    public void KafkaListener(String message){
        try {
            if (StringUtils.isNotBlank(message)){
                Map<String, Object> map = JSONObject.parseObject(message, Map.class);
                Object accountId = map.get("AccountId");
                Object dealerId = map.get("dealerId");
                System.out.println("AccountId..."+ accountId);
                System.out.println("dealerId..."+dealerId);
                System.out.println(map);
            }
        }catch (Exception e){
            log.error(message+"Ïû·ÑÒì³££¡");
        }


    }


}
