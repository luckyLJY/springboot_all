package com.kk.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步线程池配置
 *
 * @author kkmystery
 * @version 1.0 2021/7/8
 * @since 1.0.0
 */
@Configuration
@EnableAsync(proxyTargetClass = true)
@Slf4j
public class ExecutorConfig {

    /**
     * admin Controller线程池
     *
     * @return 线程池
     */
    @Bean(value = "adminControllerBizExecutor", destroyMethod = "shutdown")
    public Executor adminControllerBizExecutor() {
        ExecutorProperties biz = adminControllerBizProperties();;
        return createNormalExecutor(biz);
    }

    /**
     * 异步Controller线程池
     *
     * @return 线程池
     */
    @Bean(value = "eventExecutor", destroyMethod = "shutdown")
    public Executor eventExecutor() {
        ExecutorProperties event = asyncEventProperties();
        return createNormalExecutor(event);
    }

    @Bean
    public ExecutorProperties adminControllerBizProperties() {
        return new ExecutorProperties(3, true, 4, 100, "admin-async-pool", 5, true);
    }

    @Bean
    public ExecutorProperties asyncEventProperties() {
        return new ExecutorProperties(3, true, 4, 100, "event-async-pool", 5, true);
    }

    private Executor createNormalExecutor(ExecutorProperties properties) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(properties.getCorePoolSize());
        executor.setAllowCoreThreadTimeOut(properties.getAllowCoreThreadTimeout());
        executor.setMaxPoolSize(properties.getMaxPoolSize());
        executor.setQueueCapacity(properties.getQueueCapacity());
        executor.setThreadNamePrefix(properties.getThreadNamePrefix());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();
        return executor;
    }
}
