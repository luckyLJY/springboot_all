package com.kk.async.service;

import com.kk.async.controller.vo.OrderCreatedVO;
import com.kk.async.controller.vo.OrderPaymentVerifyVO;
import com.kk.async.controller.vo.OrderStatusVO;

import java.util.concurrent.CompletableFuture;

/**
 * 订单biz服务
 *
 * @author kkmystery
 * @version 1.0 2021/8/27
 * @since 1.0.0
 */
public interface IPaymentOrderBizService {

    /**
     * 创建订单
     *
     * @param userId 用户id
     * @return OrderCreatedVO
     */
    OrderCreatedVO placeOrder(Long userId);

    /**
     * 校验订单支付信息
     *
     * @param userId 用户id
     * @return OrderPaymentVerifyVO
     */
    CompletableFuture<OrderPaymentVerifyVO> verifyOrderPayment(Long userId, Long orderId);

    /**
     * 查询订单状态
     *
     * @param orderId 订单id
     * @param userId  userId
     * @return OrderStatusVO
     */
    OrderStatusVO queryOrder(Long orderId, Long userId);
}
