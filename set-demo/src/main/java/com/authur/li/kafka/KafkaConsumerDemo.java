package com.authur.li.kafka;


import com.authur.li.kafka.consumer.KafkaConConsumer;

/**
 * @Description:
 * @Author: jibing.Li
 * @Date: 2022/7/1 11:04
 */
public class KafkaConsumerDemo {

    public static void main(String[] args) {
        KafkaConConsumer kafkaConConsumer = new KafkaConConsumer();
        kafkaConConsumer.pullKafkaConsumer();
    }
}
