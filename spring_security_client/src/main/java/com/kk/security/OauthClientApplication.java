package com.kk.security;/**
 * @author : K k
 * @date : 21:35 2020/11/11
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

/**
 * @description: 客户端
 * @author: Kk
 * @create: 2020-11-11 21:35
 **/
@SpringBootApplication
//开启单点登陆
@EnableOAuth2Sso
public class OauthClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(OauthClientApplication.class,args);
    }
}
