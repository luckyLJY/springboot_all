package com.kk.redisson.service.Impl;

import com.kk.redisson.common.SignUpResponse;
import com.kk.redisson.constant.RedisConstants;
import com.kk.redisson.service.IUserService;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author luokexiong
 * @version 1.0 2020/12/25
 * @since 1.0.0
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public SignUpResponse signUp(String email) {
        // 分布式锁
        RLock lock = redissonClient.getLock(RedisConstants.DISTRIBUTED_LOCK);
        try {
            boolean result = lock.tryLock(10, 8, TimeUnit.SECONDS);
            if (result){
                // 模拟处理业务
                System.out.println("正在登陆中。。。");
                Thread.sleep(2000);
                return new SignUpResponse(true, "", "登录成功");
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread() + " 发送错误");
            return new SignUpResponse(false, "", "登录错误");
        } finally {
            lock.unlock();
        }
        return new SignUpResponse(false, "", "登录错误");
    }

    @Override
    public boolean add(Long userId) {
        // 添加了新用户
        RBucket<Long> bucket = redissonClient.getBucket(RedisConstants.generateUserKey(userId));
        boolean flag = bucket.trySet(userId);
        if (flag) {
            System.out.println("延时队列");
            RQueue<Long> queue = redissonClient.getQueue(RedisConstants.USER_LIST);
            RDelayedQueue<Long> delayedQueue = redissonClient.getDelayedQueue(queue);
            delayedQueue.offer(userId, 10L, TimeUnit.SECONDS);
        }
        return flag;
    }

    @Override
    public List<Long> pollExpiredUsers() {
        RQueue<Long> queue = redissonClient.getQueue(RedisConstants.USER_LIST);
        List<Long> collect = queue.readAll();
        //RDelayedQueue<Long> delayedQueue = redissonClient.getDelayedQueue(queue);
        //delayedQueue.destroy();
        return collect;
    }

}
