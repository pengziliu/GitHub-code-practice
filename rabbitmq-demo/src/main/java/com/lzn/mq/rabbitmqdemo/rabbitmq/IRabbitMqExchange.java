package com.lzn.mq.rabbitmqdemo.rabbitmq;

import java.util.Map;

public interface IRabbitMqExchange {

    /**
     * Exchange(交换机) 的名称
     * */
    String exchangeName();

    /**
     * exchange类型 DIRECT("direct"), FANOUT("fanout"), TOPIC("topic"), HEADERS("headers")
     * */
    default String type(){return "topic";}

    /**
     * 是否持久化
     */
    default boolean durable(){return true;}

    /**
     * 当所有队列在完成使用此exchange时，是否删除
     */
    default boolean autoDelete(){return false;}

    /**
     * 是否允许直接binding
     * 如果是true的话 则不允许直接binding到此 exchange
     */
    default boolean internal(){ return false;}

    /**
     * 其他的一些参数设置
     */
    default Map<String, Object> arguments(){ return null; }

    /**
     * 延时 Exchange
     * */
    default String delayExchangeName() {return "delay."+exchangeName();}

}
