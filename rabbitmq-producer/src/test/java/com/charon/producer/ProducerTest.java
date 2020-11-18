package com.charon.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @program: rabbitMQ
 * @description
 * @author: charon
 * @create: 2020-11-18 21:17
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq-producer.xml")
public class ProducerTest {

    // 1.注入rabbitTemplate操作RabbitMQ
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送简单或者workQueue消息
     */
    @Test
    public void  testProducer(){
        // 2.发送消息
        rabbitTemplate.convertAndSend("spring_queue","hello spring_queue");
    }


    /**
     * 发送PubSun消息
     */
    @Test
    public void  testProducerFanout(){
        // 2.发送消息
        rabbitTemplate.convertAndSend("spring_fanout_exchange","","hello spring_fanout_ALL");
    }


    /**
     * 发送主题消息
     */
    @Test
    public void  testProducerTopics(){
        // 2.发送消息
        rabbitTemplate.convertAndSend("spring_topic_exchange","charon.wen.crane","hello spring_topic");
    }
}
