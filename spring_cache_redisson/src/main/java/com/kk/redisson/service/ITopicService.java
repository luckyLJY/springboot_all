package com.kk.redisson.service;

/**
 * redisson消息队列
 *
 * @author kkmystery
 * @version 1.0 2021/6/30
 * @since 1.0.0
 */
public interface ITopicService {

    /**
     * 发送消息
     *
     * @param msg 消息
     */
    void sendMsg(String msg);
}
