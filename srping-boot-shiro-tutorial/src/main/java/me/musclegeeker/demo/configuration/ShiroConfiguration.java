package me.musclegeeker.demo.configuration;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro 配置
 * Apache Shiro 核心通过 Filter 来实现，就好像SpringMvc 通过DispachServlet 来主控制一样。
 * 既然是使用 Filter 一般也就能猜到，是通过URL规则来进行过滤和权限校验，所以我们需要定义一系列关于URL的规则和访问权限。
 *
 * @version v.0.1
 */
@Configuration
public class ShiroConfiguration {

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        System.out.println("ShiroConfiguration.shiroFilter()");
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        /**设置安全管理器**/
        shiroFilter.setSecurityManager(securityManager);
        /**过滤链定义**/
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 退出策略
        filterChainDefinitionMap.put("/logout", "logout");
        // 过滤链顺序执行，从上往下，都满足才可以
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);
        // 登录
        shiroFilter.setLoginUrl("/login");
        // 登录成功
        shiroFilter.setSuccessUrl("/index");
        // 未授权请求
        shiroFilter.setUnauthorizedUrl("/403");
        return shiroFilter;
    }

    @Bean(name = "securityManager")
    public SecurityManager getSecurityManager() {
        return new DefaultWebSecurityManager();
    }
}
