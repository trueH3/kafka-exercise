package com.example.kafkaExample.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Listeners {

    @KafkaListener(id = "myId", topics = "topic1")
    public void listen(String in) {
        System.out.println("Message read from topic1: " + in);
    }
}
