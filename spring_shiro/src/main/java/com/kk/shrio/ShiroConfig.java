package com.kk.shrio;

import lombok.RequiredArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.ServletContext;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置类
 *
 * @author kkmystery
 * @version 1.0 2021/7/8
 * @since 1.0.0
 */
@Configuration
@RequiredArgsConstructor
public class ShiroConfig {
    private final MyAuthorizingRealm myAuthorizingRealm;

    /**
     * 设置代理
     *
     * @return DefaultAdvisorAutoProxyCreator
     */
    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setUsePrefix(true);
        creator.setProxyTargetClass(true);
        return creator;
    }

    /**
     * securityManager 关闭内部session配置
     *
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 设置realm
        manager.setRealm(myAuthorizingRealm);

        // 禁止session持久化存储，一定要禁止session持久化。不然清除认证缓存、授权缓存后，shiro依旧能从session中读取到认证信息
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);

        return manager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
        // set securityManager to shiro
        SecurityUtils.setSecurityManager(securityManager);

        // 注入自定义filter
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put(AccessType.AUTHC.getValue(), new JwtFilter());
        bean.setFilters(filterMap);

        // 过滤规则
        Map<String, String> filterChainMap = new LinkedHashMap<>();
        // 可以匿名的
        String anon = AccessType.ANON.getValue();
        // swagger
        filterChainMap.put("/swagger-ui.html", anon);
        filterChainMap.put("/swagger-resources/**", anon);
        filterChainMap.put("/swagger-resources", anon);
        filterChainMap.put("/v2/api-docs", anon);
        filterChainMap.put("/webjars/springfox-swagger-ui/**", anon);
        // knife4j ui for swagger
        filterChainMap.put("/doc.html", anon);
        filterChainMap.put("/webjars/**", anon);
        // 根目录
        filterChainMap.put("/", anon);
        // 员工登录
        filterChainMap.put("/auth/user/login", anon);

        // 需要登录的
        filterChainMap.put("/**", "authc");
        bean.setFilterChainDefinitionMap(filterChainMap);
        return bean;
    }

    /**
     * shiro增加异步支持
     */
    @Bean
    public FilterRegistrationBean<Filter> shiroFilterRegistration(ServletContext ctx) {
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        //该值缺省为false，表示生命周期由SpringApplicationContext管理，设置为true则表示由ServletContainer管理
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        proxy.setServletContext(ctx);

        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(proxy);
        // 取消自动注册
        registration.setEnabled(false);
        registration.setOrder(Integer.MAX_VALUE - 1);
        registration.addUrlPatterns("/*");
        //支持异步
        registration.setAsyncSupported(true);
        registration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ASYNC);
        return registration;
    }
}
