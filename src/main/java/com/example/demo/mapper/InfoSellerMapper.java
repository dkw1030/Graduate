package com.example.demo.mapper;

import com.example.demo.model.Model.CompanyOverView;
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
    int insertUserBasic(List<User> users);
    int insertDepartment(List<DepartmentInfo> departments);
    int insertAccount(List<User> user);
    List<CompanyOverView> getAllCompanyOverViewList();
    List<CompanyOverView> getCompanyOverViewList(String companyId, String companyName);
    int addConfirmed(String orderId);
    int addRejected(String orderId);
    int addCompleted(String orderId);
    int addFailed(String orderId);
    CompanyInfo getCompanyById(String companyId);
    List<DepartmentInfo> getDepartmentById(String companyId);
    DepartmentInfo getDepartmentByDepartmentId(String departmentId);
    int deleteDepartment(DepartmentInfo departmentInfo);
    int deleteCompany(String companyId);
    int validCompany(String companyId);
}