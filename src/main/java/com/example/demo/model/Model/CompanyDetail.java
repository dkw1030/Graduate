package com.example.demo.model.Model;

import com.example.demo.model.Model.resultType.CompanyInfo;
import lombok.Data;

import java.util.List;

@Data
public class CompanyDetail{

    private CompanyInfo companyInfo;

    private User boss;

    private List<Department> departments;

    private List<User> allUsers;
}
