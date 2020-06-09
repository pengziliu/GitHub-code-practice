package com.xxl.conf.demo.bean;

import com.xxl.conf.core.annotation.XxlConf;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *  测试示例（可删除）
 *
 *  @author xuxueli
 */
public class XmlConfBean {

	public String paramByXml;

	public void setParamByXml(String paramByXml) {
		this.paramByXml = paramByXml;
	}

}
