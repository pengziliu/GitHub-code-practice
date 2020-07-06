package com.lzn.mq.rabbitmqdemo.rabbitmq;


public interface IRabbitMqBinding {
    /**
     * 需要绑定的exchange(交换机)
     * */
    IRabbitMqExchange exchange();

    /**
     * 需要绑定的routing(路由)
     * */
    IRabbitMqRouting routing();

    /**
     * 需要绑定的queue(队列)
     * */
    IRabbitMqQueue queue();

    /**
     * 消息队列是否允许延时
     * */
    boolean allowDelay();
}
