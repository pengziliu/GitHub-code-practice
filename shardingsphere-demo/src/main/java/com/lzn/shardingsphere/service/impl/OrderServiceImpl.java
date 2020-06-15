package com.lzn.shardingsphere.service.impl;

import com.lzn.shardingsphere.dao.OrderMapper;
import com.lzn.shardingsphere.model.Order;
import com.lzn.shardingsphere.model.OrderExample;
import com.lzn.shardingsphere.service.IOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public Order findById(Long orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public void createOrder(Order order) {
        orderMapper.insertSelective(order);
    }

    @Override
    public List<Order> listOrder(OrderExample orderExample) {
        return orderMapper.selectByExample(orderExample);
    }


}
