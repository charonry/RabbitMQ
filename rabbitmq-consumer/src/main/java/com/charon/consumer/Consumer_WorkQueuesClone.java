package com.charon.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @program: RabbitMQ
 * @description MQ的消费者 work queues工作模式的消费者
 * @author: charon
 * @create: 2020-11-16 23:20
 **/
public class Consumer_WorkQueuesClone {
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
        // 5.创建队列Queue
        final String QUEUQ_NAME = "work_queues";
        channel.queueDeclare(QUEUQ_NAME,true,false,false,null);
        // 6.接受消息
        channel.basicConsume(QUEUQ_NAME,true,new DefaultConsumer(channel){
            /**
             * 回调函数 接受消息后自动执行
             * @param consumerTag：标识
             * @param envelope：获取一些信息，交换机、路由key
             * @param properties：配置信息
             * @param body：数据
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("msg:"+new String(body));
            }
        });
    }
}
