package com.kk.kafka.event;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestDemo {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Test
    void contextLoads() {
        kafkaTemplate.send("test","haha");
    }
}
