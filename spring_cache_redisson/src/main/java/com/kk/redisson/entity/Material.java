package com.kk.redisson.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author luokexiong
 * @version 1.0 2020/12/28
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Material {
    /** 素材id */
    private String id;
    /** 标题 */
    private String title;
    /** 类型 */
    private String type;
    /** 用户id */
    private String userId;
}
