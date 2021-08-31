package com.kk.async.controller;

import com.kk.async.service.IThreadPoolService;
import com.kk.async.threadpool.vo.ThreadPoolArgsVO;
import com.kk.async.threadpool.vo.ThreadPoolDetailInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

/**
 * 动态线程池controller
 *
 * @author kkmystery
 * @version 1.0 2021/8/31
 * @since 1.0.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/thread")
public class DynamicThreadController {

    private final IThreadPoolService threadPoolService;
    @Resource(name = "dynamicExecutor")
    private Executor dynamicExecutor;

    @GetMapping("/manager")
    public CompletionStage<List<ThreadPoolArgsVO>> getAllThreadPoolArgs() {
        return CompletableFuture.supplyAsync(threadPoolService::getAllThreadPoolArgs, dynamicExecutor);
    }

    @GetMapping("/detail/{name}")
    public CompletionStage<ThreadPoolDetailInfoVO> getDetails(@PathVariable String name) {
        return  CompletableFuture.supplyAsync((() -> threadPoolService.getThreadPoolDetailByName(name)), dynamicExecutor);
    }

    @PostMapping("/manager")
    public CompletableFuture<Boolean> updateThreadPool(@RequestBody ThreadPoolArgsVO threadPoolArgs) {
        return CompletableFuture.supplyAsync(() -> threadPoolService.updateThreadPool(threadPoolArgs), dynamicExecutor);
    }
}
