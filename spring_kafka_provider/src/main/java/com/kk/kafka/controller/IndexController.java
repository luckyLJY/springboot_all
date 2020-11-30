package com.kk.kafka.controller;

import com.kk.kafka.event.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Kk
 * @create: 2020-11-29 17:39
 **/
@RestController
public class IndexController {
    @Autowired
    private EventProducer producer;

    @RequestMapping("send")
    public String send(){
        producer.sendMsg();
        return "ok";
    }
}
