package com.example.demo.controller;

import com.example.demo.model.Constant.Switcher;
import com.example.demo.model.DTO.ResultDTO;
import com.example.demo.model.DTO.CheckAccountDTO;
import com.example.demo.model.DTO.SidePanelStatusDTO;
import com.example.demo.service.lower.AccountService;
import com.example.demo.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("")
    public String loginLayout(Model model){
        return "account/login";
    }

    @RequestMapping("/login")
    public String login(@RequestParam("userId") int id,
                        @RequestParam("password") String password,
                        Model model){
        int code = -1;
        try{
            //查询是否存在用户以及密码是否正确
            ResultDTO<CheckAccountDTO> resultDTO = accountService.login(id, password);
            code = resultDTO.getCode();
            if(code == 1){
                model.addAttribute("errorText", resultDTO.getData().getResult());
                return "account/login";
            }else if(code == 0){
                //查询用户信息

                SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
                sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
                sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.HOME_ID);

                model.addAttribute("sidePanel", sidePanelStatusDTO);
                return "main/home";
            }else{
                throw new Exception();
            }
        }catch(Exception e){
            LogUtil.errorLog(e,"login controller");
            return "error/404";
        }
    }
}
