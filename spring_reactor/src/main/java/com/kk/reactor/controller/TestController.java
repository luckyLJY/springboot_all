package com.kk.reactor.controller;

import com.kk.reactor.common.R;
import com.kk.reactor.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

/**
 * @description:
 * @author: Kk
 * @create: 2020-12-19 10:32
 **/
@RestController
@Slf4j
public class TestController {

    //    @Autowired
//    @Qualifier(value = "myExecutorPool")
    @Resource(name = "myExecutorPool1")
    private Executor executor1;
    @Resource(name = "myExecutorPool2")
    private Executor executor2;
    @Autowired
    private TestService testService;

    /**
     * 调用普通同步方法
     *
     * @return
     */
    @GetMapping("/test")
    public R test() {
        log.info("普通同步模式");
        return R.ok(testService.actionSync());
    }

    @GetMapping("/test1")
    public Callable<R> test1() {
        log.info("Callable调用同步方法");
        return new Callable<R>() {
            @Override
            public R call() throws Exception {
                //处理业务
                return R.ok().setMessage(testService.actionAsync());
            }
        };
    }

    @GetMapping("/test2")
    public CompletableFuture<R> test2() {
        CompletableFuture<R> result = CompletableFuture.supplyAsync(() -> {
            testService.actionSync();
            return R.ok();
        }, executor1);
        return result;
    }

    /**
     * 模拟超时处理
     *
     * @return
     */
    @GetMapping("/test3")
    public CompletableFuture<R> test3() {
        //超时处理
        CompletableFuture<R> timeout= CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return R.error("超时!!!");
        });

        //模拟超时，两个任务谁先完成则返回谁
        CompletableFuture<R> result = CompletableFuture.supplyAsync(() -> {
            testService.actionSync();
            return R.ok();
        }, executor2).applyToEitherAsync(timeout,r -> {
            log.warn("超时处理...");
            return r;
        }, executor2);
        return result;
    }



    /**
     * 异常处理
     *
     * @return
     */
    @GetMapping("/test4")
    public CompletionStage<R> test4() {
        return CompletableFuture.supplyAsync(() -> R.ok(testService.actionSync()), executor2).exceptionally(throwable -> {
            log.warn(throwable.getMessage());
            return R.error();
        });
    }
}
