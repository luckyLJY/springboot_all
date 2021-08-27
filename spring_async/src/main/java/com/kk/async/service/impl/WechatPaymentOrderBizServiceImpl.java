package com.kk.async.service.impl;

import com.kk.async.controller.vo.OrderCreatedVO;
import com.kk.async.controller.vo.OrderPaymentVerifyVO;
import com.kk.async.controller.vo.WechatPayVO;
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
@Service("wechatPayIPaymentOrderBizService")
@RequiredArgsConstructor
public class WechatPaymentOrderBizServiceImpl extends AbstractPaymentOrderBizService {

    @Override
    public OrderCreatedVO preSubmitOrder(Long userId) {
        log.warn("The wechat pay payment do preSubmitOrder");
        return WechatPayVO.builder()
                .id(System.currentTimeMillis())
                .prepayId("prepayId" + UUID.randomUUID().toString())
                .created(System.currentTimeMillis())
                .sign("sign: " + UUID.randomUUID().toString())
                .transferUrl("www.baidu.com")
                .build();
    }

    @Override
    public CompletableFuture<OrderPaymentVerifyVO> verifyOrderPayment(Long userId, Long orderId) {
        return CompletableFuture.supplyAsync(() -> {
            log.warn("The wechat pay payment order biz service method 'verifyOrderPayment' should not be called, but it was called actually");
            throw new RuntimeException("wechatPayIPaymentOrderBizService verifyOrderPayment 出错");
        });
    }
}
