package com.wondersgroup.springbootdruiddemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootdruiddemoApplicationTests {
    @Autowired
    DataSource dataSource;

    @Test
    public void contextLoads() throws SQLException {
        //获取到的数据源为com.alibaba.druid.pool.DruidDataSource
        System.out.println(dataSource.getClass());
        //获取数据源的连接配置信息
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }

}
