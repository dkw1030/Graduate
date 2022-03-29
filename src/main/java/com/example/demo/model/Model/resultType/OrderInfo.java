package com.example.demo.model.Model.resultType;

import lombok.Data;

import java.util.Date;

@Data
public class OrderInfo {
    /**
     * 订单名或订单描述
     */
    private String orderStr;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 供应商
     */
    private String sellerId;

    /**
     * 采购员
     */
    private String buyerId;

    /**
     * 状态
     * 0    采购员选购中
     * 1    采购员确认订单
     * 2    供应商确认
     * 3    供应商驳回
     * 4    供应商备货中
     * 5    供应商已发货
     * 6    采购员确认收到
     * 7    采购员拒绝签收
     */
    private int orderStatus;

    /**
     * 是否是第一次递交的订单
     * 0    是
     * 1    否
     */
    private int firstTime;

    /**
     * 订单确认时间
     */
    private Date orderConfirmTime;

    /**
     * 订单寄出时间
     */
    private Date orderDeliverTime;

    /**
     * 订单完结时间
     */
    private Date orderFinishTime;
}
