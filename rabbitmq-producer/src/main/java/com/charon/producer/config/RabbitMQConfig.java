package com.charon.producer.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: rabbitMQ
 * @description rabbitMQ的生产者配置
 * @author: charon
 * @create: 2020-11-18 22:37
 **/
@Configuration
public class RabbitMQConfig {

    public final static  String EXCHANGE_NAME = "boot_topic_exchange";
    public final static  String QUEUE_NAME = "boot_topic_queue";

    /**
     *  1.创建交换机
     * @return  交换机
     */
    @Bean("bootExchange")
    public Exchange bootExchange(){
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
    }

    /**
     *  2.创建Queue队列
     * @return  Queue队列
     */
    @Bean("bootQueue")
    public Queue bootQueue(){
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    /**
     * 3.队列和交换机绑定关系
     * @param queue 队列
     * @param exchange 交换机
     * @return
     */
    @Bean
    public Binding bindQueueExchange(@Qualifier("bootQueue") Queue queue, @Qualifier("bootExchange") Exchange exchange){
        return  BindingBuilder.bind(queue).to(exchange).with("boot.#").noargs();
    }
}
