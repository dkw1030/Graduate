package com.example.demo.model.Model.resultType;

import lombok.Data;

@Data
public class CompanyInfo {
    /**
     * 公司id
     */
    private String companyId;

    /**
     * 公司名
     */
    private String companyName;

    /**
     * 公司描述
     */
    private String companyDescription;

    /**
     * 订单确认数
     */
    private int orderConfirmed;

    /**
     * 订单拒绝数
     */
    private int orderRejected;

    /**
     * 订单完成数
     */
    private int orderCompleted;

    /**
     * 订单驳回数
     */
    private int orderFailed;

    private int valid;
}
