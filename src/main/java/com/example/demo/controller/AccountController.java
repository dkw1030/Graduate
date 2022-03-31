package com.example.demo.controller;

import com.example.demo.model.Constant.Switcher;
import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.DTO.SidePanelStatusDTO;
import com.example.demo.model.Model.User;
import com.example.demo.service.Upper.AccountService;
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
    public String login(@RequestParam("userId") long id,
                        @RequestParam("password") String password,
                        Model model){
        ResultDTO<User> userResult = accountService.login(id, password);
        int code = userResult.getCode();
        if(code > 0){
            String errorText;
            if(code == 1){
                errorText = "账号不存在";
            }else if(code == 2){
                errorText = "账号或密码不正确";
            }else{
                errorText = "unknown error code = " + code;
            }
            model.addAttribute("errorText", errorText);
            return "account/login";
        } else if(code == 0){
            SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
            sidePanelStatusDTO.setCurSubMenu(Switcher.MenuSwitcher.HOME_ID);
            User user = userResult.getData();
            if(user.getUserRole() == 0){
                sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
            }else {
                sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.SellerSidePanel);
            }
            model.addAttribute("sidePanel", sidePanelStatusDTO);
            return "main/home";
        } else {
            return "error/404";
        }
    }
}
