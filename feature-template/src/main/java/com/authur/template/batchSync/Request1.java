package com.authur.template.batchSync;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author authur
 * @description:
 */
public class Request1 {

    private String serialNo;
    private CompletableFuture<Map<String, Object>> future;
    private String id;


}
