package com.example.demo.controller;

import com.example.demo.model.Constant.Switcher;
import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.DTO.SidePanelStatusDTO;
import com.example.demo.model.DTO.TradeDTO;
import com.example.demo.service.Upper.TradeBuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class tradeBuyerController {
    @Autowired
    TradeBuyerService tradeBuyerService;

    @RequestMapping("/tradeBuyer/{userId}")
    public String loginLayout(@PathVariable("userId")String userId, Model model){
        SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
        sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.TRADE_BUYER_ID);
        sidePanelStatusDTO.setCurSubMenu(Switcher.MenuSwitcher.TRADE_BUYER_ORDER_DETAIL_ID);
        sidePanelStatusDTO.setUserId(userId);

        ResultDTO<TradeDTO> resultDTO = tradeBuyerService.TradeBuyerPage(userId);
        if(resultDTO.getCode() < 0){
            return "error/404";
        }
        model.addAttribute("user", resultDTO.getData().getUser());
        model.addAttribute("sidePanel", sidePanelStatusDTO);
        model.addAttribute("orders", resultDTO.getData().getOrders());
        return "tradeBuyer/tradeBuyer";
    }
}
