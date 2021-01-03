package com.kk.cache.config;

import com.kk.cache.mybatis.*;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.format.support.DefaultFormattingConversionService;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Kk
 * @create: 2021-01-02 13:53
 **/
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    @Primary
    MyCacheManager cacheManager(MyCacheWriter writer){
        MyCacheConfiguration cacheConfig=new MyCacheConfiguration();
        cacheConfig.setCacheName("myCache");
        cacheConfig.setCacheNullValues(false);
        cacheConfig.setTtl(Duration.ofMinutes(1));
        cacheConfig.setUsePrefix(true);
        cacheConfig.setConversionService(new DefaultFormattingConversionService());
        Map<String, MyCacheConfiguration> map=new HashMap<>();
        map.put("myCache", cacheConfig);
        return MyCacheManager.build(writer,cacheConfig,map);
    }
}
