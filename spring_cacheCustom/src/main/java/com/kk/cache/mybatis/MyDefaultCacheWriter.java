package com.kk.cache.mybatis;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kk.cache.mybatis.entity.Cache;
import com.kk.cache.mybatis.mapper.CacheMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * 默认缓存写入器
 *
 * @author: Kk
 * @create: 2021-01-02 12:28
 **/
@Component
public class MyDefaultCacheWriter implements MyCacheWriter {
    @Resource
    CacheMapper mapper;

    public MyDefaultCacheWriter() {
    }

    public CacheMapper getMapper() {
        return mapper;
    }


    @Override
    public void clean(String name, String pattern) {
        System.out.println("clean(String name, String pattern)");
    }

    @Override
    public void put(Object key, Object value, Duration ttl) {
        System.out.println("put(Object key, Object value, Duration ttl)");
    }

    @Override
    public void put(Object key, Object value) {
        System.out.println(" put(Object key, Object value)");
        if (null == value){
            return;
        }
        Cache cache = mapper.selectOne(new QueryWrapper<Cache>().eq("key_name", key));
        if (null != cache){
            cache.setValueElement(JSONUtil.toJsonStr(value));
            mapper.update(cache,new QueryWrapper<Cache>().eq("key_name",key));
            return;
        }
        Cache newCache = new Cache().setKeyName((String) key)
                .setValueElement(JSONUtil.toJsonStr(value))
                .setClassName(value.getClass().getName())
                .setStatus(true);
        mapper.insert(newCache);
    }

    @Override
    public Object get(Object key) {
        //System.out.println("get(Object key)");
        Cache cache = mapper.selectOne(new QueryWrapper<Cache>().eq("key_name", key));
        if (null != cache) {
            try {
                Class<?> aClass = Class.forName(cache.getClassName());
                return JSONUtil.toBean(cache.getValueElement(), aClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Object putIfAbsent(Object key, Object value) {
        System.out.println("putIfAbsent(Object key, Object value)");
        return null;
    }

    @Override
    public Object putIfAbsent(Object key, Object value, Duration ttl) {
        System.out.println("putIfAbsent(Object key, Object value, Duration ttl)");
        return null;
    }

    @Override
    public boolean remove(Object key) {
        System.out.println("remove(Object key)");
        return mapper.delete(new QueryWrapper<Cache>().eq("key_name", key))>0;
    }

    @Override
    public void clear() {
        System.out.println(" clear()");
    }
}
