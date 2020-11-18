package com.charon.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @program: rabbitMQ
 * @description
 * @author: charon
 * @create: 2020-11-18 23:02
 **/
@Component
public class RabbitMQListener {

    @RabbitListener(queues = "boot_topic_queue")
    public void messageListener(Message message){
       // System.out.println("eeeee");
        System.out.println(new String(message.getBody()));
    }
}
