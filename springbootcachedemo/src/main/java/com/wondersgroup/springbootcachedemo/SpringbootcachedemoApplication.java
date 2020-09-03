package com.wondersgroup.springbootcachedemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan("com.wondersgroup.springbootcachedemo.mapper")   //使用注解版的mybatis
@EnableCaching    //开启基于注解的缓存
@SpringBootApplication
public class SpringbootcachedemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootcachedemoApplication.class, args);
    }

}
