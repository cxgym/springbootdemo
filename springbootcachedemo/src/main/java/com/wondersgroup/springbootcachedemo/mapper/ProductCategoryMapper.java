package com.wondersgroup.springbootcachedemo.mapper;

import com.wondersgroup.springbootcachedemo.model.ProductCategory;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ProductCategoryMapper {
    @Select("select * from product_category where category_id=#{id}")
    public ProductCategory getPcById(Integer id);

    @Insert("insert into product_category(category_name,category_type) values(#{categoryName},#{categoryType})")
    public void insertPc(ProductCategory productCategory);

    @Delete("delete from product_category where category_id=#{id}")
    public void deletePc(Integer id);

    @Update("update product_category set category_name=#{categoryName},category_type=#{categoryType} where category_id=#{categoryId}")
    public void updatePc(ProductCategory productCategory);
}
