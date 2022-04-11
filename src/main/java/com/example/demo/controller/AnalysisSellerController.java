package com.example.demo.controller;

import com.example.demo.model.Constant.Switcher;
import com.example.demo.model.DTO.CompanyDetailDTO;
import com.example.demo.model.DTO.PageDTO;
import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.DTO.SellerListDTO;
import com.example.demo.model.DTO.SidePanelStatusDTO;
import com.example.demo.model.Model.CompanyOverView;
import com.example.demo.model.Model.User;
import com.example.demo.model.Model.resultType.OrderPercentage;
import com.example.demo.service.Upper.AccountService;
import com.example.demo.service.Upper.AnalysisService;
import com.example.demo.service.Upper.InfoSellerService;
import com.example.demo.utils.PageControlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.example.demo.utils.PageControlUtil.fromIndex;
import static com.example.demo.utils.PageControlUtil.toIndex;

@Controller
public class AnalysisSellerController {

    @Autowired
    AccountService accountService;

    @Autowired
    InfoSellerService infoSellerService;

    @Autowired
    AnalysisService analysisService;

    @RequestMapping("/analysisSeller/analysisSeller/{userId}")
    public String infoSeller(@PathVariable("userId")String userId,
                             Model model){
        SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.ANALYSIS_SELLER_ID);
        sidePanelStatusDTO.setUserId(userId);

        ResultDTO<User> resultDTO = accountService.getUserById(userId);
        if(resultDTO.getCode() < 0){
            return "error/404";
        }
        model.addAttribute("user", resultDTO.getData());
        model.addAttribute("sidePanel", sidePanelStatusDTO);
        if(resultDTO.getData().getUserRole()==0){
            sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
            ResultDTO<OrderPercentage> resultDTOBuyer = analysisService.analysisBuyer(userId);
            model.addAttribute("data", resultDTOBuyer.getData());
            return "analysis/analysisBuyer";
        }else{
            String companyId = resultDTO.getData().getCompanyId();
            sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.SellerSidePanel);
            ResultDTO<CompanyDetailDTO> resultDTOSeller = analysisService.analysisSeller(companyId);
            model.addAttribute("data", resultDTOSeller.getData().getOrderPercentage());
            model.addAttribute("company", resultDTOSeller.getData().getCompanyDetail());
            return "analysis/analysisSeller";
        }
    }


    @RequestMapping("/infoSeller/sellerDetail/{userId}/{companyId}")
    public String sellerDetail( @PathVariable("userId") String userId,
                                @PathVariable("companyId") String companyId,
                                Model model){

        ResultDTO<User> resultDTO = accountService.getUserById(userId);
        if(resultDTO.getCode() < 0){
            return "error/404";
        }
        model.addAttribute("user", resultDTO.getData());
        ResultDTO<CompanyDetailDTO> resultDTOSeller = analysisService.analysisSeller(companyId);
        model.addAttribute("data", resultDTOSeller.getData().getOrderPercentage());
        model.addAttribute("company", resultDTOSeller.getData().getCompanyDetail());

        return "infoSeller/sellerDetail";
    }
}
