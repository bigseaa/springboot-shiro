package com.bigsea;

import com.bigsea.realm.CustomRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置
 * @author sea
 * @date 2019-06-23
 */
@Configuration
public class ShiroConfiguration {
    /**
     * 1.创建realm
     * @return CustomRealm
     */
    @Bean
    public CustomRealm getRealm() {
        return new CustomRealm();
    }

    /**
     * 2.创建安全管理器
     * @param realm CustomRealm
     * @return SecurityManager
     */
    @Bean
    public SecurityManager getSecurityManager(CustomRealm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(realm);
        return securityManager;
    }

    /**
     * 3.配置shiro过滤器工厂
     * 在web程序中，shiro进行权限控制全部是通过一组过滤器集合进行控制
     * @param securityManager SecurityManager
     * @return ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        // 1.创建过滤器工厂
        ShiroFilterFactoryBean filterFactory = new ShiroFilterFactoryBean();
        // 2.设置安全管理器
        filterFactory.setSecurityManager(securityManager);
        // 3.通用配置（跳转登录页面，未授权跳转页面）
        filterFactory.setLoginUrl("/user/autherror?code=1");
        filterFactory.setUnauthorizedUrl("/user/autherror?code=2");
        // 4.设置过滤器集合
        /**
         * 设置所有的过滤器：有顺序map
         *      key = 拦截的url地址
         *      value = 过滤器类型
         */
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/user/home", "anon");    // 当前请求的地址可以匿名访问
        filterMap.put("/user/login", "anon");
        filterMap.put("/user/**", "authc");     // 当前请求的地址必须认证之后可以访问

        filterFactory.setFilterChainDefinitionMap(filterMap);
        return filterFactory;
    }

    /**
     * 配置shiro注解的支持
     * @param securityManager SecurityManager
     * @return AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
