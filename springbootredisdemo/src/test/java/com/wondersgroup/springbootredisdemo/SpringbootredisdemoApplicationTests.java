package com.wondersgroup.springbootredisdemo;

import com.wondersgroup.springbootredisdemo.model.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootredisdemoApplicationTests {

    @Autowired
    StringRedisTemplate stringRedisTemplate;   //操作k-v都是字符串的

    @Autowired
    RedisTemplate redisTemplate;    //操作k-v都是对象的

    @Autowired
    RedisTemplate<Object,ProductCategory> pcredisTemplate;   //自定义redis序列化器
    /**
     * Redis常见的五大数据类型
     * String（字符串）、List（列表）、Set（集合）、Hash（散列）、ZSet（有序集合）
     * 更多使用方法自行百度
     * stringRedisTemplate.opsForValue() [String（字符串）]
     * stringRedisTemplate.opsForList() [List（列表）]
     * stringRedisTemplate.opsForSet() [Set（集合）]
     * stringRedisTemplate.opsForHash() [Hash（散列）]
     * stringRedisTemplate.opsForZSet() [ZSet（有序集合）]
     */
    @Test
    public void contextLoads() {
        stringRedisTemplate.opsForValue().append("msg","hello");
        String msg = stringRedisTemplate.opsForValue().get("msg");
        System.out.println(msg);

        stringRedisTemplate.opsForList().leftPush("list","hello01");
        stringRedisTemplate.opsForList().leftPush("list","hello02");
        List newList = new ArrayList();
        newList.add("hello03");
        newList.add("hello04");
        newList.add("hello05");
        stringRedisTemplate.opsForList().leftPushAll("list",newList);
        for (String list:stringRedisTemplate.opsForList().range("list",0,-1)) {
            System.out.println(list);
        }
    }

    /**
     * 测试操作对象
     */
    @Test
    public void Test01() {
        ProductCategory pc=new ProductCategory();
        pc.setCategoryId(2);
        pc.setCategoryName("测试redis操作对象");
        //默认保存对象使用jdk序列化机制，序列化后的数据保存到redis
        //redisTemplate.opsForValue().set("object",pc);
        //将数据以json方式保存，（1）自己将对象转为json   (2)使用RedisTemplate默认的序列化规则，然后自定义成json序列化器
        pcredisTemplate.opsForValue().set("object",pc);
    }

}