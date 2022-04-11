package com.example.demo.controller;

import com.example.demo.model.Constant.Switcher;
import com.example.demo.model.DTO.FrameworkDTO;
import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.DTO.SidePanelStatusDTO;
import com.example.demo.model.Model.CompanyDetail;
import com.example.demo.model.Model.User;
import com.example.demo.service.Upper.AccountService;
import com.example.demo.service.Upper.InfoSellerService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FrameworkController {

    @Autowired
    InfoSellerService infoSellerService;

    @Autowired
    AccountService accountService;

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

    @RequestMapping("/addUser/{companyId}/{userId}")
    public String companyInfo(@PathVariable("userId")String userId,
                       @PathVariable("companyId")String companyId,
                       Model model){

        ResultDTO<User> userResult = accountService.getUserById(userId);

        ResultDTO<CompanyDetail> companyResult = infoSellerService.getCompanyDetailByCompanyId(companyId);


        model.addAttribute("user", userResult.getData());
        model.addAttribute("company", companyResult.getData());
        return "infoSeller/companyDetail";
    }

    @RequestMapping("/changeDep")
    public String changeDep(@RequestParam("userId")String userId,
                            @RequestParam("peopleId")String peopleId,
                            @RequestParam("companyId")String companyId,
                            @RequestParam("dep")String dep,
                            Model model){

        ResultDTO<User> userResult = accountService.getUserById(userId);

        ResultDTO<String> resultDTO = accountService.changeDep(companyId, dep, peopleId);
        if(resultDTO.getCode()<0){
            return "error/404";
        }

        ResultDTO<CompanyDetail> companyResult = infoSellerService.getCompanyDetailByCompanyId(companyId);


        model.addAttribute("user", userResult.getData());
        model.addAttribute("company", companyResult.getData());
        return "infoSeller/companyDetail";
    }

    @RequestMapping("/changeMonitor")
    public String changeMonitor(@RequestParam("userId")String userId,
                            @RequestParam("peopleId")String peopleId,
                            @RequestParam("companyId")String companyId,
                            @RequestParam("dep")String dep,
                            Model model){

        ResultDTO<User> userResult = accountService.getUserById(userId);

        ResultDTO<String> resultDTO = accountService.changeMonitor(companyId, dep, peopleId);
        if(resultDTO.getCode()<0){
            return "error/404";
        }

        ResultDTO<CompanyDetail> companyResult = infoSellerService.getCompanyDetailByCompanyId(companyId);


        model.addAttribute("user", userResult.getData());
        model.addAttribute("company", companyResult.getData());
        return "infoSeller/companyDetail";
    }

    @RequestMapping("/addDep")
    public String addDep(@RequestParam("userId") String userId,
            @RequestParam("companyId") String companyId,
            @RequestParam("departmentName") String departmentName,
            Model model){
        ResultDTO<String> userResult = infoSellerService.addDep(companyId, departmentName);
        int code = userResult.getCode();
        if(code<0){
            return "error/404";
        }
        ResultDTO<User> userResultDTO = accountService.getUserById(userId);
        ResultDTO<CompanyDetail> companyResult = infoSellerService.getCompanyDetailByCompanyId(companyId);
        model.addAttribute("user", userResultDTO.getData());
        model.addAttribute("company", companyResult.getData());

        return "redirect:/addUser/"+companyId+"/"+userId;
    }


    @RequestMapping("/deleteDep")
    public String deleteDep(@RequestParam("userId") String userId,
                         @RequestParam("companyId") String companyId,
                         @RequestParam("departmentId") String departmentId,
                         Model model){
        ResultDTO<String> userResult = infoSellerService.deleteDep(companyId, departmentId);
        int code = userResult.getCode();
        if(code<0){
            return "error/404";
        }
        ResultDTO<User> userResultDTO = accountService.getUserById(userId);
        ResultDTO<CompanyDetail> companyResult = infoSellerService.getCompanyDetailByCompanyId(companyId);
        model.addAttribute("user", userResultDTO.getData());
        model.addAttribute("company", companyResult.getData());

        return "redirect:/addUser/"+companyId+"/"+userId;
    }
}
