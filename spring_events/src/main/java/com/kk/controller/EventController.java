package com.kk.controller;

import com.kk.service.EventService;
import com.kk.service.UserRegisterEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

/**
 * 事件controller
 *
 * @author kkmystery
 * @version 1.0 2021/7/8
 * @since 1.0.0
 */
@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {
    private final UserRegisterEventService userRegisterEventService;
    private final EventService eventService;
    @Resource(name = "adminControllerBizExecutor")
    private Executor executor;

    @GetMapping("/register")
    public CompletionStage<Boolean> register(String username) {
        return CompletableFuture.supplyAsync(() -> userRegisterEventService.userRegister(username), executor);
    }

    @GetMapping("/queue")
    public CompletionStage<Boolean> queue() {
        return CompletableFuture.supplyAsync(() -> userRegisterEventService.queue(), executor);
    }

    @GetMapping("/son")
    public CompletionStage<Void> son() {
        return CompletableFuture.runAsync(() -> eventService.sonEventPublish(), executor);
    }
}
