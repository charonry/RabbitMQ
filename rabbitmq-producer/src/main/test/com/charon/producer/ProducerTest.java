package com.charon.producer;

import com.charon.producer.config.RabbitMQConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: rabbitMQ
 * @description
 * @author: charon
 * @create: 2020-11-18 22:54
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProducerTest {

    // 1.注入RabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void test(){
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,"boot.charon.crane","hello boot rabbitmq");
    }
}
