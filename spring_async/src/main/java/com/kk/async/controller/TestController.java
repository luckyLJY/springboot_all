package com.kk.async.controller;

import com.kk.async.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @description:
 * @author: Kk
 * @create: 2020-12-12 20:14
 **/
@RestController
public class TestController {
    @Autowired
    private AsyncService asyncService;

    //一个请求开启两个异步线程
    @GetMapping("testAsync")
    public String async() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        Future<String> f1 = asyncService.doOneSeconds();
        Future<String> f2 = asyncService.doTwoSeconds();
        while (!f1.isDone() || !f2.isDone()){
            //当所有异步任务完成以后
            if (f1.isDone() && f2.isDone()){
                break;
            }
        }
        long end = System.currentTimeMillis();
        String time="总耗时："+(end-start);
        System.out.println(Thread.currentThread().getName()+"---->"+time);
        return "ok";
    }
}
