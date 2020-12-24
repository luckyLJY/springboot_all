package com.kk.reactor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



/**
 * @description: 配置webmvc 主要配置拦截器
 * @author: Kk
 * @create: 2020-12-19 11:24
 **/
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

   /* @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setTaskExecutor(taskExecutor());
        //3秒则超时
        configurer.setDefaultTimeout(3000);
    }

    @Bean
    public AsyncTaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor taskExecutor=new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(100);
        taskExecutor.setThreadNamePrefix("kk");
        taskExecutor.setKeepAliveSeconds(30);
        taskExecutor.initialize();
        return taskExecutor;
    }*/

}
