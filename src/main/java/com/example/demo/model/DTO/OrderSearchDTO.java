package com.example.demo.model.DTO;

import lombok.Data;

import java.util.List;
@Data
public class OrderSearchDTO {
    private String buyerId;
    private String companyId;
    private String orderName;
    private String orderDescription;

    private List<String> orderIdList;
    private String orderId;
    private String itemName;
    private String type;
    private int ifSearch;

    /**
     * 0 上传时间
     * 1 确认时间
     * 2 发货时间
     * 3 完成时间
     */
    private int witchTime;
    private String startTime;
    private String endTime;
    private int status = -1;
    private int statusS = -1;
}
