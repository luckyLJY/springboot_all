package com.kk.shrio;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 进入类型
 *
 * @author kkmystery
 * @version 1.0 2021/7/8
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum AccessType {

    /** 匿名访问 */
    ANON("anon"),
    /** 需要认证 */
    AUTHC("authc"),
    /** 动态：可以匿名，也可以认证 */
    DYNAMIC_ACCESS("dynamic-access");

    private String value;
}
