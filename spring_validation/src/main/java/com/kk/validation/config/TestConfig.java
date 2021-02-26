package com.kk.validation.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * @author luokexiong
 * @version 1.0 2021/2/26
 * @since 1.9.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "test.demo")
@PropertySource(value = "classpath:test.yaml", factory = YamlPropertySourceFactory.class)
public class TestConfig {
    private MyConfig myConfig;

    @Data
    public static class MyConfig {
        Integer seconds;

        Date date;

        LocalDateTime localDateTime;

        LocalTime localTime;

        LocalDate localDate;
    }
}
