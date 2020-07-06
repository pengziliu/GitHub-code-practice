package com.lzn.mq.rabbitmqdemo.rabbitmq.config;

import com.lzn.mq.rabbitmqdemo.rabbitmq.IRabbitMqRouting;

/**
 * RabbitMq routing（路由定义）
 * */
public enum RabbitMqRouting implements IRabbitMqRouting {
    MQ_ROUTING_TEST("mq.routing.test");

    private String routingKey;

    @Override
    public String routingKey() {
        return this.routingKey;
    }

    RabbitMqRouting(String routingKey){
        this.routingKey = routingKey;
    }
}
