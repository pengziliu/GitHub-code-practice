package com.lzn.shardingsphere.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Order {

    private Long id;

    private String orderSn;

    private Long memberId;

    private Date createTime;

    private Integer status;
    public Order(){

    }

    public Order(String orderSn, Long memberId, Date createTime, Integer status) {
        this.orderSn = orderSn;
        this.memberId = memberId;
        this.createTime = createTime;
        this.status = status;
    }
}