package com.xxl.conf.demo.bean;

import com.xxl.conf.core.annotation.XxlConf;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *  测试示例（可删除）
 *
 *  @author xuxueli
 */
@Component
public class AnnotationConfBean {

	@XxlConf("demo.param.by.anno")
	public String paramByAnno;


	@Value("${test.conf}")
	public String testConf;
}
