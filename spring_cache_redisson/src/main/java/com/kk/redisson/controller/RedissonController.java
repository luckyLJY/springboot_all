package com.kk.redisson.controller;

import com.kk.redisson.service.ITopicService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luokexiong
 * @version 1.0 2020/12/25
 * @since 1.0.0
 */
@RestController
@Slf4j
public class RedissonController {

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private ITopicService topicService;

    @GetMapping(value = "/redisson/{key}")
    public String redissonTest(@PathVariable("key") String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            lock.lock();
            Thread.sleep(10000);
        } catch (Exception e) {
            log.warn(e.getMessage());
        } finally {
            lock.unlock();
        }
        return "已解锁";
    }

    @GetMapping(value = "/redisson/send/{msg}")
    public String sendMsg(@PathVariable("msg") String msg) {
        topicService.sendMsg(msg);
        return msg;
    }
}
