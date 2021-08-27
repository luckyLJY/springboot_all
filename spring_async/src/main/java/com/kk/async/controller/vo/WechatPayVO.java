package com.kk.async.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * WechatPayVO
 *
 * @author kkmystery
 * @version 1.0 2021/8/27
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class WechatPayVO extends OrderCreatedVO {
    /** 预支付交易会话ID */
    private String prepayId;
    /** 随机字符串 */
    private String sign;
}
