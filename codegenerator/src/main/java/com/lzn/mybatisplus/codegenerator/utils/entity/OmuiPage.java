/**
 * Copyright: Copyright (c) 2014
 * Company:可为易达国际货运代理（深圳）有限公司
 * @author liuf
 * @date 2014年3月31日 下午12:11:35
 * @version V1.0
 */
package com.lzn.mybatisplus.codegenerator.utils.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * operamask-ui 分页对象
 */
public class OmuiPage implements Serializable{

	private static final long serialVersionUID = 8651982601185223350L;
	private int start;
	private int limit;
	private String sortBy;
	private String sortDir;
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public String getSortDir() {
		return sortDir;
	}
	public void setSortDir(String sortDir) {
		this.sortDir = sortDir;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	
}
