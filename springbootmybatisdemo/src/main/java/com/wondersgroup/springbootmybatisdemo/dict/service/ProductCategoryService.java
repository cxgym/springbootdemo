
package com.wondersgroup.springbootmybatisdemo.dict.service;

import com.wondersgroup.springbootmybatisdemo.dict.dao.ProductCategoryDao;
import com.wondersgroup.springbootmybatisdemo.dict.entity.ProductCategory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    public String getCategoryName(String categoryId) {
        ProductCategory pc = productCategoryDao.get(categoryId);
        if (pc != null) {
            String categoryname = pc.getCategoryName();
            return categoryname;
        }
        return "";
    }
}