package com.kk.reactor.controller.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @description: 接口访问的一个过滤器，用于记录
 * @author: Kk
 * @create: 2020-12-19 10:20
 **/
@Component
@WebFilter
@Slf4j
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        //log
        log.info("当前访问的方法：{}, 路径：{}", httpServletRequest.getMethod(), httpServletRequest.getRequestURL());
        chain.doFilter(request, response);
    }
}
