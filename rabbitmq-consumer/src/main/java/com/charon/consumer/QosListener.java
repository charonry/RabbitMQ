package com.charon.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * @program: rabbitMQ
 * @description Consumer的限流机制
 *      1.确保Ack的机制为手动确认
 *      2.listener-container的配置属性
 *          refetch = "1",表示消费端每次都从mp拉去一条消息，直到手动消息确认完毕，才会接收下一条消息。
 * @author: charon
 * @create: 2020-11-25 21:06
 **/
@Component
public class QosListener implements ChannelAwareMessageListener {


    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        Thread.sleep(1000);
        System.out.println(new String(message.getBody()));
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        channel.basicAck(deliveryTag,true);
    }
}
