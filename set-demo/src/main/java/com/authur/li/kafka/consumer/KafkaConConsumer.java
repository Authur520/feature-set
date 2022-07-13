package com.authur.li.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * @Description:
 * @Author: jibing.Li
 * @Date: 2022/6/30 17:25
 */


public class KafkaConConsumer {

    private static KafkaConsumer kafkaConsumer;
    private static final String TOPIC = "kafka_test";

    public KafkaConConsumer(){
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
        kafkaConsumer = new KafkaConsumer(properties);
    }

    public void pullKafkaConsumer(){
        kafkaConsumer.subscribe(Collections.singletonList(TOPIC));


        while (true) {
            ConsumerRecords consumerRecords = kafkaConsumer.poll(Duration.ofMillis(200));
            consumerRecords.forEach(c->{
                ConsumerRecord<String,String> consumerRecord = (ConsumerRecord) c;
                String value = consumerRecord.value();
                System.out.println("print:" + value);
            });

        }



    }



}
