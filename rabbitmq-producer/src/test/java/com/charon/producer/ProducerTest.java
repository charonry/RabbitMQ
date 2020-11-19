package com.charon.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
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


    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 确认模式
     *  步骤：
     *      1.确认模式开启:在connectionFactory设置publisher-confirms="true"
     *      2.在rabbitTemplate中定义confirmCallback回调函数
     */
    @Test
    public void  testConfirm(){
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             *
             * @param correlationData 配置信息
             * @param ack exchange交换机是否成功收到小心 true：成功；false：失败
             * @param cause 失败的原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("producer-> exchange;confirmCallback回调函数被执行");
                if(ack){
                    System.out.println("成功接收到消息");
                }else {
                    System.out.println("失败原因："+cause);
                }
            }
        });
        rabbitTemplate.convertAndSend("test_exchange_confirm","confirm","hello confirmCallback");
    }

    /**
     * 回退模式：当消息发送给exchange后，由exchange到queue失败时候才会执行
     *  步骤：
     *      1.回退模式开启:在connectionFactory设置ppublisher-returns="true"
     *      2.设置returnCallBack
     *      3.设置exchange处理消息的模式；
     *          1.消息没有路由到queue，消息丢弃
     *          2.消息没有路由到queue，返回给消息的发送方
     */
    @Test
    public void  testReturn(){
        // 设置交换机处理失败消息的模式
        rabbitTemplate.setMandatory(true);
        /**
         *
         * @param message   消息对象
         * @param replyCodse    失败码
         * @param replyText 失败信息
         * @param exchange  交换机
         * @param routingKey    路由键
         */
        rabbitTemplate.setReturnCallback((Message message,int replyCodse,String replyText,String exchange,
                                          String routingKey)->{
            System.out.println("exchange -> queue;returnCallback执行了");
            System.out.println("消息对象"+message);
            System.out.println("失败码"+replyCodse);
            System.out.println("失败信息"+replyText);
            System.out.println("交换机"+exchange);
            System.out.println("路由键"+routingKey);
        });
        rabbitTemplate.convertAndSend("test_exchange_confirm","confirm","hello returnCallback");
    }
}
