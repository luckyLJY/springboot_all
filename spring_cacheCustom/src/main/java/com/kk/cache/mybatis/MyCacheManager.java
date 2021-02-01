package com.kk.cache.mybatis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.cache.Cache;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 缓存管理器
 *
 * @author: Kk
 * @create: 2021-01-02 13:22
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class MyCacheManager extends AbstractTransactionSupportingCacheManager {

    private MyCacheWriter cacheWriter;
    private MyCacheConfiguration defaultCacheConfig;
    private Map<String, MyCacheConfiguration> initialCacheConfiguration;
    private boolean allowInFlightCacheCreation = true;
    private boolean enableTransactions = true;

    public MyCacheManager(MyCacheWriter cacheWriter, MyCacheConfiguration defaultCacheConfig, Map<String, MyCacheConfiguration> initialCacheConfiguration) {
        this.cacheWriter = cacheWriter;
        this.defaultCacheConfig = defaultCacheConfig;
        this.initialCacheConfiguration = initialCacheConfiguration;
    }

    @Override
    public Cache getCache(String name) {
        return super.getCache(name);
    }

    @Override
    protected Collection<MyCache> loadCaches() {

        List<MyCache> caches = new LinkedList<>();

        for (Map.Entry<String, MyCacheConfiguration> entry : initialCacheConfiguration.entrySet()) {
            caches.add(createMyCache(entry.getKey(), entry.getValue()));
        }

        return caches;
    }

    /**
     * (non-Javadoc)
     *
     * @see org.springframework.cache.support.AbstractCacheManager#getMissingCache(java.lang.String)
     */
    @Override
    protected MyCache getMissingCache(String name) {
        return allowInFlightCacheCreation ? createMyCache(name, defaultCacheConfig) : null;
    }

    /**
     * (non-Javadoc)
     *
     * @see org.springframework.cache.support.AbstractCacheManager#loadCaches()
     */
    private MyCache createMyCache(String key, MyCacheConfiguration config) {
        return new MyCache(allowInFlightCacheCreation, key, cacheWriter, config != null ? config : defaultCacheConfig);
    }

    public static MyCacheManager build(MyCacheWriter writer, MyCacheConfiguration config, Map<String, MyCacheConfiguration> map) {
        MyCacheManager cacheManager = new MyCacheManager(writer, config, map);
        cacheManager.setTransactionAware(true);
        cacheManager.initializeCaches();
        return cacheManager;
    }
}
