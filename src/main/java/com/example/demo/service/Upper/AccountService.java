package com.example.demo.service.Upper;

import com.example.demo.mapper.AccountMapper;
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
//
//    public ResultDTO<String> login(long id, String password){
//        ResultDTO<String> resultDTO= new ResultDTO<>();
//        String actrualPassword;
//        //首先进行账号验证
//        resultDTO.setCode(-1);
//        try{
//            actrualPassword = accountMapper.getPasswordById(id+"");
//            resultDTO.setCode(1);
//            if(actrualPassword == null){
//                resultDTO.setData("账号不存在");
//            }else if (!actrualPassword.equals(password)){
//                resultDTO.setData("账号或密码不正确");
//            }else{
//                resultDTO.setData("登录成功");
//                resultDTO.setCode(0);
//            }
//        }catch (Exception e){
//            LogUtil.errorLog(e, "login service", resultDTO.getCode());
//        }
//        return resultDTO;
//    }
//
//    public ResultDTO<User> getUser(String id){
//        ResultDTO<User> resultDTO = new ResultDTO<>();
//        resultDTO.setCode(-1);
//        try{
//            User user = accountMapper.getUserById(id);
//            resultDTO.setData(user);
//        }catch (Exception e){
//            return resultDTO;
//        }
//        resultDTO.setCode(0);
//        return resultDTO;
//    }
    public ResultDTO<User> login(long id, String password){
        ResultDTO<User> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        try {
            ResultDTO<String> accountResult = accountBasicService.login(id, password);
            resultDTO.setCode(accountResult.getCode());
            if(resultDTO.getCode() > 0){
                return resultDTO;
            }
            resultDTO = accountBasicService.getUser(id+"");
        }catch (Exception e){
            LogUtil.errorLog(e, getClass().getName());
        }
        return resultDTO;
    }

}

