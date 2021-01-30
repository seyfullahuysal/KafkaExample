package com.seyfullah.kafkaexample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void produce(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }

    public void produce(String topic, String key, String message) {
        kafkaTemplate.send(topic, key, message);
    }

    public void produce(String topic, int partition, String key, String message) {
        kafkaTemplate.send(topic, partition, key, message);
    }


    // use this if you dont wanna use offset
//    @KafkaListener(topicPartitions = @TopicPartition(topic = "topicName", partitions = { "0", "1" }))
    @KafkaListener(topicPartitions = @TopicPartition(topic = "${kafka.topic}",
            partitionOffsets = {@PartitionOffset(partition = "0", initialOffset = "0"),}))
    public void consumer(@Payload String message,
                         @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                         @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                         @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts) {
        System.out.println("partition : " + partition);
        System.out.println("message : " + message);
        System.out.println("topic : " + topic);
        System.out.println();

    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "${kafka.topic}", partitions = {"1"}))
    public void consumer2(@Payload String message,
                          @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                          @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts) {
        System.out.println("partition : " + partition);
        System.out.println("message : " + message);
        System.out.println("topic : " + topic);
        System.out.println();
    }

    // reads from all partitions
/*
    @KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.group.id}")
    public void consume(@Payload String message,
        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                        @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts) {
        System.out.println("message : " + message);
        System.out.println("key : " + key);
        System.out.println("partition : " + partition);
        System.out.println("topic : " + topic);
        System.out.println("ts : " + ts);
    }
*/
}
