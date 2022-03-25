package com.example.demo.model.Model.resultType;

import lombok.Data;

@Data
public class DepartmentInfo {
    /**
     * 公司id
     */
    private int companyId;

    /**
     * 部门id
     */
    private int departmentId;

    /**
     * 部门名
     */
    private String departmentName;
}
