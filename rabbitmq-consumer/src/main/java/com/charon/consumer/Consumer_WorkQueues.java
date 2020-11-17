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
public class Consumer_WorkQueues {
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
        /*String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
         * 参数：
         *   1.queue：队列名称 如果没有一个该队列名则会创建一个叫这名字的队列；如果有就不会创建。
         *   2.durable：是否持久化：当mq重启后还存在
         *   3.exclusive：（1）是否独占，只能有一个消费者监听。（2）当connection关闭是否关闭队列
         *   4.autoDelete：是否自动删除，当没有consumer时自动删除
         *   5.arguments：参数信息
         * */
        final String QUEUQ_NAME = "work_queues";
        channel.queueDeclare(QUEUQ_NAME,true,false,false,null);
        // 6.接受消息
        /*String queue, boolean autoAck, Consumer callback
        *   1.queue：队列名称
        *   2.autoAck：是否自动确认
        *   3.callback：回调函数
        * */
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
