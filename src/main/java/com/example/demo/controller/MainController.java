package com.example.demo.controller;

import com.example.demo.model.Constant.Switcher;
import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.DTO.SidePanelStatusDTO;
import com.example.demo.model.Model.User;
import com.example.demo.service.Upper.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @Autowired
    MainService mainService;

    @RequestMapping("/main/home/{userId}")
    public String home(@PathVariable("userId") String userId, Model model){
        SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
        sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.HOME_ID);
        sidePanelStatusDTO.setUserId(userId);

        ResultDTO<User> userResult = mainService.mainPage(userId);
        if(userResult.getCode() < 0){
            return "error/404";
        }
        model.addAttribute("sidePanel", sidePanelStatusDTO);
        model.addAttribute("user",userResult.getData());

        return "/main/home";
    }

    @RequestMapping("/help/{userId}")
    public String help(@PathVariable("userId") String userId, Model model){
        SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
        sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.HELP_ID);
        sidePanelStatusDTO.setUserId(userId);

        ResultDTO<User> userResult = mainService.mainPage(userId);
        if(userResult.getCode() < 0){
            return "error/404";
        }
        model.addAttribute("sidePanel", sidePanelStatusDTO);
        model.addAttribute("user",userResult.getData());

        return "/main/help";
    }
}
