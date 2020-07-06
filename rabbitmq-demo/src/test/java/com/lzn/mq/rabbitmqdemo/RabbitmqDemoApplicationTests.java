package com.lzn.mq.rabbitmqdemo;

import com.lzn.mq.rabbitmqdemo.rabbitmq.IRabbitMqService;
import com.lzn.mq.rabbitmqdemo.rabbitmq.config.RabbitMqExchange;
import com.lzn.mq.rabbitmqdemo.rabbitmq.config.RabbitMqRouting;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RabbitmqDemoApplicationTests {

    Logger logger = LoggerFactory.getLogger(RabbitmqDemoApplicationTests.class);

    @Resource
    IRabbitMqService rabbitMqService;

    @Test
   public void testSendMq(){
        logger.info("生产者发送消息到mq");
        rabbitMqService.send(RabbitMqExchange.MQ_EXCHANGE_TEST, RabbitMqRouting.MQ_ROUTING_TEST,"测试发送消息");
    }

    @Test
   public void testSendDelayMq(){
        logger.info("生产者发送延迟消息到mq");
        rabbitMqService.send(RabbitMqExchange.MQ_EXCHANGE_TEST, RabbitMqRouting.MQ_ROUTING_TEST,"测试发送延时消息60s",60*1000);
    }
}
