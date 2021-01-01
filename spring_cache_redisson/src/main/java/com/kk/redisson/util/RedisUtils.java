package com.kk.redisson.util;

import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.codec.FstCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 封装操作
 *
 * @author luokexiong
 * @version 1.0 2020/12/25
 * @since 1.0.0
 */
@Component
public class RedisUtils {

    @Autowired
    private RedissonClient redissonClient;

    public <V> V set(String key, V value) {
        RBucket<V> bucket = redissonClient.getBucket(key);
        bucket.set(value);
        return value;
    }

    public <V> V get(String key) {
        RBucket<V> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    public <V> V setMap(String mapName, String key, V value) {
        RMap<String, V> map = redissonClient.getMap(mapName);
        return map.put(key, value);
    }

    public <V> V getMap(String mapName, String key) {
        RMap<String, V> map = redissonClient.getMap(mapName);
        return map.get(key);
    }
}
