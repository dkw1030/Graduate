package com.example.demo.model.DTO;

public class OrderSearchDTO {
    private String buyerId;
    private String companyId;
    private String orderId;
    private String itemName;
    private String type;

    /**
     * 0 上传时间
     * 1 确认时间
     * 2 发货时间
     * 3 完成时间
     */
    private int witchTime;
    private String startTime;
    private String endTime;
    private int status;
}
