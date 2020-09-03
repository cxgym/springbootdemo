package com.wondersgroup.springbootamqpdemo;

import com.wondersgroup.springbootamqpdemo.model.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootamqpdemoApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;

    /**执行下面步骤前首先创建队列
     * 创建队列
     */
    @Test
    public void createQueue()
    {
        amqpAdmin.declareQueue(new Queue("amqpadmin.queue01",true));
        amqpAdmin.declareQueue(new Queue("amqpadmin.queue02",true));
        amqpAdmin.declareQueue(new Queue("amqpadmin.queue03",true));
        amqpAdmin.declareQueue(new Queue("amqpadmin.queue04",true));
        System.out.println("创建队列完成queue01 queue02 queue03 queue04完成");
    }

    /**
     * 创建exchange，包括direct(单播)、fanout(广播)、topic(路由)
     */
    @Test
    public void createExchange()
    {
        //durable是否持久化，true下次重新启动rabbitmq时还有这个exchange
        amqpAdmin.declareExchange(new DirectExchange("amqpadmin.direct.exchange",true,false));
        System.out.println("创建direct exchange完成");
        amqpAdmin.declareExchange(new FanoutExchange("amqpadmin.fanout.exchange",true,false));
        System.out.println("创建fanout exchange完成");
        amqpAdmin.declareExchange(new TopicExchange("amqpadmin.topic.exchange",true,false));
        System.out.println("创建topic exchange完成");
    }

    /**
     * 创建绑定规则Binding，不管是单播、广播、路由都必须创建Binding，消息具体要发布给哪个队列是根据routingKey来匹配的
     * 单播sendMsg必须指定routingKey，以实现点到点的传播
     * 广播sendMsg不需要指定routingKey，所有添加进来的Binding都会收到消息
     * 路由sendMsg可以指定也可以不指定routingKey，它会根据规则去匹配符合的routingKey，以实现消息传输
     */
    @Test
    public void createBingding()
    {
        amqpAdmin.declareBinding(new Binding("amqpadmin.queue01",Binding.DestinationType.QUEUE,"amqpadmin.direct.exchange","amqp.routingkey01",null));
        amqpAdmin.declareBinding(new Binding("amqpadmin.queue02",Binding.DestinationType.QUEUE,"amqpadmin.direct.exchange","amqp.routingkey02",null));
        amqpAdmin.declareBinding(new Binding("amqpadmin.queue03",Binding.DestinationType.QUEUE,"amqpadmin.direct.exchange","amqp.routingkey03",null));
        amqpAdmin.declareBinding(new Binding("amqpadmin.queue04",Binding.DestinationType.QUEUE,"amqpadmin.direct.exchange","amqp.routingkey04",null));

        amqpAdmin.declareBinding(new Binding("amqpadmin.queue01",Binding.DestinationType.QUEUE,"amqpadmin.fanout.exchange","amqp.routingkey01",null));
        amqpAdmin.declareBinding(new Binding("amqpadmin.queue02",Binding.DestinationType.QUEUE,"amqpadmin.fanout.exchange","amqp.routingkey02",null));
        amqpAdmin.declareBinding(new Binding("amqpadmin.queue03",Binding.DestinationType.QUEUE,"amqpadmin.fanout.exchange","amqp.routingkey03",null));
        amqpAdmin.declareBinding(new Binding("amqpadmin.queue04",Binding.DestinationType.QUEUE,"amqpadmin.fanout.exchange","amqp.routingkey04",null));

        amqpAdmin.declareBinding(new Binding("amqpadmin.queue01",Binding.DestinationType.QUEUE,"amqpadmin.topic.exchange","amqp.routingkey.#",null));
        amqpAdmin.declareBinding(new Binding("amqpadmin.queue02",Binding.DestinationType.QUEUE,"amqpadmin.topic.exchange","amqp.routingkey.#",null));
        amqpAdmin.declareBinding(new Binding("amqpadmin.queue03",Binding.DestinationType.QUEUE,"amqpadmin.topic.exchange","*.news",null));
        amqpAdmin.declareBinding(new Binding("amqpadmin.queue04",Binding.DestinationType.QUEUE,"amqpadmin.topic.exchange","*.news",null));
    }

    /**
     * 使用RabbitTemplate发步单播消息
     */
    @Test
    public void send()
    {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("msg","这是一个测试消息的msg");
        map.put("data", Arrays.asList("helloworld",123,true));
        rabbitTemplate.convertAndSend("amqpadmin.direct.exchange","amqp.routingkey01",map);
    }

    /**
     * 使用RabbitTemplate获取指定队列的消息，如果想看效果记得把BookService中的监听注释
     */
    @Test
    public void receive()
    {
        Object o=rabbitTemplate.receiveAndConvert("amqpadmin.queue01");
        System.out.println(o.getClass());
        System.out.println(o);
    }

    /**
     * 发送单播，验证BookService中的消息监听
     * 指定routingKey=amqp.routingkey01 只有amqpadmin.queue01队列会收到消息
     */
    @Test
    public void sendMsg01()
    {
        rabbitTemplate.convertAndSend("amqpadmin.direct.exchange","amqp.routingkey01",new Book("西游记","吴承恩"));
    }

    /**
     * 发送广播，验证BookService中的消息监听
     * 不用指定routingKey参数
     * amqpadmin.queue01 amqpadmin.queue02 amqpadmin.queue03 amqpadmin.queue04都会收到消息
     */
    @Test
    public void sendMsg02()
    {
        rabbitTemplate.convertAndSend("amqpadmin.fanout.exchange","",new Book("西游记","吴承恩02"));
    }

    /**
     * 路由模式发布，验证BookService中的消息监听
     * 如果routingKey=amqp.routingkey.hello 则根据匹配规则amqp.routingkey.#只有amqpadmin.queue01、amqpadmin.queue02会收到消息
     * 如果routingKey=hello.news 则根据匹配规则*.news只有amqpadmin.queue03、amqpadmin.queue04会收到消息
     * 如果routingKey=amqp.routingkey.news 则根据匹配规则amqp.routingkey.#和*.news有amqpadmin.queue01、amqpadmin.queue02、amqpadmin.queue03、amqpadmin.queue04会收到消息
     */
    @Test
    public void sendMsg03()
    {
        rabbitTemplate.convertAndSend("amqpadmin.topic.exchange","amqp.routingkey.hello",new Book("西游记","吴承恩02"));
    }

    @Test
    public void contextLoads() {

    }

}