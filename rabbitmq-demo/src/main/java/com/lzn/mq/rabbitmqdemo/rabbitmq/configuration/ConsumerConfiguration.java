package com.lzn.mq.rabbitmqdemo.rabbitmq.configuration;

import com.lzn.mq.rabbitmqdemo.rabbitmq.config.RabbitMqQueue;
import com.lzn.mq.rabbitmqdemo.rabbitmq.consumer.TestConsumer;
import com.lzn.mq.rabbitmqdemo.rabbitmq.impl.DefaultRabbitMqRegister;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerConfiguration {
    /**
     * 使用 默认的注册器 注册 消息队列的消费者(消息处理器)
     */
    @Bean
    Object listenerRabbit(DefaultRabbitMqRegister rabbitMqRegister) {
        rabbitMqRegister.listenerQueue(testConsumer(), RabbitMqQueue.MQ_QUEUE_TEST);
        return null;
    }
    @Bean
    TestConsumer testConsumer(){
        return new TestConsumer();
    }


}
