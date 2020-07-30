package com.lzn.mybatisplus.codegenerator.entity;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 数据库表名 test_goods
 *
 * @author liuzhinan
 * @date 2020-07-22
 */
@Getter
@Setter
@ToString
public class Goods  {

    /**
     * 数据库字段名 id 类型 bigint(20)
     */
    private Long  id;

    /**
     * 数据库字段名 goods_sn 类型 varchar(45)
     */
    private String  goodsSn;

    /**
     * 数据库字段名 name 类型 varchar(255)
     */
    private String  name;

    /**
     * 数据库字段名 title 类型 varchar(80)
     */
    private String  title;

    /**
     * 数据库字段名 price 类型 decimal(10,2)
     */
    private BigDecimal  price;

    /**
     * 数据库字段名 status 类型 int(2)
     */
    private Integer  status;

    /**
     * 数据库字段名 sale_count 类型 int(11)
     */
    private Integer  saleCount;

    /**
     * 数据库字段名 create_date 类型 datetime
     */
    private Date  createDate;

    /**
     * 数据库字段名 modify_date 类型 datetime
     */
    private Date  modifyDate;


}