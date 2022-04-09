package com.example.demo.controller;

import com.example.demo.model.Constant.Switcher;
import com.example.demo.model.DTO.FrameworkDTO;
import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.DTO.SidePanelStatusDTO;
import com.example.demo.service.Upper.InfoSellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrameworkController {

    @Autowired
    InfoSellerService infoSellerService;

    @RequestMapping("/demo")
    public String demo(){
        return "framework/demo";
    }

    @RequestMapping("/framework/framework/{userId}")
    public String framework(@PathVariable("userId")String userId,
                            Model model){

        ResultDTO<FrameworkDTO> companyDetail = infoSellerService.getCompanyDetail(userId);

        if(companyDetail.getCode()<0){
            return "error/404";
        }
        SidePanelStatusDTO sidePanelStatusDTO= new SidePanelStatusDTO();
        sidePanelStatusDTO.setUserId(userId);
        sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.SellerSidePanel);
        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.FRAMEWORK_ID);

        model.addAttribute("sidePanel", sidePanelStatusDTO);
        model.addAttribute("user", companyDetail.getData().getUser());
        model.addAttribute("company", companyDetail.getData().getCompanyDetail());

        return "framework/framework";
    }
}
