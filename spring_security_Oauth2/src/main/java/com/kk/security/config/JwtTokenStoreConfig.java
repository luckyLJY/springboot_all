package com.kk.security.config;/**
 * @author : K k
 * @date : 11:40 2020/11/10
 */

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.stereotype.Component;

/**
 * @description: JwtToken配置类
 * @author: Kk
 * @create: 2020-11-10 11:40
 **/
@Configuration
//@ConfigurationProperties(prefix = "jwt")
public class JwtTokenStoreConfig {
    private String key;

    @Bean(name = "jwtTokenStore")
    @Primary
    public TokenStore jwtTokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter accessTokenConverter=new JwtAccessTokenConverter();
        //配置jwt使用的密钥
        accessTokenConverter.setSigningKey("test_key");
        return accessTokenConverter;
    }

    //Jwt内容扩展器
    @Bean
    public JwtTokenEnhancer jwtTokenEnhancer(){
        return new JwtTokenEnhancer();
    }
}
