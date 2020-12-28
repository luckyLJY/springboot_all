package com.kk.redisson.service.Impl;

import com.kk.redisson.constant.RedisConstants;
import com.kk.redisson.service.ITaskQueueService;
import org.redisson.api.RBucket;
import org.redisson.api.RQueue;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author luokexiong
 * @version 1.0 2020/12/28
 * @since 1.0.0
 */
@Service
public class TaskQueueServiceImpl implements ITaskQueueService {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public boolean addOne(Long taskId) {
        // 存储在队列中
        RQueue<Long> queue = redissonClient.getQueue(RedisConstants.TASK);
        RScoredSortedSet<Long> scoredSortedSet = redissonClient.getScoredSortedSet(RedisConstants.TASK);
        return queue.add(taskId);
    }

    @Override
    public Optional<Long> qieruOmdex(long taskId) {
        RQueue<Long> queue = redissonClient.getQueue(RedisConstants.TASK);
        List<Long> results = queue.readAll();
        return Optional.of((long)results.indexOf(taskId));
    }

    @Override
    public List<Long> pollFirst(int size) {
        RQueue<Long> queue = redissonClient.getQueue(RedisConstants.TASK);
        return queue.poll(size);
    }
}
