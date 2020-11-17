package com.charon.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @program: RabbitMQ
 * @description MQ的发送端  routing路由模式的生产者
 * @author: charon
 * @create: 2020-11-16 22:32
 **/
public class Producer_Routing {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1.创建工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 2.设置参数
        connectionFactory.setHost("192.168.20.129");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/itcast");
        connectionFactory.setUsername("charon");
        connectionFactory.setPassword("charon");
        // 3.创建连接Connection
        Connection connection = connectionFactory.newConnection();
        // 4.创建channel
        Channel channel = connection.createChannel();
        // 5.创建交换机
        final  String EXCHANGE_NAME = "test_direct";
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT,true,false,false,null);
        // 6.创建队列
        final  String DIRECT_NAME_ONE = "test_direct_queue_one";
        final  String DIRECT_NAME_TWO = "test_direct_queue_two";
        channel.queueDeclare(DIRECT_NAME_ONE,true,false,false,null);
        channel.queueDeclare(DIRECT_NAME_TWO,true,false,false,null);
        // 7.绑定交换机和队列的关系
        //队列1的绑定
        channel.queueBind(DIRECT_NAME_ONE,EXCHANGE_NAME,"error");
        //队列2的绑定
        channel.queueBind(DIRECT_NAME_TWO,EXCHANGE_NAME,"info");
        channel.queueBind(DIRECT_NAME_TWO,EXCHANGE_NAME,"waring");
        channel.queueBind(DIRECT_NAME_TWO,EXCHANGE_NAME,"error");
        // 8.发送消息
        final String body = "这是路由定向类型交换机信息";
        channel.basicPublish(EXCHANGE_NAME,"error",null,body.getBytes());
        // 9.释放资源
        channel.close();
        connection.close();
    }
}
