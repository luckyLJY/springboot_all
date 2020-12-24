package com.kk.redisson.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author luokexiong
 * @version 1.0 2020/12/24
 * @since 1.0.0
 */
@Service
public class RedisServiceImpl {

    @Autowired
    private StringRedisTemplate redisTemplate;
}
