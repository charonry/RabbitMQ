package com.charon.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @program: RabbitMQ
 * @description MQ的发送端  pubsub订阅模式的生产者
 * @author: charon
 * @create: 2020-11-16 22:32
 **/
public class Producer_PubSub {
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
        /*String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments
        *参数
        *   1.exchange：交换机名称
        *   2.type：交换机的类型
        *       DIRECT("direct")：定向
        *       FANOUT("fanout")：广播
        *       TOPIC("topic")：通配符方式
        *       HEADERS("headers")：参数匹配
        *   3.durable：是否持久化
        *   4.autoDelete：自动删除
        *   5.internal：内部使用：false
        *   6.arguments：参数
        * */
        final  String EXCHANGE_NAME = "test_fanout";
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT,true,false,false,null);
        // 6.创建队列
        final  String QUEUQ_NAME_ONE = "test_faount_queue_one";
        final  String QUEUQ_NAME_TWO = "test_faount_queue_two";
        channel.queueDeclare(QUEUQ_NAME_ONE,true,false,false,null);
        channel.queueDeclare(QUEUQ_NAME_TWO,true,false,false,null);
        // 7.绑定交换机和队列的关系
        /*String queue, String exchange, String routingKey
        * 参数
        *   1.queue:队列名称
        *   2.exchange：交换机名称
        *   3.routingKey：路由键：绑定规则
        *       如果交换机的类型是FANOUT，那么路由键为空
        * */
        channel.queueBind(QUEUQ_NAME_ONE,EXCHANGE_NAME,"");
        channel.queueBind(QUEUQ_NAME_TWO,EXCHANGE_NAME,"");
        // 8.发送消息
        final String body = "这是广播类型交换机信息";
        channel.basicPublish(EXCHANGE_NAME,"",null,body.getBytes());
        // 9.释放资源
        channel.close();
        connection.close();
    }
}
