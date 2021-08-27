package com.kk.listener;

import com.kk.event.QueueEvent;
import com.kk.event.UserRegisterEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 优惠券监听器
 *
 * @author kkmystery
 * @version 1.0 2021/7/8
 * @since 1.0.0
 */
@Component
@Order(1)
@Slf4j
public class CouponListener {
    /**
     * 监听对象为UserRegisterEvent
     *
     * @param event 事件
     */
    @EventListener(classes = {UserRegisterEvent.class, QueueEvent.class})
    public void sendCoupon(Object event) {
        if (event instanceof UserRegisterEvent) {
            UserRegisterEvent register = (UserRegisterEvent) event;
            log.info("CouponUserRegisterListener: {} - 给用户: {} 发送优惠券", Thread.currentThread().getName(), register.getUsername());
        }
        if (event instanceof QueueEvent) {
            QueueEvent queue = (QueueEvent) event;
            log.info("CouponUserRegisterListener: {} - 给用户: {} 发送优惠券", Thread.currentThread().getName(), queue.getNumber());
        }
    }
}
