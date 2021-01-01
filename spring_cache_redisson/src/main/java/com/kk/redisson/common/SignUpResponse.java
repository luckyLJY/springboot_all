package com.kk.redisson.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 统一返回格式
 * @author luokexiong
 * @version 1.0 2020/12/25
 * @since 1.0.0
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SignUpResponse {
    /** 是否注册成功 */
    Boolean success;
    /** 成功时返回用户id，UUID即可 */
    String userId;
    /** 错误信息 */
    String message;
}
