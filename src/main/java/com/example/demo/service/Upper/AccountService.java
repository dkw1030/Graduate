package com.example.demo.service.Upper;

import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.Model.User;
import com.example.demo.model.Model.resultType.CompanyInfo;
import com.example.demo.model.Model.resultType.DepartmentInfo;
import com.example.demo.service.lower.AccountBasicService;
import com.example.demo.service.lower.InfoSellerBasicService;
import com.example.demo.utils.LogUtil;
import com.example.demo.utils.TimeUtil;
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

    public ResultDTO<String> signUp(String name, int role, String companyId, String dep, String password){
        ResultDTO<String> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        try {
            User user = new User();
            user.setUserId(TimeUtil.getTimeStamp());
            user.setUserName(name);
            user.setUserRole(role);
            user.setCompanyId(companyId);
            user.setDepartmentId(dep);
            resultDTO = accountBasicService.signUp(user, password);
            resultDTO.setData(user.getUserId());
            resultDTO.setCode(0);
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
            if(resultDTO.getData().getUserRole()>0){
                resultDTO.getData().setCompanyId(companyInfo.getData().getCompanyName());
                resultDTO.getData().setDepartmentId(departmentInfo.getData().getDepartmentName());
            }else{
                resultDTO.getData().setCompanyId("");
                resultDTO.getData().setDepartmentId("");
            }
        }catch (Exception e){
            LogUtil.errorLog(e, getClass().getName());
        }

        return resultDTO;
    }

    public ResultDTO<String> change(User user){
        ResultDTO<String> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        try{
            resultDTO = accountBasicService.change(user);
        }catch (Exception e){
            LogUtil.errorLog(e, getClass().getName());
        }
        return resultDTO;
    }

    public ResultDTO<String> changeDep(String companyId, String dep, String peopleId){
        ResultDTO<String> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        try{
            resultDTO = accountBasicService.changeDep(companyId, dep, peopleId);
        }catch (Exception e){
            LogUtil.errorLog(e, getClass().getName());
        }
        return resultDTO;
    }

    public ResultDTO<String> changeMonitor(String companyId, String dep, String peopleId){
        ResultDTO<String> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        try{
            resultDTO = accountBasicService.changeMonitor(companyId, dep, peopleId);
        }catch (Exception e){
            LogUtil.errorLog(e, getClass().getName());
        }
        return resultDTO;
    }

}

