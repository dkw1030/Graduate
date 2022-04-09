package com.example.demo.service.Upper;

import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.Model.User;
import com.example.demo.model.Model.resultType.CompanyInfo;
import com.example.demo.model.Model.resultType.DepartmentInfo;
import com.example.demo.service.lower.AccountBasicService;
import com.example.demo.service.lower.InfoSellerBasicService;
import com.example.demo.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountBasicService accountBasicService;

    @Autowired
    InfoSellerBasicService infoSellerBasicService;

    public ResultDTO<User> login(long id, String password){
        ResultDTO<User> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        try {
            ResultDTO<String> accountResult = accountBasicService.login(id, password);
            resultDTO.setCode(accountResult.getCode());
            if(resultDTO.getCode() > 0){
                return resultDTO;
            }
            resultDTO = accountBasicService.getUserById(id+"");
        }catch (Exception e){
            LogUtil.errorLog(e, getClass().getName());
        }
        return resultDTO;
    }

    public ResultDTO<User> getUserById(String userId){
        ResultDTO<User> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        try{
            resultDTO = accountBasicService.getUserById(userId);
        }catch (Exception e){
            LogUtil.errorLog(e, getClass().getName());
        }
        return resultDTO;
    }

    public ResultDTO<User> getUserInfo(String userId){
        ResultDTO<User> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        try {
            resultDTO = accountBasicService.getUserById(userId);
            ResultDTO<CompanyInfo> companyInfo = infoSellerBasicService.getCompanyInfoById(resultDTO.getData().getCompanyId());
            ResultDTO<DepartmentInfo> departmentInfo = infoSellerBasicService.getDepartmentByDepartmentId(resultDTO.getData().getDepartmentId());
            resultDTO.getData().setCompanyId(companyInfo.getData().getCompanyName());
            resultDTO.getData().setDepartmentId(departmentInfo.getData().getDepartmentName());
        }catch (Exception e){
            LogUtil.errorLog(e, getClass().getName());
        }

        return resultDTO;
    }

    public ResultDTO<String> change(String userId, String email, int phone){
        ResultDTO<String> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        try{
            resultDTO = accountBasicService.change(userId, email, phone);
        }catch (Exception e){
            LogUtil.errorLog(e, getClass().getName());
        }
        return resultDTO;
    }

}

