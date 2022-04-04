package com.example.demo.controller;

import com.example.demo.model.Constant.Switcher;
import com.example.demo.model.DTO.OrderSearchDTO;
import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.DTO.SidePanelStatusDTO;
import com.example.demo.model.DTO.TradeDTO;
import com.example.demo.service.Upper.DistributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class distributionController {
    @Autowired
    DistributionService distributionService;

    @RequestMapping("/distribution/waitDeliverOrderList/{userId}")
    public String waitDeliverOrderList(@PathVariable("userId")String userId, Model model){
        SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
        OrderSearchDTO orderSearchDTO = new OrderSearchDTO();


        orderSearchDTO.setStatus(4);
        ResultDTO<TradeDTO> resultDTO = distributionService.deliverOrderList(userId, orderSearchDTO);

        if(resultDTO.getData().getUser().getUserRole()==0){
            sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
        }else{
            sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.SellerSidePanel);
        }
        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.DISTRIBUTION_ID);
        sidePanelStatusDTO.setCurSubMenu(Switcher.MenuSwitcher.DISTRIBUTION_WAIT_DELIVER_ID);
        sidePanelStatusDTO.setUserId(userId);


        if(resultDTO.getCode() < 0){
            return "error/404";
        }
        model.addAttribute("user", resultDTO.getData().getUser());
        model.addAttribute("sidePanel", sidePanelStatusDTO);
        model.addAttribute("orders", resultDTO.getData().getOrders());
        return "/distribution/waitDeliverOrderList";
    }
}
