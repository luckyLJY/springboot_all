package com.kk.listener;

import com.kk.event.SonEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 子监听器
 *
 * @author kkmystery
 * @version 1.0 2021/7/8
 * @since 1.0.0
 */
@Component
@Order(4)
@Slf4j
public class SonListener {

    /**
     * 监听对象为SonEvent
     * 子事件是否会触发父事件的监听器
     *
     * @param event 事件
     */
    @EventListener
    @Async(value = "eventExecutor")
    public void sonListen(SonEvent event) {
        log.info("SonListener: {} - 给用户: {} 发生异步处理", Thread.currentThread().getName(), event.getIdentity());
    }
}
