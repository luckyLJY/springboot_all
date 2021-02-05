package com.kk.cache.controller.request;

import lombok.Data;

import java.util.Set;

/**
 * @author luokexiong
 * @version 1.0 2021/2/4
 * @since 1.9.0
 */
@Data
public class SetRequest {
    Set<Long> ids;
}
