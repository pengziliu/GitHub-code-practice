package com.lzn.shardingsphere.service;

import com.lzn.shardingsphere.model.Order;
import com.lzn.shardingsphere.model.OrderExample;

import java.util.List;

public interface IOrderService {

    Order findById(Long orderId);

    void createOrder(Order order);

    List<Order> listOrder(OrderExample orderExample);

}
