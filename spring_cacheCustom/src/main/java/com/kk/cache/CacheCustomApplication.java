package com.kk.cache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author: Kk
 * @create: 2021-01-02 12:44
 **/
@SpringBootApplication
@ComponentScan(basePackages = "com.kk")
@MapperScan("com.kk.cache.mybatis.mapper")
public class CacheCustomApplication {
    public static void main(String[] args) {
        SpringApplication.run(CacheCustomApplication.class, args);
    }
}
