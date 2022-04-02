package com.example.demo.controller;

import com.example.demo.model.Constant.Switcher;
import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.DTO.SellerListDTO;
import com.example.demo.model.DTO.SidePanelStatusDTO;
import com.example.demo.model.Model.User;
import com.example.demo.service.Upper.AccountService;
import com.example.demo.service.Upper.InfoSellerService;
import com.example.demo.service.lower.AccountBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class InfoSellerController {

    @Autowired
    private InfoSellerService infoSellerService;

    @Autowired
    private AccountService accountService;

    @RequestMapping("/sellerList/{userId}")
    public String infoSeller( @PathVariable("userId") String userId, Model model){

        SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
        sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.INFO_SELLER_ID);
        sidePanelStatusDTO.setCurSubMenu(Switcher.MenuSwitcher.INFO_SELLER_SELLER_DETAIL_ID);
        sidePanelStatusDTO.setUserId(userId);

        ResultDTO<SellerListDTO> pageResultDTO = infoSellerService.sellerListPage(userId);
        if(pageResultDTO.getCode() < 0){
            return "error/404";
        }

        model.addAttribute("user", pageResultDTO.getData().getUser());
        model.addAttribute("companyOverView", pageResultDTO.getData().getCompanyOverViewList());
        model.addAttribute("sidePanel", sidePanelStatusDTO);

        return "infoSeller/sellerList";
    }

    @RequestMapping("/upload/{userId}")
    public String loginLayout( @PathVariable("userId") String userId, Model model){
//        model.addAttribute("fileName", "Order.xlsx");

        SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
        sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.INFO_SELLER_ID);
        sidePanelStatusDTO.setCurSubMenu(Switcher.MenuSwitcher.INFO_SELLER_UPLOAD_SELLER_ID);
        sidePanelStatusDTO.setUserId(userId);
        ResultDTO<User> userResultDTO = accountService.getUserById(userId);
        if(userResultDTO.getCode() < 0){
            return "error/404";
        }

        model.addAttribute("user", userResultDTO.getData());

        model.addAttribute("sidePanel", sidePanelStatusDTO);

        return "infoSeller/uploadSeller";
    }

    @RequestMapping("/uploadSeller/{userId}")
    public String inputSeller(@RequestParam("file") MultipartFile multipartFile, @PathVariable("userId") String userId, Model model){
        SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
        sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.INFO_SELLER_ID);
        sidePanelStatusDTO.setCurSubMenu(Switcher.MenuSwitcher.INFO_SELLER_UPLOAD_SELLER_ID);
        sidePanelStatusDTO.setUserId(userId);

        model.addAttribute("sidePanel", sidePanelStatusDTO);
        ResultDTO<String> resultDTO = infoSellerService.uploadSellerInfo(multipartFile);
        if(resultDTO.getCode() < 0){
            return "error/404";
        }
        ResultDTO<User> userResultDTO = accountService.getUserById(userId);
        if(userResultDTO.getCode() < 0){
            return "error/404";
        }

        model.addAttribute("user", userResultDTO.getData());
        model.addAttribute("msg", resultDTO.getData());
        return "infoSeller/uploadSeller";
    }

    @RequestMapping("/sellerListSearch/{userId}")
    public String sellerListSearch(@PathVariable("userId") String userId,
                                   @RequestParam("id") String id,
                                   @RequestParam("name") String name,
                                   Model model){
        SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
        sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.INFO_SELLER_ID);
        sidePanelStatusDTO.setCurSubMenu(Switcher.MenuSwitcher.INFO_SELLER_SELLER_DETAIL_ID);
        sidePanelStatusDTO.setUserId(userId);

        ResultDTO<SellerListDTO> pageResultDTO = infoSellerService.searchSellerListPage(userId, id, name);
        if(pageResultDTO.getCode() < 0){
            return "error/404";
        }

        model.addAttribute("user", pageResultDTO.getData().getUser());
        model.addAttribute("companyOverView", pageResultDTO.getData().getCompanyOverViewList());
        model.addAttribute("sidePanel", sidePanelStatusDTO);

        return "infoSeller/sellerList";
    }
}
