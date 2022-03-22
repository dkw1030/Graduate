package com.example.demo.service;

import com.example.demo.mapper.AccountMapper;
import com.example.demo.model.DTO.CheckAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountMapper accountMapper;

    public CheckAccountDTO login(int id, String password){
        CheckAccountDTO checkAccountDTO = new CheckAccountDTO();
        String actrualPassword;
        //首先进行账号验证
        actrualPassword = accountMapper.getPasswordById(id);
        checkAccountDTO.setNextPage("login");
        //不存在id时结果为null
        if(actrualPassword == null){
            checkAccountDTO.setResult("账号不存在");
        }else if (!actrualPassword.equals(password)){
            checkAccountDTO.setResult("账号或密码不正确");
        }else{
            checkAccountDTO.setResult("登录成功");
            checkAccountDTO.setNextPage("index");
        }
        return checkAccountDTO;
    }

}

