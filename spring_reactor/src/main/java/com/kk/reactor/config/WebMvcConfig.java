package com.kk.reactor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.async.TimeoutDeferredResultProcessingInterceptor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;


/**
 * @description: 配置webmvc 主要配置拦截器
 * @author: Kk
 * @create: 2020-12-19 11:24
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final long DEFAULT_TIMEOUT_MILLIS = 30000;

    @Value("${async.request.timeout}")
    private Duration timeout = Duration.ofMillis(DEFAULT_TIMEOUT_MILLIS);

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setDefaultTimeout(timeout.toMillis());
        configurer.registerDeferredResultInterceptors(asyncTimeoutInterceptor());
    }

    @Bean
    public TimeoutDeferredResultProcessingInterceptor asyncTimeoutInterceptor() {
        return new TimeoutDeferredResultProcessingInterceptor();
    }
}
