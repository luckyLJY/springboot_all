package com.kk.security.controller;/**
 * @author : K k
 * @date : 21:37 2020/11/11
 */

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 用户
 * @author: Kk
 * @create: 2020-11-11 21:37
 **/
@RestController
@RequestMapping("user")
public class UserController {

    /**
     * 获取当前用户信息
     * @param authentication
     * @return
     */
    @RequestMapping("getCurrentUser")
    public Object getCurrentUser(Authentication authentication){
        return authentication;
    }
}
