package com.charon.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @program: RabbitMQ
 * @description MQ的发送端 work queues工作模式的生产者
 * @author: charon
 * @create: 2020-11-16 22:32
 **/
public class Producer_WorkQueues {
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
        // 6.发送消息
        /*String exchange, String routingKey, BasicProperties props, byte[] body
        * 参数：
        *   1.exchange：交换机名称。简单模式下会使用默认的""
        *   2.routingKey：路由名称
        *   3.props：配置信息
        *   4.body：字节信息（发送消息信息）
        * */
        for(int i =0;i<10;i++){
            String body = "hello work_queues"+i;
            channel.basicPublish("",QUEUQ_NAME,null,body.getBytes());
        }


        // 7.释放资源
        channel.close();
        connection.close();
    }
}
