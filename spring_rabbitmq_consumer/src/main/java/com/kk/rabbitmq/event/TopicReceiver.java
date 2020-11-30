package com.kk.rabbitmq.event;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author : K k
 * @date : 12:06 2020/11/7
 */
@Component
public class TopicReceiver {

    @RabbitListener(queues = "topic.man")
    public void getManMessage(Map testMessage) {
        System.out.println("TopicManReceiver消费者收到消息  : " + testMessage.toString());
    }

    @RabbitListener(queues = "topic.woman")
    public void getWomanMessage(Map testMessage) {
        System.out.println("TopicWomanReceiver消费者收到消息  : " + testMessage.toString());
    }
}
