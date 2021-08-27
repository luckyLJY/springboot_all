package com.kk.service;

/**
 * 事件服务
 *
 * @author kkmystery
 * @version 1.0 2021/7/8
 * @since 1.0.0
 */

import com.kk.event.QueueEvent;
import com.kk.event.SonEvent;
import com.kk.event.UserRegisterEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService /*implements ApplicationContextAware, ApplicationEventPublisherAware*/ {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ApplicationContext applicationContext;

    public void registerEventPublish(UserRegisterEvent event) {
        applicationEventPublisher.publishEvent(event);
    }

    public void queueEventPublish() {
        for (int i = 1; i <= 2; i++) {
            applicationEventPublisher.publishEvent(new QueueEvent(this, i));
        }
    }

    public void sonEventPublish() {
        applicationEventPublisher.publishEvent(new SonEvent(this, 1));
    }

    /* @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }*/
}
