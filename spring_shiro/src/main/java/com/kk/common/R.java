package com.kk.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回信息
 *
 * @author kkmystery
 * @version 1.0 2021/7/8
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class R<T> {
    private int code;
    private String msg;
    private T data;

    public static <T> R<T> failed(int code, String msg, T data) {
        return new R<>(code, "认证失败", null);
    }

    public static <T> R<T> success(String msg, T data) {
        return new R<>(200, msg, data);
    }
}
