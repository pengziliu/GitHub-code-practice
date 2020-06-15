package com.lzn.shardingsphere;

import com.lzn.shardingsphere.model.Order;
import com.lzn.shardingsphere.model.OrderExample;
import com.lzn.shardingsphere.service.IOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class ShardingsphereApplicationTests {

    @Resource
    IOrderService orderService;

    @Test
    void contextLoads() {
    }

    @Test
    void TestInsertOrder(){
        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order("111111",100L,new Date(),1));
        orderList.add(new Order("222222",101L,new Date(),1));
        orderList.add(new Order("333333",102L,new Date(),1));
        orderList.add(new Order("444444",103L,new Date(),1));
        for(Order order:orderList){
            orderService.createOrder(order);
        }
    }


    @Test
    void TestListOrder(){
        OrderExample orderExample = new OrderExample();
        List<Order> orderList= orderService.listOrder(orderExample);
        for (Order o:orderList){
            System.out.println(o.toString());
        }
    }
    @Test
    void TestListOrder2(){
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andMemberIdEqualTo(101L);
        List<Order> orderList= orderService.listOrder(orderExample);
        for (Order o:orderList){
            System.out.println(o.toString());
        }
    }



}
