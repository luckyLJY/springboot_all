package com.kk.async.service.impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * @description:
 * @author: Kk
 * @create: 2020-12-12 20:16
 **/
@Service
public class AsyncService {

    @Async
    public Future<String> doOneSeconds(){
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(2000);
            long end = System.currentTimeMillis();
            String result = "doOneSeconds spend time: " + (end - start);
            return new AsyncResult<>(result);
        } catch (InterruptedException e) {
            return new AsyncResult<>("线程池异常: "+e.getMessage());
        }
    }

    @Async
    public Future<String> doTwoSeconds(){
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(2000);
            long end = System.currentTimeMillis();
            String result = "doTwoSeconds spend time: " + (end - start);
            return new AsyncResult<>(result);
        } catch (InterruptedException e) {
            return new AsyncResult<>("线程池异常: "+e.getMessage());
        }
    }

    public String testInterceptor() {
        try {
            Thread.sleep(3000);
            System.out.println("service: " + Thread.currentThread().getName() + " - " + Thread.currentThread().getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "interceptor";
    }
}
