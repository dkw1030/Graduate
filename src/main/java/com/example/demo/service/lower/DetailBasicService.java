package com.example.demo.service.lower;

import com.example.demo.mapper.OrderMapper;
import com.example.demo.model.DTO.OrderSearchDTO;
import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.Model.Order;
import com.example.demo.model.Model.OrderItem;
import com.example.demo.model.Model.resultType.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailBasicService{

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderBasicService orderBasicService;

    public ResultDTO<Order> getOrderDetail(String orderId) throws Exception{
        ResultDTO<Order> resultDTO = new ResultDTO<>();
        OrderSearchDTO orderSearchDTO = new OrderSearchDTO();
        orderSearchDTO.setOrderId(orderId);

        OrderInfo orderInfo = orderBasicService.searchOrders(orderSearchDTO).getData().get(0);
        Order order = new Order();
        order.setOrderInfo(orderInfo);
        List<OrderItem> items = orderMapper.getItemsByOrderId(orderId);
        order.setOrderItems(items);
        resultDTO.setData(order);
        return resultDTO;
    }
}
