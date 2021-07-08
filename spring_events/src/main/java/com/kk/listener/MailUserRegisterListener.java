package com.kk.listener;

import com.kk.event.UserRegisterEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 事件监听器：监听用户注册事件
 *
 * @author kkmystery
 * @version 1.0 2021/7/8
 * @since 1.0.0
 */
@Component
@Order(2)
@Slf4j
public class MailUserRegisterListener implements ApplicationListener<UserRegisterEvent> {

    /**
     * 监听对象为UserRegisterEvent
     *
     * @param event 事件
     */
    @Override
    public void onApplicationEvent(UserRegisterEvent event) {
        log.info("MailUserRegisterListener: {} - 给用户: {} 发送邮件", Thread.currentThread().getName(), event.getUsername());
    }
}
