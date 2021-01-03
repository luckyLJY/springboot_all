package com.kk.cache.memory;

/**
 * @description:
 *   这些属性来自于ehcache
 *   属于缓存级别的 一些控制
 * @author: Kk
 * @create: 2021-01-02 10:04
 **/
public class CacheConfiguration {
    // 缓存的名字
    private String cacheName;
    // 是否需要缓存 循环检查
    private Boolean isNeedCacheCheckListener = false;

    public Boolean getIsNeedCacheCheckListener() {
        return isNeedCacheCheckListener;
    }
    public void setIsNeedCacheCheckListener(Boolean isNeedCacheCheckListener) {
        this.isNeedCacheCheckListener = isNeedCacheCheckListener;
    }
    // 内存最大缓存对象数
    private Integer maxElementsInMemory;

    // 缓存元素是否永久有效，一旦设置true ,失效时间 将不起作用,默认false
    private Boolean eternal = false;

    // 设置缓存在失效前的允许闲置时间。仅当缓存不是永久有效时使用(timeToLiveSeconds != 0)
    // 可选属性，默认值是0，也就是可闲置时间无穷大。
    private Integer timeToIdleSeconds = 0;

    // 设置缓存长允许存活时间，最大时间介于创建时间和失效时间之间.
    // 仅当缓存不是永久有效时使用，默认值是0，也就是缓存存活时间无穷大。
    private Integer timeToLiveSeconds = 0;
    // 对象检测线程运行的时间间隔。表示对象状态的线程多长时间运行一次
    // 这里暂时用来对内存对象的检查
    private Integer diskExpiryThreadIntervalSeconds = 120;

    // 如果缓存满了，执行清空策略
    // 可选FIFO,LFU 这里要用枚举类型
    // FIFO ：先进先出
    // LFU:最少使用,一直以来最少被使用的，缓存缓存有一个hit属性，清除hit最小的
    // LRU:最近最少使用，缓存元素有个时间戳，当缓存容量满了，而又需要腾出新地方
    // 来缓存的时候，那么现有的缓存缓存中时间戳离当前时间最远的缓存将被清除缓存
    private String memoryStoreEvictionPolicy = EvictionType.LRU.name();


    // 暂时未用
    // 当缓存数量达到最大值时，允许将缓存写入到磁盘
    private Boolean overflowToDisk = false;
    // 磁盘中最大的缓存对象数，若是0表示无穷大
    private Integer maxElementsOnDisk = 0;
    // 是否在磁盘上持久化，默认false
    private Boolean diskPersistent = false;

    public CacheConfiguration() {
    }
    public CacheConfiguration(String cacheName,Integer maxElementsInMemory,
                              Boolean eternal,Integer timeToIdleSeconds,Integer timeToLiveSeconds,
                              Integer diskExpiryThreadIntervalSeconds) {
        this.cacheName = cacheName;
        this.maxElementsInMemory = maxElementsInMemory;
        this.eternal = eternal;
        this.timeToIdleSeconds = timeToIdleSeconds;
        this.timeToLiveSeconds = timeToLiveSeconds;
        this.diskExpiryThreadIntervalSeconds = diskExpiryThreadIntervalSeconds;
    }

    public String getCacheName() {
        return cacheName;
    }
    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }
    public Integer getMaxElementsInMemory() {
        return maxElementsInMemory;
    }
    public void setMaxElementsInMemory(Integer maxElementsInMemory) {
        this.maxElementsInMemory = maxElementsInMemory;
    }
    public Boolean getEternal() {
        return eternal;
    }
    public void setEternal(Boolean eternal) {
        this.eternal = eternal;
    }
    public Integer getTimeToIdleSeconds() {
        return timeToIdleSeconds;
    }
    public void setTimeToIdleSeconds(Integer timeToIdleSeconds) {
        this.timeToIdleSeconds = timeToIdleSeconds;
    }
    public Integer getTimeToLiveSeconds() {
        return timeToLiveSeconds;
    }
    public void setTimeToLiveSeconds(Integer timeToLiveSeconds) {
        this.timeToLiveSeconds = timeToLiveSeconds;
    }
    public Boolean getOverflowToDisk() {
        return overflowToDisk;
    }
    public void setOverflowToDisk(Boolean overflowToDisk) {
        this.overflowToDisk = overflowToDisk;
    }
    public Integer getMaxElementsOnDisk() {
        return maxElementsOnDisk;
    }
    public void setMaxElementsOnDisk(Integer maxElementsOnDisk) {
        this.maxElementsOnDisk = maxElementsOnDisk;
    }
    public Boolean getDiskPersistent() {
        return diskPersistent;
    }
    public void setDiskPersistent(Boolean diskPersistent) {
        this.diskPersistent = diskPersistent;
    }
    public Integer getDiskExpiryThreadIntervalSeconds() {
        return diskExpiryThreadIntervalSeconds;
    }
    public void setDiskExpiryThreadIntervalSeconds(
            Integer diskExpiryThreadIntervalSeconds) {
        this.diskExpiryThreadIntervalSeconds = diskExpiryThreadIntervalSeconds;
    }
    public String getMemoryStoreEvictionPolicy() {
        return memoryStoreEvictionPolicy;
    }
    public void setMemoryStoreEvictionPolicy(String memoryStoreEvictionPolicy) {
        this.memoryStoreEvictionPolicy = memoryStoreEvictionPolicy;
    }
}
