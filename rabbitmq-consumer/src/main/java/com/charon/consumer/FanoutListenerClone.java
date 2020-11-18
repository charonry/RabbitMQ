package com.charon.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @program: rabbitMQ
 * @description
 * @author: charon
 * @create: 2020-11-18 21:40
 **/
public class FanoutListenerClone implements MessageListener {

    @Override
    public void onMessage(Message message) {
        //打印消息
        System.out.println(new String(message.getBody()));
    }
}
