package com.kk.redisson.service;

import com.kk.redisson.constant.RedisConstants;
import com.kk.redisson.entity.User;
import com.kk.redisson.util.RedisUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBitSet;
import org.redisson.api.RBucket;
import org.redisson.api.RHyperLogLog;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MfaceCodeServiceImplTest {
    @Autowired
    private IMfaceCodeService mfaceCodeService;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private RedisUtils redisUtils;

    @BeforeEach
    void init() {
        RBucket<String> bucket = redissonClient.getBucket(RedisConstants.generateUserCodeKey(100L));
        bucket.set("123456", 5, TimeUnit.MINUTES);

    }

    @AfterEach
        //@Test
    void clear() {
        RBucket<String> bucket = redissonClient.getBucket(RedisConstants.generateUserCodeKey(100L));
        bucket.delete();
    }

    void clear(long userId) {
        RBucket<String> bucket = redissonClient.getBucket(RedisConstants.generateUserCodeKey(userId));
        bucket.delete();
    }

    @Test
    void generate() {
        assertEquals(true, mfaceCodeService.generate(101L));
        clear(101L);

        assertEquals(true, mfaceCodeService.generate(101L));
    }

    @Test
    void valid() {
        assertEquals(true, mfaceCodeService.valid(100L, "123456"));
    }

    @Test
    void test1(){
        //统计
        /*RBitSet bitSet = redissonClient.getBitSet("DATE-COUNT-USER");
        bitSet.set(1L);
        bitSet.set(2L);
        bitSet.set(3L);
        bitSet.set(3L);
        bitSet.set(3L);
        bitSet.set(5L);
        System.out.println(bitSet.cardinality());
        System.out.println(bitSet.length());*/

        RHyperLogLog<Long> hyperLogLog = redissonClient.getHyperLogLog("DATE-COUNT-USER");
        hyperLogLog.add(1L);
        hyperLogLog.add(2L);
        hyperLogLog.add(3L);
        hyperLogLog.add(4L);
        hyperLogLog.add(4L);
        hyperLogLog.add(4L);

        System.out.println(hyperLogLog.count());
    }
}