package com.kk.async.service;

import com.kk.async.controller.vo.OrderCreatedVO;
import com.kk.async.controller.vo.OrderStatusVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 抽象服务
 * 将统一部分提取放入抽象类，调用抽象方法，抽象方法可以具体实现类实现
 *
 * @author kkmystery
 * @version 1.0 2021/8/27
 * @since 1.0.0
 */
@Slf4j
public abstract class AbstractPaymentOrderBizService implements IPaymentOrderBizService {
    protected AbstractPaymentOrderBizService() {
    }

    @Autowired
    protected IPaymentOrderService paymentOrderService;

    public abstract OrderCreatedVO preSubmitOrder(Long userId);

    @Override
    public OrderCreatedVO placeOrder(Long userId) {
        log.warn("abstract paymentOrderBizService do place order: {}", userId);
        return this.preSubmitOrder(userId);
    }

    @Override
    public OrderStatusVO queryOrder(Long orderId, Long userId) {
        log.warn("abstract paymentOrderBizService do query order: {}, {}", userId, orderId);
        return paymentOrderService.query(userId, orderId);
    }
}
