package com.lzn.mq.rabbitmqdemo.rabbitmq.config;

import com.lzn.mq.rabbitmqdemo.rabbitmq.IRabbitMqExchange;

/**
 * RabbitMq Exchange(交换机)定义
 * */
public enum RabbitMqExchange implements IRabbitMqExchange {

    MQ_EXCHANGE_TEST("mq.exchange.test") ;

    private String exchangeName;

    @Override
    public String exchangeName() {
        return this.exchangeName;
    }

    RabbitMqExchange(String exchangeName){
        this.exchangeName = exchangeName;
    }
}
