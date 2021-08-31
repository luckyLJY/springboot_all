package com.kk.async.controller.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异步拦截器
 *
 * @author kkmystery
 * @version 1.0 2021/6/21
 * @since 2.2.5
 */
public class AsyncInterceptor implements HandlerInterceptor {

    /*@Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("afterConcurrentHandlingStarted: " + Thread.currentThread().getName() + " - " + Thread.currentThread().getId());
    }*/

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle: " + Thread.currentThread().getName() + " - " + Thread.currentThread().getId());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle: " + Thread.currentThread().getName() + " - " + Thread.currentThread().getId());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion: " + Thread.currentThread().getName() + " - " + Thread.currentThread().getId());
    }
}
