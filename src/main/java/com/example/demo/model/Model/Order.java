package com.example.demo.model.Model;

import com.example.demo.model.Model.resultType.OrderInfo;
import lombok.Data;

import java.util.List;

@Data
public class Order extends OrderInfo {

    /**
     * 订单包含的物料
     */
    List<OrderItem> OrderItems;

}
