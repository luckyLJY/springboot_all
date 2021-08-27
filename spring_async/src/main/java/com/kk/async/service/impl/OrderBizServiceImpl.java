package com.kk.async.service.impl;

import com.kk.async.pojo.PayChannelEnum;
import com.kk.async.service.IOrderBizService;
import com.kk.async.service.IPaymentOrderBizService;
import com.kk.async.util.ServiceMapConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * OrderBizServiceImpl
 *
 * @author kkmystery
 * @version 1.0 2021/8/27
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class OrderBizServiceImpl implements IOrderBizService {
    private final Map<String, IPaymentOrderBizService> bizServiceMap;

    @Override
    public IPaymentOrderBizService getService() {
        return getService(PayChannelEnum.WECHAT_PAY);
    }

    @Override
    public IPaymentOrderBizService getService(PayChannelEnum payChannel) {
        final String beanName = ServiceMapConstant.getServiceBeanName(payChannel.name(), IPaymentOrderBizService.class);
        final IPaymentOrderBizService bizService = bizServiceMap.get(beanName);
        if (null == bizService) {
            throw new RuntimeException("FUNCTION_PAUSE_TEMPORARY");
        }
        return bizService;
    }
}
