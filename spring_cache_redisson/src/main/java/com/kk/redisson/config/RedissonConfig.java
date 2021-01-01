package com.kk.redisson.config;

import com.kk.redisson.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.codec.FstCodec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.config.TransportMode;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 16384 slot
 *
 * @author luokexiong
 * @version 1.0 2020/12/24
 * @since 1.0.0
 */
@Configuration
@EnableCaching
@EnableConfigurationProperties(RedisProperties.class)
public class RedissonConfig {

    @Autowired
    private RedisProperties redisProperties;

    /**
     * 单机模式配置
     *
     * @return RedissonClient
     */
    @Bean(destroyMethod = "shutdown")
    @ConditionalOnProperty(name = "spring.redis.mode", havingValue = "single")
    RedissonClient redissonSingle() {
        Config config = new Config();
        String node = redisProperties.getSingle().getAddress();
        node = node.startsWith("redis://") ? node : "redis://" + node;
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(node)
                .setDatabase(redisProperties.getDatabase())
                .setTimeout(redisProperties.getTimeout())
                .setConnectTimeout(redisProperties.getPool().getConnectTimeout())
                .setConnectionPoolSize(redisProperties.getPool().getSize())
                .setConnectionMinimumIdleSize(redisProperties.getPool().getMinimumIdleSize());
        if (StringUtils.isNotBlank(redisProperties.getPassword())) {
            serverConfig.setPassword(redisProperties.getPassword());
        }
        // 序列化策略
        config.setCodec(new JsonJacksonCodec(JsonUtil.jsr310ObjectMapper()));
        return Redisson.create(config);
    }

    /**
     * 缓存管理器
     *
     * @param redissonClient
     * @return CacheManager
     */
    @Bean
    RedissonSpringCacheManager redissonSpringCacheManager(RedissonClient redissonClient) {
        Map<String, CacheConfig> config = new HashMap<String, CacheConfig>();
        // 创建一个名称为"testMap"的缓存，过期时间ttl为24分钟，同时最长空闲时maxIdleTime为12分钟。
        CacheConfig cacheConfig = new CacheConfig(24 * 60 * 1000, 12 * 60 * 1000);
        cacheConfig.setMaxSize(1000);
        config.put("testMap", cacheConfig);
        return new RedissonSpringCacheManager(redissonClient, config, new JsonJacksonCodec(JsonUtil.jsr310ObjectMapper()));
    }
}
