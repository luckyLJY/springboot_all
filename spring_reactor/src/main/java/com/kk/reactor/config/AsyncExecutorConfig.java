package com.kk.reactor.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

/**
 * @description: 配置异步 该线程池用于@Async修饰的方法代理
 * @author: Kk
 * @create: 2020-12-19 11:41
 **/
@Configuration
@EnableAsync
@Slf4j
public class AsyncExecutorConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor=new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(100);
        taskExecutor.setThreadNamePrefix("AsyncExecutor-");
        taskExecutor.setKeepAliveSeconds(30);
        taskExecutor.initialize();
        return taskExecutor;
    }

    /**
     * 统一异步异常捕获
     *
     * @return
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncUncaughtExceptionHandler() {
            @Override
            public void handleUncaughtException(Throwable ex, Method method, Object... params) {
                System.out.println("异常捕获");
                log.warn(ex.getMessage());
            }
        };
    }

    @Bean("myExecutorPool1")
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
    }
}
