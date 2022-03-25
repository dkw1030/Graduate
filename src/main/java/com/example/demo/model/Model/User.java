package com.example.demo.model.Model;

import lombok.Data;

@Data
public class User {
    /**
     * 主键，每人唯一独立标志
     */
    private int userId;

    /**
     * 角色：
     * 采购员：
     * 0    采购员
     * 供应商：
     * 1    公司老总
     * 2    部门主管
     * 3    普通员工
     */
    private int role;

    /**
     * 电话
     */
    private int phone;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 公司id，角色（0）不存在为负
     */
    private int companyId;

    /**
     * 部门id，角色（0，1）不存在为负
     */
    private int departmentId;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 性别，男1，女0
     */
    private int sex;

    /**
     * 头像链接
     */
    private String iconUrl;
}
