package com.lzn.mq.rabbitmqdemo.rabbitmq.impl;

import com.lzn.mq.rabbitmqdemo.rabbitmq.IRabbitMqExchange;
import com.lzn.mq.rabbitmqdemo.rabbitmq.IRabbitMqRouting;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 缓存消息对象
 */
@Getter
@Setter
@ToString
public class MessageData {
    /**
     * RabbitMq Exchange
     */
    private IRabbitMqExchange exchange;
    /**
     * RabbitMq routing
     */
    private IRabbitMqRouting routing;
    /**
     * 消息体
     */
    private Object msg;
    /**
     * 延时时间
     */
    private long delay;
    /**
     * 创建时间
     */
    private long now;
    /**
     * 重试次数
     */
    private int retryCount;

    public MessageData() {

    }

    public MessageData(IRabbitMqExchange exchange, IRabbitMqRouting routing, Object msg) {
        this.exchange = exchange;
        this.routing = routing;
        this.msg = msg;
        this.delay = 0;
        this.now = System.currentTimeMillis();
        this.retryCount = 0;
    }

    public MessageData(IRabbitMqExchange exchange, IRabbitMqRouting routing, Object msg, long delay) {
        this.exchange = exchange;
        this.routing = routing;
        this.msg = msg;
        this.delay = delay;
        this.now = System.currentTimeMillis();
        this.retryCount = 0;
    }

    public void addRetryCount() {
        this.retryCount ++;
    }
}
