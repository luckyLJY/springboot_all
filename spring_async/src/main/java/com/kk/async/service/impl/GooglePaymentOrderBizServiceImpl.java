package com.kk.async.service.impl;

import com.kk.async.controller.vo.GooglePlayPayVO;
import com.kk.async.controller.vo.OrderCreatedVO;
import com.kk.async.controller.vo.OrderPaymentVerifyVO;
import com.kk.async.service.AbstractPaymentOrderBizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * 具体实现类
 *
 * @author kkmystery
 * @version 1.0 2021/8/27
 * @since 1.0.0
 */
@Slf4j
@Service("googlePlayIPaymentOrderBizService")
@RequiredArgsConstructor
public class GooglePaymentOrderBizServiceImpl extends AbstractPaymentOrderBizService {

    @Override
    public OrderCreatedVO preSubmitOrder(Long userId) {
        log.warn("The Google pay payment do preSubmitOrder");
        return GooglePlayPayVO.builder()
                .id(System.currentTimeMillis())
                .created(System.currentTimeMillis())
                .credential("credential: " + UUID.randomUUID().toString())
                .transferUrl("www.google.com")
                .build();
    }

    @Override
    public CompletableFuture<OrderPaymentVerifyVO> verifyOrderPayment(Long userId, Long orderId) {
        return CompletableFuture.supplyAsync(() -> {
            log.warn("The Google pay payment order biz service method 'verifyOrderPayment' should not be called, but it was called actually");
            throw new RuntimeException("googlePlayIPaymentOrderBizService verifyOrderPayment 出错");
        });
    }
}
