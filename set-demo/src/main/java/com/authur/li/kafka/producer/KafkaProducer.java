//package com.authur.li.kafka.producer;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//
///**
// * @Description:
// * @Author: jibing.Li
// * @Date: 2022/6/29 16:32
// */
//public class KafkaProducer implements Runnable {
//
//    private String value;
//    private static final String topic = "kafka_test";
//
//    public KafkaProducer(String value,KafkaTemplate kafkaTemplate) {
//        this.value = value;
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    @Autowired
//    private KafkaTemplate kafkaTemplate;
//
//    @Override
//    public void run() {
//        KafkaProducer.this.kafkaTemplate.send(topic,value);
//    }
//}
