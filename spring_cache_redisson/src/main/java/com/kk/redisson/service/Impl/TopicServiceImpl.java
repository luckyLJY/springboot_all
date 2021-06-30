package com.kk.redisson.service.Impl;

import com.kk.redisson.service.ITopicService;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * {@link ITopicService}
 *
 * @author kkmystery
 * @version 1.0 2021/6/30
 * @since 1.0.0
 */
@Service
public class TopicServiceImpl implements ITopicService {
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void sendMsg(String msg) {
        if (StringUtils.isNotBlank(msg)) {
            RTopic topic = redissonClient.getTopic("wechat");
            topic.publish(msg);
        }
    }
}
