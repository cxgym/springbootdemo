package com.wondersgroup.springbootamqpdemo.service;

import com.wondersgroup.springbootamqpdemo.model.Book;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * 监听RabbitMQ消息队列  @EnableRabbit + @RabbitListener
 * 先启动SpringbootamqpdemoApplication才会生效
 */
@Service
public class BookService {

    /**
     * 监听消息队列信息
     * @param book
     */
    //@RabbitListener(queues = "amqpadmin.queue01")
    @RabbitListener(queues = {"amqpadmin.queue01","amqpadmin.queue02","amqpadmin.queue03","amqpadmin.queue04"})
    public void receive01(Book book)
    {
        System.out.println("收到消息:"+book);
    }

    /**
     * 监听消息队列头信息
     * @param message
     */
    //@RabbitListener(queues = "amqpadmin.queue01")
    //public void receive02(Message message)
    //{
    //   System.out.println(message.getBody());
    //    System.out.println(message.getMessageProperties());
    //}
}
