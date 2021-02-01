package com.kk.cache.mybatis;

import org.springframework.lang.Nullable;

import java.time.Duration;

/**
 * 定义cacheWriter的方法接口
 * 需要连接jdbc
 * 参数name是为了锁
 *
 * @author: Kk
 * @create: 2021-01-02 11:44
 **/
public interface MyCacheWriter {

    /**
     * Remove all keys following the given pattern.
     *
     * @param name    The cache name must not be {@literal null}.
     * @param pattern The pattern for the keys to remove. Must not be {@literal null}.
     */
    void clean(String name, String pattern);

    void put(Object key, Object value, @Nullable Duration ttl);

    /**
     * 实际调用put
     *
     * @param key   key
     * @param value value
     */
    void put(Object key, Object value);

    /**
     * 实际调用get
     *
     * @param key key
     * @return value
     */
    Object get(Object key);

    /**
     * 实际调用putIfAbsent
     *
     * @param key   key
     * @param value value
     * @return value
     */
    Object putIfAbsent(Object key, Object value);

    Object putIfAbsent(Object key, Object value, @Nullable Duration ttl);

    boolean remove(Object key);

    void clear();

}
