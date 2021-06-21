package com.kk.async.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * @description:
 * Spring中通过任务执行器TaskExecutor来实现多线程和并发编程。
 * 使用ThreadPoolTaskExecutor可实现一个基于线程池的TaskExecutor。
 * 因为实际开发中任务一般是异步的（即非阻塞的），所以要在配置类中@EnableAsync，
 * 并在实际执行的Bean方法中使用@Async来声明这是一个异步方法。
 * @author: Kk
 * @create: 2020-12-12 20:07
 **/
@EnableAsync
@Configuration
public class TaskExecutorConfig implements AsyncConfigurer {

    /**
     * 配置类继承AsyncConfigurer接口并重写getAsyncExecutor方法，并返回ThreadPoolTaskExecutor，
     * 这样我们就获得了一个基于线程池的TaskExecutor
     */
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(3);
        taskExecutor.setMaxPoolSize(5);
        taskExecutor.setQueueCapacity(10);
        //new ThreadPoolExecutor.DiscardPolicy() 丢弃不抛异常
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        taskExecutor.initialize();
        return taskExecutor;
    }

    /**
     * AsyncUncaughtExceptionHandler：用来处理从异步方法抛出的未被捕获的exceptions
     * An asynchronous method usually returns a Future instance that gives access to the underlying exception. When the method does not provide that return type, this handler can be used to managed such uncaught exceptions.
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        AsyncUncaughtExceptionHandler handler=new AsyncUncaughtExceptionHandler() {
            @Override
            public void handleUncaughtException(Throwable ex, Method method, Object... params) {
                System.out.println("出现异常："+ex.getMessage());
            }
        };
        return handler;
    }
}
