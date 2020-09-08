package com.wondersgroup.springbootshirodemo.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.google.common.collect.Maps;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    @Bean("redisTemplateObject")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new RedisObjectSerializer());
        return template;
    }

    @Bean
    public RedisCacheManager cacheManager(RedisTemplate<String, Object> redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager();
        cacheManager.setRedisTemplate(redisTemplate);
        return cacheManager;
    }

    @Bean
    public SessionManager sessionManager(@Value("${shiro.sessionTimeout}") Long sessionTimeout,
                                         @Value("${shiro.sessionIdName}") String sessionIdName) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 设置session超时时间，单位为毫秒
        sessionManager.setGlobalSessionTimeout(sessionTimeout);
        sessionManager.setSessionIdCookie(new SimpleCookie(sessionIdName));
        return sessionManager;
    }

    @Bean
    public SecurityManager securityManager(CustomAuthorizingRealm realm, RedisCacheManager cacheManager, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setCacheManager(cacheManager);
        // 配置SecurityManager，并注入shiroRealm
        securityManager.setRealm(realm);
        // 配置sessionManager
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager,
                                                            @Value("${cas_login_url}") String casLoginUrl,
                                                            @Value("${shiro.loginUrl}") String loginUrl,
                                                            @Value("${shiro.successUrl}") String successUrl,
                                                            @Value("${shiro.unauthorizedUrl}") String unauthorizedUrl,
                                                            @Value("${shiro.anonUrl}") String anonUrl,
                                                            @Value("${shiro.logoutUrl}") String logoutUrl) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置 securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 登录的 url
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
        // 登录成功后跳转的 url
        shiroFilterFactoryBean.setSuccessUrl(successUrl);
        // 未授权 url
        shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);

        // 添加casFilter到shiroFilter中
        //Map<String, Filter> filters = Maps.newHashMap();
        //filters.put("authc", new MyUserFilter(casLoginUrl));
        //filters.put("logout", new MyLogoutFilter(casLoginUrl));
        //shiroFilterFactoryBean.setFilters(filters);

        // 这里配置授权链，跟mvc的xml配置一样
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 设置免认证 url
        String[] anonUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(anonUrl, ",");
        for (String url : anonUrls) {
            filterChainDefinitionMap.put(url, "anon");
        }
        // 配置退出过滤器，其中具体的退出代码 Shiro已经替我们实现了
        filterChainDefinitionMap.put(logoutUrl, "logout");
        // 除上以外所有 url都必须认证通过才可以访问，未通过认证自动访问 LoginUrl
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }
}
