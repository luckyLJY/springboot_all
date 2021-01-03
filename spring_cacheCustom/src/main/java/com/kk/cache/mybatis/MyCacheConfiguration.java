package com.kk.cache.mybatis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.convert.ConversionService;

import java.time.Duration;

/**
 * @author: Kk
 * @create: 2021-01-02 11:44
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyCacheConfiguration {
    /** 用于checkManager检查cache是否空闲过时 */
    private Duration ttl;
    private boolean cacheNullValues;
    private String cacheName;
    private boolean usePrefix;

    /*private  SerializationPair<String> keySerializationPair;
    private  SerializationPair<Object> valueSerializationPair;*/

    private ConversionService conversionService;
}
