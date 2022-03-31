package com.example.demo.service.lower;


import com.example.demo.mapper.AccountMapper;
import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.Model.User;
import com.example.demo.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountBasicService {
    @Autowired
    private AccountMapper accountMapper;

    public ResultDTO<String> login(long id, String password) throws Exception{
        ResultDTO<String> resultDTO= new ResultDTO<>();
        String actrualPassword;
        //首先进行账号验证
        actrualPassword = accountMapper.getPasswordById(id+"");
        if(actrualPassword == null){
            resultDTO.setCode(1);
            resultDTO.setData("账号不存在");
        }else if (!actrualPassword.equals(password)){
            resultDTO.setCode(2);
            resultDTO.setData("账号或密码不正确");
        }else{
            resultDTO.setCode(0);
            resultDTO.setData("登录成功");
        }
        return resultDTO;
    }

    public ResultDTO<User> getUser(String id) throws Exception{
        ResultDTO<User> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        User user = accountMapper.getUserById(id);
        resultDTO.setData(user);
        resultDTO.setCode(0);
        return resultDTO;
    }

}

