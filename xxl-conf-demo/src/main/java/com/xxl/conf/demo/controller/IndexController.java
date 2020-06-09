package com.xxl.conf.demo.controller;

import com.xxl.conf.core.XxlConfClient;
import com.xxl.conf.core.listener.XxlConfListener;
import com.xxl.conf.demo.bean.AnnotationConfBean;
import com.xxl.conf.demo.bean.XmlConfBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * @author xuxueli 2018-02-04 01:27:30
 */
@Controller
public class IndexController {
    private static Logger logger = LoggerFactory.getLogger(IndexController.class);

    static {
        /**
         * 配置变更监听示例：可开发Listener逻辑，监听配置变更事件；可据此实现动态刷新JDBC连接池等高级功能；
         */
        XxlConfClient.addListener("demo.param.by.anno", new XxlConfListener(){
            @Override
            public void onChange(String key, String value) throws Exception {
                logger.info("配置变更事件通知：{}={}", key, value);
            }
        });
    }

    @Resource
    private AnnotationConfBean annotationConfBean;


    @Resource
    private XmlConfBean xmlConfBean;

    @RequestMapping("/test")
    @ResponseBody
    public List<String> index(){

        List<String> list = new LinkedList<>();

        /**
         * 方式1: API方式
         */
        String paramByApi = XxlConfClient.get("demo.param.by.api", null);
        list.add("1、API方式: default.key01=" + paramByApi);

        /**
         * 方式2: @XxlConf 注解方式
         */
        list.add("2、@XxlConf 注解方式: demo.param.by.anno=" + annotationConfBean.paramByAnno);
        /**
         * 方式3: XML占位符方式
         */
        list.add("3、XML占位符方式: demo.param.by.xml=" + xmlConfBean.paramByXml);
        /**
         * 方式3: 属性文件占位符方式
         */
        list.add("4、读取application.properties demo.param.by.properties =" + annotationConfBean.testConf);
        return list;
    }

}
