/**
 * Copyright: Copyright (c) 2012
 * Company:深圳市海乐淘电子商务有限公司
 * @author liuf
 * @date 2013-2-27 下午4:54:13
 * @version V1.0
 */
package com.lzn.mybatisplus.codegenerator.utils.entity;

import java.io.Serializable;
import java.util.List;

/**
 * omui数据结果集
 */
public class GridDataModel<T> implements Serializable
{
	private static final long serialVersionUID = 1L;

	private List<T> rows;
	
	private Long total;

	public List<T> getRows()
	{
		return rows;
	}

	public void setRows(List<T> rows)
	{
		this.rows = rows;
	}

	public Long getTotal()
	{
		return total;
	}

	public void setTotal(Long total)
	{
		this.total = total;
	}
	
	

}
