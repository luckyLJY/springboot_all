package com.kk.service;

import com.kk.event.UserRegisterEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
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
public class UserRegisterEventService /*implements ApplicationContextAware, ApplicationEventPublisherAware*/ {
    // 直接注入的方式或实现接口注入依赖
    private final ApplicationContext applicationContext;
    private final ApplicationEventPublisher applicationEventPublisher;

    public boolean userRegister(String username){
        log.info("用户 {} 注册成功", username);
        //可以用applicationContext发送事件
        //applicationContext.publishEvent(new UserRegisterEvent(this, username));
        //也可以用applicationEventPublisher，二者等价
        applicationEventPublisher.publishEvent(new UserRegisterEvent(this, username));
        return true;
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
