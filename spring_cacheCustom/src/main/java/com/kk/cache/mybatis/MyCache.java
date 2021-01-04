package com.kk.cache.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.core.convert.ConversionService;

import java.util.concurrent.Callable;

/**
 * 配置cache 基于数据库缓存
 *
 * @author: Kk
 * @create: 2021-01-02 11:24
 **/
@Slf4j
public class MyCache extends AbstractValueAdaptingCache {

    private String name;
    private MyCacheWriter cacheWriter;
    private MyCacheConfiguration cacheConfig;
    // 序列化规则
    private ConversionService conversionService;

    /**
     * 全参构造器
     *
     * @param allowNullValues   是否允许null值
     * @param name              缓存名称
     * @param cacheWriter       缓存写入方式（jdbc模式）,cache重写的操作方法需要cacheWriter来完成
     * @param cacheConfig       缓存配置
     * @param conversionService 序列化转换器
     */
    protected MyCache(boolean allowNullValues, String name, MyCacheWriter cacheWriter, MyCacheConfiguration cacheConfig, ConversionService conversionService) {
        super(allowNullValues);
        this.name = name;
        this.cacheWriter = cacheWriter;
        this.cacheConfig = cacheConfig;
        this.conversionService = conversionService;
    }

    public MyCache(boolean allowInFlightCacheCreation, String key, MyCacheWriter cacheWriter, MyCacheConfiguration myCacheConfiguration) {
        this(allowInFlightCacheCreation, key, cacheWriter, myCacheConfiguration,
                myCacheConfiguration.getConversionService());
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return cacheWriter;
    }

    @Override
    public ValueWrapper get(Object key) {
        ValueWrapper result = null;
        Object thevalue = cacheWriter.get(key);
        if (thevalue != null) {
            log.info("[" + name + "]got cache, key:" + key);
            result = new SimpleValueWrapper(thevalue);
        } else {
            log.info("[" + name + "]missing cache, key:" + key);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Object key, Class<T> type) {
        ValueWrapper vw = get(key);
        if (vw == null) {
            return null;
        }
        return (T) vw.get();
    }

    @Override
    protected Object lookup(Object key) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        ValueWrapper vw = get(key);
        if (vw == null) {
            return null;
        }
        return (T) vw.get();
    }

    @Override
    public void put(Object key, Object value) {
        cacheWriter.put(key, value);
    }

    @Override
    public Cache.ValueWrapper putIfAbsent(Object key, Object value) {
        Object existing = this.cacheWriter.putIfAbsent(key, value);
        return (existing != null ? new SimpleValueWrapper(existing) : null);
    }

    @Override
    public void evict(Object key) {
        cacheWriter.remove(key);
    }

    @Override
    public boolean evictIfPresent(Object key) {
        return cacheWriter.remove(key);
    }

    @Override
    public void clear() {
        cacheWriter.clear();
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyCacheWriter getCacheWriter() {
        return cacheWriter;
    }

    public void setCacheWriter(MyCacheWriter cacheWriter) {
        this.cacheWriter = cacheWriter;
    }

    public MyCacheConfiguration getCacheConfig() {
        return cacheConfig;
    }

    public void setCacheConfig(MyCacheConfiguration cacheConfig) {
        this.cacheConfig = cacheConfig;
    }

    public ConversionService getConversionService() {
        return conversionService;
    }

    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }
}
