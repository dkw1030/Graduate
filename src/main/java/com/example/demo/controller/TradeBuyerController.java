package com.example.demo.controller;

import com.example.demo.model.Constant.Switcher;
import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.DTO.SidePanelStatusDTO;
import com.example.demo.model.DTO.TradeDTO;
import com.example.demo.model.Model.User;
import com.example.demo.service.Upper.AccountService;
import com.example.demo.service.Upper.TradeBuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Controller
public class TradeBuyerController {
    @Autowired
    TradeBuyerService tradeBuyerService;

    @Autowired
    AccountService accountService;

    @RequestMapping("/orderList/{userId}")
    public String orderList(@PathVariable("userId")String userId, Model model){
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
        return "tradeBuyer/orderList";
    }

    @RequestMapping("/orderListSearch/{userId}")
    public String orderListSearch(@PathVariable("userId")String userId,
                                  @RequestParam("orderId") String orderId,
                                  @RequestParam("itemName") String itemName,
                                  @RequestParam("id") String Id,
                                  @RequestParam("type") String type,
                                  @RequestParam("startTime") String startTime,
                                  @RequestParam("endTime") String endTime,
                                  Model model){
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
        return "tradeBuyer/orderList";
    }

    @RequestMapping("/orderDetail/{userId}/{orderId}")
    public String loginLayout(@PathVariable("userId")String userId,
                              @PathVariable("orderId")String orderId,
                              Model model){
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
        return "tradeBuyer/orderDetail";
    }

    @RequestMapping("/uploadOrder/{userId}")
    public String uploadOrder(@PathVariable("userId")String userId, Model model){
        SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
        sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.TRADE_BUYER_ID);
        sidePanelStatusDTO.setCurSubMenu(Switcher.MenuSwitcher.TRADE_BUYER_INPUT_ORDER_ID);
        sidePanelStatusDTO.setUserId(userId);

        ResultDTO<User> resultDTO = tradeBuyerService.UploadOrderPage(userId);
        if(resultDTO.getCode() < 0){
            return "error/404";
        }
        model.addAttribute("user", resultDTO.getData());
        model.addAttribute("sidePanel", sidePanelStatusDTO);
        return "tradeBuyer/uploadOrder";
    }

    @RequestMapping("/uploadOrderAction/{userId}")
    public String inputSeller(@RequestParam("file") MultipartFile multipartFile, @PathVariable("userId") String userId, Model model){
        SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
        sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.TRADE_BUYER_ID);
        sidePanelStatusDTO.setCurSubMenu(Switcher.MenuSwitcher.TRADE_BUYER_INPUT_ORDER_ID);
        sidePanelStatusDTO.setUserId(userId);

        model.addAttribute("sidePanel", sidePanelStatusDTO);
        ResultDTO<String> resultDTO = tradeBuyerService.uploadOrderInfo(multipartFile);
        if(resultDTO.getCode() < 0){
            return "error/404";
        }
        ResultDTO<User> userResultDTO = accountService.getUserById(userId);
        if(userResultDTO.getCode() < 0){
            return "error/404";
        }

        model.addAttribute("user", userResultDTO.getData());
        model.addAttribute("msg", resultDTO.getData());
        return "tradeBuyer/uploadOrder";
    }
}
