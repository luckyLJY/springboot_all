package com.kk.kafka.entity;

import lombok.Data;

/**
 * @description:
 * @author: Kk
 * @create: 2020-11-29 17:37
 **/
@Data
public class User {
    /**
     * id
     */
    private Integer id;

    /**
     * 用户名字
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
