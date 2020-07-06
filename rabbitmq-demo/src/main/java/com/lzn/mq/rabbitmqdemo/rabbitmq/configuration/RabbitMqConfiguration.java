package com.lzn.mq.rabbitmqdemo.rabbitmq.configuration;

import com.lzn.mq.rabbitmqdemo.rabbitmq.config.RabbitMqBinding;
import com.lzn.mq.rabbitmqdemo.rabbitmq.config.RabbitMqExchange;
import com.lzn.mq.rabbitmqdemo.rabbitmq.config.RabbitMqQueue;
import com.lzn.mq.rabbitmqdemo.rabbitmq.impl.DefaultRabbitMqRegister;
import com.lzn.mq.rabbitmqdemo.rabbitmq.impl.RabbitMqServiceImpl;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class RabbitMqConfiguration {

        /**
         * 默认采用rabbitMQ
         */
        @Bean
        public RabbitMqServiceImpl rabbitMqService() {
            return new RabbitMqServiceImpl();
        }

        /**
         * 创建 默认的RabbitMQ 注册器
         */
        @Bean
        DefaultRabbitMqRegister rabbitMqRegister(ConnectionFactory connectionFactory) {
            return new DefaultRabbitMqRegister(connectionFactory);
        }

        /**
         * 使用 默认的注册器 注册 Exchange Queue Binding
         */
        @Bean
        Object registerRabbit(DefaultRabbitMqRegister rabbitMqRegister) throws IOException {
            rabbitMqRegister.registerExchange(RabbitMqExchange.values());
            rabbitMqRegister.registerQueue(RabbitMqQueue.values());
            rabbitMqRegister.registerBinding(RabbitMqBinding.values());
            return new Object();
        }
}
