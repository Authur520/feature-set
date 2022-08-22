package com.authur.template.batchSync;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author authur
 * @description:
 */
public class Request {

    String serialNo;
    CompletableFuture<Map<String, Object>> future;
    String id;


}
