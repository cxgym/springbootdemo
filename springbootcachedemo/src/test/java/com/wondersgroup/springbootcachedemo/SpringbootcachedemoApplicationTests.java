package com.wondersgroup.springbootcachedemo;

import com.wondersgroup.springbootcachedemo.mapper.ProductCategoryMapper;
import com.wondersgroup.springbootcachedemo.model.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootcachedemoApplicationTests {

    @Autowired
    ProductCategoryMapper productCategoryMapper;

    @Test
    public void contextLoads() {
        ProductCategory productCategory=productCategoryMapper.getPcById(1);
        System.out.println(productCategory);
    }

}