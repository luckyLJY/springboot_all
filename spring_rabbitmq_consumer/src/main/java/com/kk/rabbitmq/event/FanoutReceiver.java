package com.kk.rabbitmq.event;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author : K k
 * @date : 12:19 2020/11/7
 */
@Component
public class FanoutReceiver {

    @RabbitListener(queues = {"fanout.A"})
    public void fanoutA(Map testMessage) {
        System.out.println("FanoutReceiverA消费者收到消息  : " +testMessage.toString());
    }
    @RabbitListener(queues = {"fanout.B"})
    public void fanoutB(Map testMessage) {
        System.out.println("FanoutReceiverB消费者收到消息  : " +testMessage.toString());
    }
    @RabbitListener(queues = {"fanout.C"})
    public void fanoutC(Map testMessage) {
        System.out.println("FanoutReceiverC消费者收到消息  : " +testMessage.toString());
    }
}
