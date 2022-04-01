package com.example.demo.service.Upper;

import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.Model.User;
import com.example.demo.service.lower.AccountBasicService;
import com.example.demo.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainService {

    @Autowired
    AccountBasicService accountBasicService;

    public ResultDTO<User> mainPage(String userId){
        ResultDTO<User> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        User user = null;
        try{
            ResultDTO<User> userResult = accountBasicService.getUserById(userId);
            user = userResult.getData();
        }catch (Exception e){
            LogUtil.errorLog(e, getClass().getName());
        }
        resultDTO.setCode(0);
        resultDTO.setData(user);
        return resultDTO;
    }

}
