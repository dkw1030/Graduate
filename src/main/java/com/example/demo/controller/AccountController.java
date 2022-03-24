package com.example.demo.controller;

import com.example.demo.model.Constant.Switcher;
import com.example.demo.model.DTO.ResultDTO;
import com.example.demo.model.DTO.CheckAccountDTO;
import com.example.demo.model.DTO.SidePanelStatusDTO;
import com.example.demo.service.AccountService;
import com.example.demo.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;

@Controller
public class AccountController {

    @Autowired
    DataSource dataSource;

    @Autowired
    private AccountService accountService;

    @RequestMapping("/user/login")
    public String login(@RequestParam("userId") int id,
                        @RequestParam("password") String password,
                        Model model){
        int code = -1;
        try{
            ResultDTO<CheckAccountDTO> resultDTO = accountService.login(id, password);
            //设置
            SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
            sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
            sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.TRADE_BUYER_ID);
            sidePanelStatusDTO.setCurSubMenu(Switcher.MenuSwitcher.TEST0);

            model.addAttribute("data", resultDTO.getData());
            model.addAttribute("sidePanel", sidePanelStatusDTO);
//            model.addAttribute("test","sidepanel");
            code = resultDTO.getCode();
            if(code == 0){
                return "index";
            }else if(code == 1){
                return "account/login";
            }else{
                throw new Exception();
            }
        }catch(Exception e){
            LogUtil.errorLog(e, code);
            return "error/404";
        }
    }
}
