package com.wondersgroup.springbootconfigdemo;

import com.wondersgroup.springbootconfigdemo.model.InitInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)   //不用junit而用SpringRunner驱动器，这样可以在测试期间很方便的类似编码一样进行自动注入容器等功能
@SpringBootTest
public class SpringbootconfigdemoApplicationTests {

    //如果我们要将配置文件和实体类映射，供全局应用使用，可以使用这种方法读取配置文件
    @Autowired
    InitInfo initinfo;

    //如果只是在程序中获取某个配置文件的值，可以用这种方式读取配置文件简单
    //@Value("${initinfo.appName}")
    //private String appName;

    @Value("${name}")
    private String name;

    @Test
    public void contextLoads() {
        System.out.println(initinfo);
        System.out.println(name);
        //System.out.println(appName);
    }

}