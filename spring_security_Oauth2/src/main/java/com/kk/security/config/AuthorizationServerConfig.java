package com.kk.security.config;/**
 * @author : K k
 * @date : 21:17 2020/11/9
 */

import com.kk.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 授权服务器配置
 * @author: Kk
 * @create: 2020-11-09 21:17
 **/
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
   /* @Autowired
    @Qualifier("redisTokenStore")
    private TokenStore redisTokenStore;*/
    @Autowired
    @Qualifier("jwtTokenStore")
    private TokenStore jwtTokenStore;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    private JwtTokenEnhancer jwtTokenEnhancer;

    //密码模式所需要的配置
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //加载Jwt内容增强器
        TokenEnhancerChain enhancerChain=new TokenEnhancerChain();
        List<TokenEnhancer> delegates=new ArrayList<>();
        delegates.add(jwtTokenEnhancer);
        delegates.add(jwtAccessTokenConverter);
        enhancerChain.setTokenEnhancers(delegates);

        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userService)
                //配置存储令牌策略：redisTokenStore
                //.tokenStore(redisTokenStore);
                //配置存储令牌策略：jwtTokenStore
                .tokenStore(jwtTokenStore)
                .accessTokenConverter(jwtAccessTokenConverter)
                .tokenEnhancer(enhancerChain);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                //采用内存存储模式，真实开发环境需要数据库查询该第三方应用是否属于开发者平台
                //配置client-id 类比 appId
                .withClient("admin")
                //配置client-secret 类比 appSecret
                .secret(passwordEncoder.encode("112233"))
                //配置访问token有效时间
                .accessTokenValiditySeconds(3600)
                //配置刷新令牌的有效时间
                .refreshTokenValiditySeconds(864000)
                //配置redirect-uri
                .redirectUris("http://localhost:8081/login")
                //自动授权配置
                .autoApprove(true)
                //配置申请的权限范围
                .scopes("all")
                //配置grant_type,oauth模式：授权码模式
                //.authorizedGrantTypes("authorization_code");
                //配置grant_type,oauth模式：密码模式
                .authorizedGrantTypes("password","refresh_token","authorization_code");
    }

    /**
     * 单点登陆需要配置，检验第三方应用是否通过了以下认证方法
     * public void configure(ClientDetailsServiceConfigurer clients)
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //获取密钥需要身份认证,使用单点登陆时必须配置
        security.tokenKeyAccess("isAuthenticated()");
    }
}
