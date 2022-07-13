package com.authur.li.kafka.producer;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;


/**
 * @Description: Producer
 * @Author: jibing.Li
 * @Date: 2022/6/20 17:51
 */
public class KafkaConProducer implements Runnable {

    private String value;
    private Properties properties;
    private KafkaProducer producer;
    private static final String TOPIC = "Li_Request_Body_Topic";

    public KafkaConProducer(String value) {
        this.value = value;
        properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
//        properties.put("acks", "1");
        properties.put("retries", "3");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<String, String>(properties);

    }

    @Override
    public void run() {
        System.out.println("kafka currentThread Name:::"+Thread.currentThread().getName()+"kafka currentThread Id:::"+Thread.currentThread().getId());
        ProducerRecord record = new ProducerRecord(TOPIC,  value);
        producer.send(record);

    }


}
