package com.kk.service;

import com.kk.event.UserRegisterEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户注册事件服务
 *
 * @author kkmystery
 * @version 1.0 2021/7/8
 * @since 1.0.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserRegisterEventService {
    private final EventService eventService;

    public boolean userRegister(String username) {
        log.info("{} 线程，用户 {} 注册成功", Thread.currentThread().getName(), username);
        eventService.registerEventPublish(new UserRegisterEvent(this, username));
        return true;
    }

    public Boolean queue() {
        eventService.queueEventPublish();
        return true;
    }
}
