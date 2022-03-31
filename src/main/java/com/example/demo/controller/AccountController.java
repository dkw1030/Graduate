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
        SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.HOME_ID);

        //查询是否存在用户以及密码是否正确
        ResultDTO<String> resultDTO = accountService.login(id, password);
        int code = resultDTO.getCode();
        if(code == 1){
            model.addAttribute("errorText", resultDTO.getData());
            return "account/login";
        }else if(code == 0){
            //查询用户信息
            ResultDTO<User> userResultDTO = accountService.getUser(id+"");
            if(userResultDTO.getCode() != 0){
                return "error/404";
            }
            User user = userResultDTO.getData();
            if(user.getUserRole() != 0){
                sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.SellerSidePanel);
            }else {
                sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
            }

            model.addAttribute("sidePanel", sidePanelStatusDTO);
            model.addAttribute("user", user);
            return "main/home";
        }else{
            return "error/404";
        }
    }
}
