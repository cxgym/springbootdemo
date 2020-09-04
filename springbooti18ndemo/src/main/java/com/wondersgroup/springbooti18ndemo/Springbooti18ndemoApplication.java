package com.wondersgroup.springbooti18ndemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * LocaleResolver 区域信息解析器
 */
@SpringBootApplication
public class Springbooti18ndemoApplication implements LocaleResolver {

    public static void main(String[] args) {
        SpringApplication.run(Springbooti18ndemoApplication.class, args);
    }

    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        String l = httpServletRequest.getParameter("l");
        Locale locale=Locale.getDefault();   //默认使用浏览器默认的区域信息解析器
        if(!StringUtils.isEmpty(l))    //如果请求参数带有语言信息，则使用自己配置的区域信息解析器
        {
            String[] split=l.split("_");
            locale=new Locale(split[0],split[1]);    //param1语言  param2国家
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }

    @Bean
    public LocaleResolver localeResolver()
    {
        return new Springbooti18ndemoApplication();
    }
}
