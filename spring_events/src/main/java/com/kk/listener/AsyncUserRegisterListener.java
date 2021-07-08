package com.kk.listener;

import com.kk.event.UserRegisterEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步监听器
 *
 * @author kkmystery
 * @version 1.0 2021/7/8
 * @since 1.0.0
 */
@Component
@Order(3)
@Slf4j
public class AsyncUserRegisterListener{

    /**
     * 监听对象为UserRegisterEvent
     *
     * @param event 事件
     */
    @EventListener
    @Async
    public void asyncListen(UserRegisterEvent event) {
        log.info("AsyncUserRegisterListener: {} - 给用户: {} 发生异步处理", Thread.currentThread().getName(), event.getUsername());
    }
}
