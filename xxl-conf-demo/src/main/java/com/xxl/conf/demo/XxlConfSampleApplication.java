package com.xxl.conf.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author xuxueli 2018-02-24 22:00:52
 */
@SpringBootApplication
@ImportResource(locations = {"classpath:xxl-conf.xml"})
public class XxlConfSampleApplication {

	public static void main(String[] args) {
        SpringApplication.run(XxlConfSampleApplication.class, args);
	}

}