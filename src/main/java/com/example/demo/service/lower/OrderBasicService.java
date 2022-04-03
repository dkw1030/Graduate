package com.example.demo.service.lower;

import com.example.demo.mapper.OrderMapper;
import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.Model.Order;
import com.example.demo.model.Model.OrderItem;
import com.example.demo.model.Model.User;
import com.example.demo.model.Model.resultType.OrderInfo;
import com.example.demo.utils.LogUtil;
import com.example.demo.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderBasicService {

    @Autowired
    OrderMapper orderMapper;

    public ResultDTO<List<OrderInfo>> getOrderByUser(User user){
        List<OrderInfo> orders;
        if(user.getUserRole() == 0){
            orders = orderMapper.getOrderByBuyerId(user.getUserId());
        }else{
            orders = orderMapper.getOrderByCompanyId(user.getCompanyId());
        }

        ResultDTO<List<OrderInfo>> resultDTO = new ResultDTO<>();
        resultDTO.setCode(0);
        resultDTO.setData(orders);
        return resultDTO;
    }

    public ResultDTO<String> uploadOrder(List<List<String>> data) throws Exception{
        ResultDTO<String> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        resultDTO.setData("failed");

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId(TimeUtil.getTimeStamp());
        orderInfo.setOrderName(data.get(1).get(1));
        orderInfo.setOrderDescription(data.get(3).get(2));
        orderInfo.setOrderStatus(0);
        orderInfo.setBuyerId(data.get(3).get(1));
        orderInfo.setSellerId(data.get(3).get(0));
        orderInfo.setOrderUploadTime(new Date());
        orderInfo.setFirstTime(0);

        List<OrderItem> orderItems = new ArrayList<OrderItem>();
        for (int i = 5; i < data.size(); i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderInfo.getOrderId());
            orderItem.setItemName(data.get(i).get(0));
            orderItem.setItemType(data.get(i).get(1));
            orderItem.setNumber(Integer.parseInt(data.get(i).get(2)));
            orderItem.setCost(Integer.parseInt(data.get(i).get(3)));
            orderItems.add(orderItem);
        }
        int orderResult = orderMapper.insertOrder(orderInfo);
        LogUtil.log(getClass().getName(), "order insert finish\n" + orderResult);
        int itemsResult = orderMapper.insertItems(orderItems);
        LogUtil.log(getClass().getName(), "item insert finish\n" + itemsResult);

        resultDTO.setData("success");
        resultDTO.setCode(0);
        return resultDTO;
    }
}
