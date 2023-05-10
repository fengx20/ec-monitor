package com.weaver.fengx.ecmonitor.common.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Fengx
 * Shiro配置类
 **/
@Configuration
public class ShiroConfig {

    /**
     * 首先需要提供一个Realm的实例。
     * @return
     */
    @Bean
    public CustomRealm customRealm() {
        return new CustomRealm();
    }

    /**
     * 需要配置一个SecurityManager，在SecurityManager中配置Realm。
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        defaultSecurityManager.setRealm(customRealm());
        return defaultSecurityManager;
    }

    /**
     * 配置一个ShiroFilterFactoryBean，在ShiroFilterFactoryBean中指定路径拦截规则等
     * Shiro自带的一个Filter工厂实例，所有的认证和授权判断都是由这个bean生成的Filter对象来完成的，
     * Shiro框架的运行机制，开发者只需要定义规则，进行配置，具体的执行者全部由Shiro自己创建的Filter来完成
     * @param securityManager
     * @return
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 表示指定SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 表示指定登录页面
        shiroFilterFactoryBean.setLoginUrl("/");
        // 没有权限页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");
        // 表示指定登录成功页面
        shiroFilterFactoryBean.setSuccessUrl("/upgrade/systemlist");
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // anon:所有url都可以匿名访问
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/notRole", "anon");

        // authc:所有url都必须认证通过才可以访问
        filterChainDefinitionMap.put("/upgrade/**", "anon");
        filterChainDefinitionMap.put("/rollback/**", "anon");
        // 主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        filterChainDefinitionMap.put("/**", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

//    @Bean(name = "shiroFilter")
//    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        shiroFilterFactoryBean.setSecurityManager(securityManager);
//        shiroFilterFactoryBean.setLoginUrl("/");
//        shiroFilterFactoryBean.setUnauthorizedUrl("/");
//        shiroFilterFactoryBean.setSuccessUrl("/server");
//        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
//        filterChainDefinitionMap.put("/login", "anon");
//        filterChainDefinitionMap.put("/", "anon");
//        filterChainDefinitionMap.put("/monitor", "anon");
//        filterChainDefinitionMap.put("/index.css", "anon");
//        filterChainDefinitionMap.put("/bg.jpg", "anon");
//        filterChainDefinitionMap.put("/favicon.ico", "anon");
//        // 主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截 剩余的都需要认证
//        filterChainDefinitionMap.put("/**", "authc");
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//        return shiroFilterFactoryBean;
//    }

}