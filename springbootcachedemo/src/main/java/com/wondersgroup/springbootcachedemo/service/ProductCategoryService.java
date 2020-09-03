package com.wondersgroup.springbootcachedemo.service;

import com.wondersgroup.springbootcachedemo.mapper.ProductCategoryMapper;
import com.wondersgroup.springbootcachedemo.model.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@CacheConfig(cacheNames = "pc")   //全局缓存配置
@Service
public class ProductCategoryService {
    @Autowired
    ProductCategoryMapper productCategoryMapper;

    /**
     * 将方法的运行结果进行缓存，以后再要相同的数据，直接从缓存中获取，不用调用方法，再调用方法不会进入。
     * CacheManager管理多个Cache组件的，对缓存的真正CRUD操作在Cache组件中，每一个缓存组件有自己唯一一个名字。
     * 几个属性：
     *   cacheNames/value   指定缓存组件的名字
     *   key                缓存数据使用的key可以用它来指定，默认是使用方法的参数，编写key的SpEL(#id #root.args[0])
     *   keyGenerator       key的生成器，可以自己指定key的生成器的组件id，key/keyGenerator使用时二选一
     *   cacheManager       指定缓存管理器，或者cacheResolver指定获取解析器
     *   condition          指定符合条件的情况下才缓存
     *   unless             否定缓存，当unless指定的条件为true,方法的返回值就不会被缓存，可以获取到结果进行判断，unless="#result==null"
     *   sync               是否使用异步模式
     * @param id
     * @return
     */
    //查询数据库并保存缓存
    //@Cacheable(value = {"pc"},condition = "#id>0")   //{"pc","pc1"}这种写法可以同时创建多个缓存组件
    //@Cacheable(value = "pc",key = "#id")
    @Cacheable(key = "#id")   //@CacheConfig(cacheNames = "pc")
    public ProductCategory getPcById(Integer id)
    {
        System.out.println("查询商品"+id+"");
        ProductCategory pc = productCategoryMapper.getPcById(id);
        return pc;
    }

    //更新数据库和缓存，注意缓存的key一定要和之前缓存的key一致
    //@CachePut(value = "pc",key="#pc.categoryId")   //也可以用key="#result.categoryId"   result代表返回值
    @CachePut(key="#pc.categoryId")   //@CacheConfig(cacheNames = "pc")
    public ProductCategory updatePc(ProductCategory pc)
    {
        System.out.println("updatePc:" + pc);
        productCategoryMapper.updatePc(pc);
        return pc;
    }

    /**
     * 缓存清除
     * key指定要清除的数据
     * allEntries = true  指定清除这个pc中所有的缓存
     * beforeInvocation = false  true是  false否  缓存的清除是否在方法之前执行。默认是在方法之后执行（那么如果出现异常缓存就不会清除）
     * @param id
     */
    //@CacheEvict(value = "pc",key = "#id")
    @CacheEvict(key="#id")   //@CacheConfig(cacheNames = "pc")
    public void deletePc(Integer id)
    {
        System.out.println("deletePc:" + id);
        //productCategoryMapper.deletePc(id);
    }
}
