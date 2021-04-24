package com.share.springboot.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**配置类
 * @author 汪宇
 * @date 2020/4/27 11:09
 */
@Configuration
public class ShiroConfig {

    /*
     * 凭证匹配器 （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * 所以我们需要修改下doGetAuthenticationInfo中的代码; )
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");// 散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(1024);// 散列的次数，比如散列两次，相当于md5(md5(""));
        return hashedCredentialsMatcher;
    }
    /**
     * 创建ShiroFilterFantoryBean
     */
    @Bean
    public ShiroFilterFactoryBean ShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        /**
         * Shiro内置过滤器，实现权限相关的拦截器
         * anno:无需认证（登陆）可以访问
         * authc:必须认证才能访问
         * user：如果使用rememberMe的功能可以直接访问
         * perms:该资源必须得到资源权限可以访问
         * role:该资源必须得到角色权限才能访问
         */
        Map<String,String> filterMap = new LinkedHashMap<String,String>();

        //filterMap.put("/testThymeleaf","anon");
        filterMap.put("/user/toLogin","anon");
        filterMap.put("/share/login","anon");
        filterMap.put("/login","anon");
        filterMap.put("/user/toRegister","anon");
        filterMap.put("/user/code1","anon");
        filterMap.put("/user/code2","anon");
        shiroFilterFactoryBean.setUnauthorizedUrl("/error/permission");
        filterMap.put("/sharestyle/**","anon");
        filterMap.put("/**","authc");



        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }
    /**
     *创建DefaultWebSecurityManager
     */
    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDeafDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }
    /**
     *创建Realm
     */
    @Bean(name = "userRealm")
    public UserRealm getRealm(){
        return new UserRealm();
    }
}
