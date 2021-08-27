package com.kk.async.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * GooglePlayPayVO
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
public class GooglePlayPayVO extends OrderCreatedVO {
    /** 凭证信息 */
    private String credential;
}
