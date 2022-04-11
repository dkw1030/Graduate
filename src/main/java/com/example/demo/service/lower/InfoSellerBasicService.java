package com.example.demo.service.lower;

import com.example.demo.mapper.AccountMapper;
import com.example.demo.mapper.InfoSellerMapper;
import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.Model.CompanyDetail;
import com.example.demo.model.Model.CompanyOverView;
import com.example.demo.model.Model.Department;
import com.example.demo.model.Model.User;
import com.example.demo.model.Model.resultType.CompanyInfo;
import com.example.demo.model.Model.resultType.DepartmentInfo;
import com.example.demo.utils.FileUtil;
import com.example.demo.utils.LogUtil;
import com.example.demo.utils.TimeUtil;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class InfoSellerBasicService {

    @Autowired
    InfoSellerMapper infoSellerMapper;

    @Autowired
    AccountMapper accountMapper;

    public ResultDTO<String> uploadSeller(List<List<String>> data) throws Exception{
        ResultDTO<String> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);

        CompanyInfo companyInfo = new CompanyInfo();
        companyInfo.setCompanyId(TimeUtil.getTimeStamp());
        companyInfo.setCompanyName(data.get(1).get(1));
        companyInfo.setCompanyDescription(data.get(1).get(2));
        companyInfo.setOrderConfirmed(Integer.parseInt(data.get(3).get(0)));
        companyInfo.setOrderRejected(Integer.parseInt(data.get(3).get(1)));
        companyInfo.setOrderCompleted(Integer.parseInt(data.get(3).get(2)));
        companyInfo.setOrderFailed(Integer.parseInt(data.get(3).get(3)));
        List<DepartmentInfo> departmentInfos = new ArrayList<>();
        List<User> users = new ArrayList<>();
        String lastDepartmentName = "";
        String lastDepartmentId = "";
        for (int i = 5; i < data.size(); i++) {
            List<String> row = data.get(i);
            User user = new User();
            user.setCompanyId(companyInfo.getCompanyId());
            user.setUserName(row.get(0));
            user.setUserId(companyInfo.getCompanyId() + (i - 5));
            if(row.get(2).equals("老板")){
                user.setDepartmentId("0");
                user.setUserRole(1);
                users.add(user);
                continue;
            }
            if(!row.get(3).equals(lastDepartmentName)){
                DepartmentInfo departmentInfo = new DepartmentInfo();
                lastDepartmentName = row.get(3);
                lastDepartmentId = companyInfo.getCompanyId() + (i - 5);
                departmentInfo.setDepartmentId(lastDepartmentId);
                departmentInfo.setCompanyId(companyInfo.getCompanyId());
                departmentInfo.setDepartmentName(row.get(3));
                departmentInfos.add(departmentInfo);
            }
            user.setDepartmentId(lastDepartmentId);
            if(row.get(2).equals("主管")){
                user.setUserRole(2);
            }else{
                user.setUserRole(3);
            }
            users.add(user);
        }
        int companyResult = infoSellerMapper.insertCompany(companyInfo);
        LogUtil.log(getClass().getName(), "company insert finish\n" + companyResult);
        int departmentResult = infoSellerMapper.insertDepartment(departmentInfos);
        LogUtil.log(getClass().getName(), "department insert finish\n" + departmentResult);
        int userResult = infoSellerMapper.insertUserBasic(users);
        LogUtil.log(getClass().getName(), "user insert finish\n" + userResult);
        int accountResult = infoSellerMapper.insertAccount(users);
        LogUtil.log(getClass().getName(), "account insert finish\n" + accountResult);
        resultDTO.setData("success");
        resultDTO.setCode(0);
        return resultDTO;
    }

    public ResultDTO<List<CompanyOverView>> getAllCompany() throws Exception{
        ResultDTO<List<CompanyOverView>> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        List<CompanyOverView> companyOverViewList = infoSellerMapper.getAllCompanyOverViewList();
//        List<CompanyOverView> companyOverViewList = Arrays.asList();

        resultDTO.setData(companyOverViewList);
        resultDTO.setCode(0);
        return resultDTO;

    }

    public ResultDTO<List<CompanyOverView>> getCompany(String companyId, String companyName) throws Exception{
        ResultDTO<List<CompanyOverView>> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        List<CompanyOverView> companyOverViewList = infoSellerMapper.getCompanyOverViewList(companyId, companyName);
//        List<CompanyOverView> companyOverViewList = Arrays.asList();

        resultDTO.setData(companyOverViewList);
        resultDTO.setCode(0);
        return resultDTO;

    }

    public ResultDTO<String> addCount(int type,String orderId) throws Exception{
        ResultDTO<String> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        switch (type){
            case 1:
                infoSellerMapper.addConfirmed(orderId);
                break;
            case 2:
                infoSellerMapper.addRejected(orderId);
                break;
            case 3:
                infoSellerMapper.addCompleted(orderId);
                break;
            case 4:
                infoSellerMapper.addFailed(orderId);
        }
        resultDTO.setCode(0);
        return resultDTO;
    }

    public ResultDTO<CompanyDetail> getCompanyDetail(String companyId) throws Exception{
        ResultDTO<CompanyDetail> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        if(companyId == null){
            return resultDTO;
        }
        CompanyInfo companyInfo = infoSellerMapper.getCompanyById(companyId);

        List<DepartmentInfo> departmentInfos = infoSellerMapper.getDepartmentById(companyId);

        List<User> employees = accountMapper.getEmployeeByCompanyId(companyId);

        //组装companyDetail
        CompanyDetail companyDetail = new CompanyDetail();
        companyDetail.setCompanyInfo(companyInfo);
        List<Department> departments = new ArrayList<>();
        for (DepartmentInfo dep :
                departmentInfos) {
            Department department = new Department();
            department.setDepartmentId(dep.getDepartmentId());
            department.setCompanyId(companyId);
            department.setDepartmentName(dep.getDepartmentName());
            List<User> college = new ArrayList<>();
            for (User user :
                    employees) {
                if(user.getUserRole()==1){
                    companyDetail.setBoss(user);
                    user.setDepartmentName(department.getDepartmentName());
                }else if(user.getDepartmentId().equals(dep.getDepartmentId())){
                    if(user.getUserRole() == 2){
                        department.setMonitor(user);
                    }else{
                        college.add(user);
                    }
                    user.setDepartmentName(department.getDepartmentName());
                }
                department.setUsers(college);
            }
            departments.add(department);
        }
        companyDetail.setDepartments(departments);
        companyDetail.setAllUsers(employees);
        resultDTO.setCode(0);
        resultDTO.setData(companyDetail);
        return resultDTO;
    }

    public ResultDTO<CompanyInfo> getCompanyInfoById(String companyId) throws Exception{
        ResultDTO<CompanyInfo> resultDTO = new ResultDTO<>();
        CompanyInfo companyInfo = infoSellerMapper.getCompanyById(companyId);
        resultDTO.setCode(0);
        resultDTO.setData(companyInfo);
        return resultDTO;
    }

    public ResultDTO<DepartmentInfo> getDepartmentByDepartmentId(String departmentId) throws Exception{
        ResultDTO<DepartmentInfo> resultDTO = new ResultDTO<>();
        DepartmentInfo departmentInfo = infoSellerMapper.getDepartmentByDepartmentId(departmentId);
        resultDTO.setCode(0);
        resultDTO.setData(departmentInfo);
        return resultDTO;
    }

    public ResultDTO<String> addDep(DepartmentInfo departmentInfo){
        ResultDTO<String> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        List<DepartmentInfo> departmentInfos = Arrays.asList(departmentInfo);
        infoSellerMapper.insertDepartment(departmentInfos);
        resultDTO.setCode(0);
        return resultDTO;
    }


    public ResultDTO<String> deleteDep(DepartmentInfo departmentInfo){
        ResultDTO<String> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        accountMapper.changeMonitorToEmployee(departmentInfo.getCompanyId(), departmentInfo.getDepartmentId());
        infoSellerMapper.deleteDepartment(departmentInfo);
        resultDTO.setCode(0);
        return resultDTO;
    }

    public ResultDTO<String> validCompany(String companyId,int type) throws Exception{
        ResultDTO<String> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        if(type == 0){
            infoSellerMapper.deleteCompany(companyId);
        }else {
            infoSellerMapper.validCompany(companyId);
        }
        resultDTO.setCode(0);
        return resultDTO;
    }

}
