package com.charon.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * @program: rabbitMQ
 * @description 消费端的消息确认
 *  Consumer Ack机制：
 *      1.设置手动签收机制acknowledge="manual"
 *          1.acknowledge="auto"  自动签收 异常被丢弃
 *          2.acknowledge="manual" 手动签收
 *          3.acknowledge="none" 根据异常签收
 *      2.让监听器实现ChannelAwareMessageListener接口
 *      3.消息成功处理后调用channel.basicAck()接受
 *      4.消息异常调用channel.basicNack()拒绝签收，让broker重新发送
 * @author: charon
 * @create: 2020-11-19 22:15
 **/
@Component
public class AckListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        Thread.sleep(1000);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            System.out.println(new String(message.getBody()));
            //模拟出错
            int i = 3/0;
            // 手动签收
            channel.basicAck(deliveryTag,true);
        }catch (Exception e){
            // 第三个参数requeue;重回队列
            channel.basicNack(deliveryTag,true,true);
            //channel.basicReject(deliveryTag,true);
        }

    }
}
