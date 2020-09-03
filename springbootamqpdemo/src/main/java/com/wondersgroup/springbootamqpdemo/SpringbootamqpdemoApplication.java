package com.wondersgroup.springbootamqpdemo;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**本程序说明：
 * 自动配置，这些信息可以自己Ctrl+Alt+鼠标左键到注解的方法里了解
 * 1、RabbitAutoConfiguration
 * 2、有自动配置的连接工厂ConnectionFactory
 * 3、RabbitProperties 封装了RabbitMQ的配置
 * 4、RabbitTemplate 给RabbitMQ发送和接收消息
 * 5、AmqpAdmin RabbitMQ系统管理功能组件 创建和删除Queue、Exchange、Binding
 *              在后面的代码演示中将用AmqpAdmin组件分别创建不同的Exchange:direct(单播)、fanout(广播)、topic(主题路由匹配规则)
 *              来实现不同模式的消息机制
 * 6、@EnableRabbit + @RabbitListener 监听消息队列的内容
 */
@EnableRabbit    //开启基于注解的RabbitMQ模式
@SpringBootApplication
@Configuration
public class SpringbootamqpdemoApplication {

    /**
     * 指定RabbitMQ的序列化规则为json
     * @return
     */
    @Bean
    public MessageConverter MessageConverter()
    {
        return new Jackson2JsonMessageConverter();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootamqpdemoApplication.class, args);
    }

}