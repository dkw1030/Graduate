package com.example.demo.mapper;

import com.example.demo.model.DemoModel;
import com.example.demo.model.Model.User;
import com.example.demo.model.Model.resultType.CompanyInfo;
import com.example.demo.model.Model.resultType.DepartmentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InfoSellerMapper {
    int insertCompany(CompanyInfo company);
    int insertUser(List<User> users);
    int insertDepartment(List<DepartmentInfo> departments);
}