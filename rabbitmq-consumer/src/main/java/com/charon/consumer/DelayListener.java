package com.charon.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * @program: rabbitMQ
 * @description 消费端的延迟消费
 * @author: charon
 * @create: 2020-11-19 22:15
 **/
@Component
public class DelayListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            System.out.println(new String(message.getBody()));
            System.out.println("根据情况去判断");
            // 手动签收
            channel.basicAck(deliveryTag,true);
        }catch (Exception e){
            System.out.println("出现异常，拒绝接受消息");
            // 不重回队列:requeue = false
            channel.basicNack(deliveryTag,true,false);
        }

    }
}
