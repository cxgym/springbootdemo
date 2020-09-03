package com.wondersgroup.springbootmybatisdemo;

import com.wondersgroup.springbootmybatisdemo.dict.service.ProductCategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootmybatisdemoApplicationTests {

    @Autowired
    ProductCategoryService productCategoryService;

    @Test
    public void contextLoads() {
        String categoryName = productCategoryService.getCategoryName("1");
        System.out.println(categoryName);
    }

}
