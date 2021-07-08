package com.kk.event;

import org.springframework.context.ApplicationEvent;

/**
 * 用户注册事件
 *
 * @author kkmystery
 * @version 1.0 2021/7/8
 * @since 1.0.0
 */
public class UserRegisterEvent extends ApplicationEvent {
    private String username;

    public UserRegisterEvent(Object source, String username) {
        super(source);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
