package com.kk.event;

/**
 * 子事件
 *
 * @author kkmystery
 * @version 1.0 2021/7/8
 * @since 1.0.0
 */
public class SonEvent extends QueueEvent{
    private Integer identity;

    public SonEvent(Object source, Integer number) {
        super(source, number);
        this.identity = number + 1;
    }

    public Integer getIdentity() {
        return identity;
    }
}
