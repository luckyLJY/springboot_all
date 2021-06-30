package com.kk.redisson.listenter;

import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * topic订阅监听器
 *
 * @author kkmystery
 * @version 1.0 2021/6/30
 * @since 1.0.0
 */
@Component
public class TopicListener implements ApplicationRunner, Ordered {
    private static final Logger LOGGER = LoggerFactory.getLogger(TopicListener.class);

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        RTopic topic = redissonClient.getTopic("wechat");
        topic.addListener(String.class, new MessageListener<String>() {
            @Override
            public void onMessage(CharSequence charSequence, String msg) {
                LOGGER.info("Redisson监听器收到消息:{}", msg);
            }
        });
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
