package com.wondersgroup.springbootmybatisdemo.dict.dao;

import com.wondersgroup.springbootmybatisdemo.dict.entity.ProductCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductCategoryDao {

    public ProductCategory get(@Param("categoryId") String categoryId);

    public int delete(@Param("categoryId") String categoryId);

    List<ProductCategory> findPc(@Param("categoryId") String categoryId);

}
