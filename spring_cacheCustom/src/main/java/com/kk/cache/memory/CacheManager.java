package com.kk.cache.memory;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 缓存管理类
 * @author: Kk
 * @create: 2021-01-02 10:05
 **/
public class CacheManager {
    // 默认单例
    private CacheManager(){}
    private static class  Singleton{
        private static CacheManager instance = new CacheManager();
    }
    public static CacheManager getInstance() {
        return Singleton.instance;
    }

    public static Map<String, Cache> MAP_NAMES_CACHE = new HashMap<String, Cache>();

    // 存放 取出 缓存对象
    public Cache getCache(String CacheName){
        Cache cache = MAP_NAMES_CACHE.get(CacheName);
        return cache;
    }

    public void putCache(Cache cache){
        if(cache != null  && !MAP_NAMES_CACHE.containsKey(cache.getName())){
            MAP_NAMES_CACHE.put(cache.getName(), cache);
        }
    }

    // 移除
    public void remove(String cacheName){
        Cache c = MAP_NAMES_CACHE.remove(cacheName);
        c.destory();
    }

    // 关闭所有缓存
    public void shutDown(){
        removeAllCaches();
        MAP_NAMES_CACHE.clear();
    }

    // 移除所有
    public void removeAllCaches(){
        String [] cacheNames = getCacheNames();
        for(String cacheName : cacheNames){
            remove(cacheName);
        }
    }

    // 获得名字
    public String[] getCacheNames(){
        return MAP_NAMES_CACHE.keySet().toArray(new String[0]);
    }

}
