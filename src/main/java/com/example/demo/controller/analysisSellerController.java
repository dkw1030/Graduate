package com.example.demo.controller;

import com.example.demo.mapper.AccountMapper;
import com.example.demo.model.Constant.Switcher;
import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.DTO.SidePanelStatusDTO;
import com.example.demo.model.Model.User;
import com.example.demo.service.Upper.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class analysisSellerController {

    @Autowired
    AccountService accountService;

    @RequestMapping("/analysisSeller/analysisSeller/{userId}")
    public String infoSeller(@PathVariable("userId")String userId,
                             Model model){
        SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
        sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.ANALYSIS_SELLER_ID);
        sidePanelStatusDTO.setUserId(userId);

        ResultDTO<User> resultDTO = accountService.getUserById(userId);
        if(resultDTO.getCode() < 0){
            return "error/404";
        }
        model.addAttribute("user", resultDTO.getData());
        model.addAttribute("sidePanel", sidePanelStatusDTO);
        return "analysis/analysisSeller";
    }
}
