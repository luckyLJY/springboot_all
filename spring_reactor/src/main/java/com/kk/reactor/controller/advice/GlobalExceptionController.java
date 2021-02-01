package com.kk.reactor.controller.advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

import java.util.Locale;

/**
 * @author luokexiong
 * @version 1.0 2020/12/29
 * @since 1.0.0
 */
@RestControllerAdvice
public class GlobalExceptionController {

    /**
     * 统一异步超时捕获
     *
     * @param e      异常
     * @param locale 区域
     * @return
     */
    @ExceptionHandler(AsyncRequestTimeoutException.class)
    public String timoutException(AsyncRequestTimeoutException e, Locale locale) {
        System.out.println("超时异常处理");
        return "请求超时...";
    }

    @ExceptionHandler(Exception.class)
    public String exception(Exception e, Locale locale) {
        System.out.println("任何异常处理");
        return "出现异常";
    }
}
