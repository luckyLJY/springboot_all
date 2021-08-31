package com.kk.async.config;

import com.kk.async.controller.interceptor.AsyncInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvc配置
 *
 * @author kkmystery
 * @version 1.0 2021/6/21
 * @since 2.2.5
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AsyncInterceptor())
                .addPathPatterns("/test/**");
    }
}
