package com.kk.async.service.impl;

import com.kk.async.controller.vo.OrderStatusVO;
import com.kk.async.service.IPaymentOrderService;
import org.springframework.stereotype.Service;

/**
 * PaymentOrderServiceImpl
 *
 * @author kkmystery
 * @version 1.0 2021/8/27
 * @since 1.0.0
 */
@Service
public class PaymentOrderServiceImpl implements IPaymentOrderService {
    @Override
    public OrderStatusVO query(Long userId, Long orderId) {
        return new OrderStatusVO(System.currentTimeMillis(), "PAID");
    }
}
