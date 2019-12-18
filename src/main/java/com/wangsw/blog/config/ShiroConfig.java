package com.wangsw.blog.config;

import com.wangsw.blog.common.MyRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {

	private static final Logger log = LoggerFactory.getLogger(ShiroConfig.class);

    /**
     * 注入Realm
     * @return MyRealm
     */
    @Bean(name = "myRealm")
    public MyRealm myAuthRealm() {
        MyRealm myRealm = new MyRealm();
        log.info("myRealm注册完成");
        return myRealm;
    }


    /**
     * 注入SecurityManager
     * @param myRealm
     * @return SecurityManager
     */
    @Bean(name = "securityManager")
    DefaultWebSecurityManager securityManager(@Qualifier("myRealm")MyRealm myRealm) {
    	DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(myRealm);
        log.info("securityManager注册完成");
        return manager;
    }

    /**
     * 注入Filter
     * @param securityManager
     * @return ShiroFilterFactoryBean
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
    	System.out.println("ShiroConfig.shirFilter()");
    	ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        // 配置登录的url和登录成功的url
        //filterFactoryBean.setLoginUrl("/auth/login");
        //filterFactoryBean.setSuccessUrl("/admin/toAdminMain");
        // 配置未授权跳转页面
        //filterFactoryBean.setUnauthorizedUrl("/error/403");
  /*      // 配置访问权限
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //filterChainDefinitionMap.put("*//**", "anon");
        filterChainDefinitionMap.put("/css*//**", "anon"); // 表示可以匿名访问
        filterChainDefinitionMap.put("/fonts*//**", "anon");
        filterChainDefinitionMap.put("/images*//**", "anon");
        filterChainDefinitionMap.put("/js*//**", "anon");
        filterChainDefinitionMap.put("/plugin*//**", "anon");
        filterChainDefinitionMap.put("/auth*//**", "anon");
        filterChainDefinitionMap.put("/error*//**", "anon");
        filterChainDefinitionMap.put("/demo*//**", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html*//**", "anon");
        filterChainDefinitionMap.put("/layui*//**", "anon");
        filterChainDefinitionMap.put("/index", "anon");
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/blog*//**", "anon");
        filterChainDefinitionMap.put("/picture*//**", "anon");
        filterChainDefinitionMap.put("/sys*//**", "authc");// 表示admin权限才可以访问，多个加引号用逗号相隔
        filterChainDefinitionMap.put("/admin*//**", "authc");// 表示需要认证才可以访问
        filterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);*/
        log.info("shiroFilter注册完成");
        return filterFactoryBean;
    }
}
