package com.charon.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @program: RabbitMQ
 * @description MQ的发送端  Topics通配符的生产者
 * @author: charon
 * @create: 2020-11-16 22:32
 **/
public class Producer_Topics {
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
        final  String EXCHANGE_NAME = "test_topics";
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC,true,false,false,null);
        // 6.创建队列
        final  String QUEUQ_NAME_ONE = "test_topics_queue_one";
        final  String QUEUQ_NAME_TWO = "test_topics_queue_two";
        channel.queueDeclare(QUEUQ_NAME_ONE,true,false,false,null);
        channel.queueDeclare(QUEUQ_NAME_TWO,true,false,false,null);
        // 7.绑定交换机和队列的关系
        channel.queueBind(QUEUQ_NAME_ONE,EXCHANGE_NAME,"#.error");
        channel.queueBind(QUEUQ_NAME_ONE,EXCHANGE_NAME,"linux.*");
        channel.queueBind(QUEUQ_NAME_TWO,EXCHANGE_NAME,"*.*");
        // 8.发送消息
        final String body = "这是广播类型交换机信息";
        channel.basicPublish(EXCHANGE_NAME,"rabbitmq.linux",null,body.getBytes());
        // 9.释放资源
        channel.close();
        connection.close();
    }
}
