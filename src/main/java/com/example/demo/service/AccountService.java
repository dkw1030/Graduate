package com.example.demo.service;

import com.example.demo.mapper.AccountMapper;
import com.example.demo.model.Constant.Switcher;
import com.example.demo.model.DTO.ResultDTO;
import com.example.demo.model.DTO.CheckAccountDTO;
import com.example.demo.model.DTO.SidePanelStatusDTO;
import com.example.demo.model.Enum.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountMapper accountMapper;

    public ResultDTO<CheckAccountDTO> login(int id, String password){
        ResultDTO<CheckAccountDTO> resultDTO= new ResultDTO<>();
        CheckAccountDTO checkAccountDTO = new CheckAccountDTO();
        //设置
        SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
        sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.HOME_ID);
        resultDTO.setSidePanelStatusDTO(sidePanelStatusDTO);
        String actrualPassword;
        //首先进行账号验证
        actrualPassword = accountMapper.getPasswordById(id);
        resultDTO.setCode(1);
        //不存在id时结果为null
        if(actrualPassword == null){
            checkAccountDTO.setResult("账号不存在");
        }else if (!actrualPassword.equals(password)){
            checkAccountDTO.setResult("账号或密码不正确");
        }else{
            checkAccountDTO.setResult("登录成功");
            resultDTO.setCode(0);
        }
        return resultDTO;
    }

}

