package com.kk.event;

import org.springframework.context.ApplicationEvent;

/**
 * 排队事件
 *
 * @author kkmystery
 * @version 1.0 2021/7/8
 * @since 1.0.0
 */
public class QueueEvent extends ApplicationEvent {
    private Integer number;

    public QueueEvent(Object source, Integer number) {
        super(source);
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }
}
