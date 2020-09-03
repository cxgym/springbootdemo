package com.wondersgroup.springbootdruiddemo;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@Configuration
public class SpringbootdruiddemoApplication {

    //Druid后台地址：http://localhost:8080/druid/login.html
    //配置Druid的监控
    // 1、配置一个管理后台的Servlet
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String, String> initParams = new HashMap<>();
        initParams.put("loginUsername", "admin");
        initParams.put("loginPassword", "123456");
        initParams.put("allow", "");    //默认就是允许所有访问
        //initParams.put("deny", "192.168.15.21");   //拒绝访问的主机
        bean.setInitParameters(initParams);
        return bean;
    }

    //2、配置一个web监控的filter
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String, String> initParams = new HashMap<>();
        initParams.put("exclusions", "*.js,*.css,/druid/*");   //排除的拦截
        bean.setInitParameters(initParams);
        bean.setUrlPatterns(Arrays.asList("/*"));   //拦截所有请求
        return bean;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootdruiddemoApplication.class, args);
    }

}
