package com.kk.redisson.service.Impl;

import cn.hutool.core.util.RandomUtil;

import com.kk.redisson.service.IMfaceCodeService;
import com.kk.redisson.constant.RedisConstants;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author luokexiong
 * @version 1.0 2020/12/24
 * @since 1.0.0
 */
@Service
public class MfaceCodeServiceImpl implements IMfaceCodeService {

    /** 使用RedissonClient操作redis */
    @Autowired
    private RedissonClient redissonClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean generate(long userId) {
        // 生成6位
        String randomCode = RandomUtil.randomNumbers(6);
        String userCodeKey = RedisConstants.generateUserCodeKey(userId);
        //RMap<String, String> myMap = redissonClient.getMap(RedisUtils.CLIENT_MAP_NAME);
        RBucket<String> map = redissonClient.getBucket(userCodeKey);
        RFuture<Void> future = map.setAsync(randomCode, 5, TimeUnit.MINUTES);
        return future.isSuccess();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean valid(long userId, String code) {
        // 获取验证码
        String userCodeKey = RedisConstants.generateUserCodeKey(userId);
        // 获取缓存中的value
        //RMap<String, String> map = redissonClient.getMap(RedisUtils.CLIENT_MAP_NAME);
        RBucket<String> map = redissonClient.getBucket(userCodeKey);
        String userCode = map.get();
        boolean result = StringUtils.isNotBlank(code) && StringUtils.isNotBlank(userCode) && code.equals(userCode);
        return result;
    }
}
