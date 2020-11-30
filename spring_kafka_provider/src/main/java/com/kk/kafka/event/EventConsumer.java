package com.kk.kafka.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EventConsumer {

    @KafkaListener(topics = "test")
    public void acceptMQ(String message){
        System.out.println("message:" + message);
    }
}

