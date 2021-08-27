package com.kk.async.service;

import com.kk.async.controller.vo.OrderStatusVO;

/**
 * 订单服务
 *
 * @author kkmystery
 * @version 1.0 2021/8/27
 * @since 1.0.0
 */
public interface IPaymentOrderService {

    OrderStatusVO query(Long userId, Long orderId);
}
