package com.example.demo.service.lower;

import com.example.demo.mapper.InfoSellerMapper;
import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.Model.User;
import com.example.demo.model.Model.resultType.CompanyInfo;
import com.example.demo.model.Model.resultType.DepartmentInfo;
import com.example.demo.utils.LogUtil;
import com.example.demo.utils.TimeUtil;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InfoSellerBasicService {
    @Autowired
    InfoSellerMapper infoSellerMapper;

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

        int companyResult = infoSellerMapper.insertCompany(companyInfo);
        if(companyResult != 1){
            resultDTO.setData("company insert error");
            return resultDTO;
        }

        List<DepartmentInfo> departmentInfos = new ArrayList<>();
        List<User> users = new ArrayList<>();
        for (int i = 5; i < data.size(); i++) {
            List<String> row = data.get(i);
            User user = new User();
            user.setCompanyId(companyInfo.getCompanyId());
            user.setUserName(row.get(0));
            user.setUserId(companyInfo.getCompanyId() + (i - 5));
            if(row.get(2).equals("老板")){
                user.setRole(1);
                users.add(user);
                continue;
            }

            DepartmentInfo departmentInfo = new DepartmentInfo();
            departmentInfo.setDepartmentId(companyInfo.getCompanyId() + (i - 5));
            departmentInfo.setCompanyId(companyInfo.getCompanyId());
            departmentInfo.setDepartmentName(row.get(3));
            departmentInfos.add(departmentInfo);

            user.setDepartmentId(departmentInfo.getDepartmentId());
            if(row.get(2).equals("主管")){
                user.setRole(2);
            }else{
                user.setRole(3);
            }
            users.add(user);
        }
//        int departmentResult = infoSellerMapper.insertDepartment(departmentInfos);
//        if(departmentResult != 1){
//            resultDTO.setData("department insert error");
//            return resultDTO;
//        }
//        int userResult = infoSellerMapper.insertUser(users);
//        if(userResult != 1){
//            resultDTO.setData("company insert error");
//            return resultDTO;
//        }
        resultDTO.setData("success");
        resultDTO.setCode(0);
        return resultDTO;
    }
}
