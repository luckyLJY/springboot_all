package com.kk.async.service;

import com.kk.async.pojo.PayChannelEnum;

/**
 * 订单服务接口
 *
 * @author MingJie-Ou
 * @version 1.0, 2021/8/6
 * @since 2.4.0
 */
public interface IOrderBizService {

    /**
     * 获取 payment order biz 服务
     * <p>用于查询订单接口，三个渠道的实现都继承于抽象类，这里暂时写死返回微信渠道</p>
     *
     * @return payment order biz
     */
    IPaymentOrderBizService getService();

    /**
     * 根据渠道获取 payment order biz 服务
     *
     * @param payChannel 渠道
     * @return payment order biz
     */
    IPaymentOrderBizService getService(PayChannelEnum payChannel);
}
