package com.charon.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * @program: rabbitMQ
 * @description 消费端的死信队列拒收消息
 * @author: charon
 * @create: 2020-11-19 22:15
 **/
@Component
public class DlxListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            System.out.println(new String(message.getBody()));
            //模拟出错
            int i = 3/0;
            // 手动签收
            channel.basicAck(deliveryTag,true);
        }catch (Exception e){
            System.out.println("出现异常，拒绝接受消息");
            // 不重回队列:requeue = false
            channel.basicNack(deliveryTag,true,false);
        }

    }
}
