package com.kk.security.config;/**
 * @author : K k
 * @date : 21:24 2020/11/9
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @description: 资源服务器配置
 * @author: Kk
 * @create: 2020-11-09 21:24
 **/
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //所有请求需要认证
                /*.antMatchers("/user/**")
                .hasAnyAuthority("admin")*/
                .anyRequest()
                .authenticated()
                .and()
                //匹配路由，若请求路径匹配以下路由则放行，否则不放行。表示当前资源服务器只开放/user/**接口
                .requestMatchers()
                .antMatchers("/user/**");

    }
}
