package com.kk.reactor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @description:
 * @author: Kk
 * @create: 2020-12-19 10:37
 **/
@Configuration
public class MyExecutorService {

    /*@Bean("myExecutorPool1")
    public Executor executor1(){
        ThreadPoolTaskExecutor taskExecutor=new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(100);
        taskExecutor.setThreadNamePrefix("MyExecutor1-");
        taskExecutor.setKeepAliveSeconds(30);
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Bean("myExecutorPool2")
    public Executor executor2(){
        ThreadPoolTaskExecutor taskExecutor=new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(100);
        taskExecutor.setThreadNamePrefix("MyExecutor2-");
        taskExecutor.setKeepAliveSeconds(30);
        taskExecutor.initialize();
        return taskExecutor;
    }*/
}
