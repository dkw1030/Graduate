package com.example.demo.model.Model;

import lombok.Data;

@Data
public class OrderItem {
    /**
     * 订单号
     */
    private String orderId;

    /**
     * 数量
     */
    private int number;

    /**
     * 单价
     */
    private int cost;

    /**
     * 物料名
     */
    private String itemName;

    /**
     * 型号
     */
    private String itemType;

    /**
     * 进度
     */
    int process;
}
