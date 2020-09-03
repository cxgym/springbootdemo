package com.wondersgroup.springbootredisdemo;

import com.wondersgroup.springbootredisdemo.model.ProductCategory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.net.UnknownHostException;

@SpringBootApplication
@Configuration
public class SpringbootredisdemoApplication {

    /**
     * 配置ProductCategory对象操作redis的序列化器为json
     * @param redisConnectionFactory
     * @return
     * @throws UnknownHostException
     */
    @Bean
    public RedisTemplate<Object, ProductCategory> pcredisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException
    {
        RedisTemplate<Object,ProductCategory> template=new RedisTemplate<Object, ProductCategory>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<ProductCategory> ser=new Jackson2JsonRedisSerializer<ProductCategory>(ProductCategory.class);
        template.setDefaultSerializer(ser);   //设置setDefaultSerializer，这样的话不管是key还是value都使用这个序列化器，不要单独设置了
        return template;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootredisdemoApplication.class, args);
    }

}
