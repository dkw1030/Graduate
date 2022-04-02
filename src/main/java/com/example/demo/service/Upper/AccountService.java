package com.example.demo.service.Upper;

import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.Model.User;
import com.example.demo.service.lower.AccountBasicService;
import com.example.demo.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountBasicService accountBasicService;

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

}

