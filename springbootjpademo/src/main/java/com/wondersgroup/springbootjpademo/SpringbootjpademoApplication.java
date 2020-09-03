package com.wondersgroup.springbootjpademo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动应用程序会自动创建User实体类对应的tbl_user表
 */
@SpringBootApplication
public class SpringbootjpademoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootjpademoApplication.class, args);
    }

}
