package com.kk.kafka.event;

import com.alibaba.fastjson.JSON;
import com.kk.kafka.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description:
 * @author: Kk
 * @create: 2020-11-29 13:23
 **/
@Component
public class EventProducer {

    @Resource
    private KafkaTemplate<String,String> kafkaTemplate;

    public void sendMsg(){
        User user=new User();
        user.setId(1);
        user.setUsername("kk");
        user.setPassword("123");
        kafkaTemplate.send("test", JSON.toJSONString(user));
    }

}
