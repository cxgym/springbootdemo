package com.wondersgroup.springbootcachedemo.controller;

import com.wondersgroup.springbootcachedemo.model.ProductCategory;
import com.wondersgroup.springbootcachedemo.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductCategoryController {
    @Autowired
    ProductCategoryService productCategoryService;

    @GetMapping("/pc/{id}")
    public ProductCategory getPcById(@PathVariable("id") Integer id)
    {
        ProductCategory pc = productCategoryService.getPcById(id);
        return pc;
    }

    //http://localhost:8080/updatepc?categoryId=1&categoryName=水果1
    @GetMapping("/updatepc")
    public ProductCategory updatePc(ProductCategory pc)
    {
        ProductCategory pcm = productCategoryService.updatePc(pc);
        return pcm;
    }

    //http://localhost:8080/delpc?id=1
    @GetMapping("/delpc")
    public String deletePc(Integer id)
    {
        productCategoryService.deletePc(id);
        return "success";
    }
}
