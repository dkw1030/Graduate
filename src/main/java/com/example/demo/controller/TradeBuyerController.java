package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.model.Constant.Switcher;
import com.example.demo.model.DTO.*;
import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.Model.CompanyOverView;
import com.example.demo.model.Model.User;
import com.example.demo.model.Model.resultType.OrderInfo;
import com.example.demo.service.Upper.AccountService;
import com.example.demo.service.Upper.OrderService;
import com.example.demo.service.Upper.TradeBuyerService;
import com.example.demo.utils.JsonListUtil;
import com.example.demo.utils.PageControlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.demo.utils.PageControlUtil.fromIndex;
import static com.example.demo.utils.PageControlUtil.toIndex;

@Controller
public class TradeBuyerController {
    @Autowired
    TradeBuyerService tradeBuyerService;

    @Autowired
    AccountService accountService;

    @Autowired
    OrderService orderService;

    @RequestMapping("/orderList/{userId}")
    public String orderList(@PathVariable("userId")String userId,
                            Model model){
        SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
        OrderSearchDTO orderSearchDTO = new OrderSearchDTO();
        orderSearchDTO.setStatus(0);

        ResultDTO<TradeDTO> resultDTO = orderService.OrderSearchPage(orderSearchDTO, userId, null);

        if(resultDTO.getData().getUser().getUserRole()==0){
            sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
        }else{
            sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.SellerSidePanel);
        }
        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.TRADE_BUYER_ID);
        sidePanelStatusDTO.setCurSubMenu(Switcher.MenuSwitcher.TRADE_BUYER_ORDER_DETAIL_ID);
        sidePanelStatusDTO.setUserId(userId);


        if(resultDTO.getCode() < 0){
            return "error/404";
        }

        PageDTO pageDTO = new PageDTO();
        pageDTO.setCur(1);
        pageDTO.setUrl("/orderListPage/"+userId);
        List<OrderInfo> allList =resultDTO.getData().getOrders();
        List<OrderInfo> showList = allList.subList(fromIndex(pageDTO.getCur()),
                toIndex(pageDTO.getCur(), allList.size()));
        String json = JSONArray.toJSON(allList).toString();
        pageDTO.setTot(PageControlUtil.totPage(allList.size()));
        pageDTO.setStyle(PageControlUtil.setStyle(pageDTO));

        ListUrlDTO urlDTO = new ListUrlDTO();
        urlDTO.setSearchUrl("/orderListSearch/");
        urlDTO.setDetailUrl("/orderDetail/");

        model.addAttribute("url", urlDTO);
        model.addAttribute("user", resultDTO.getData().getUser());
        model.addAttribute("orders", showList);
        model.addAttribute("list", json);
        model.addAttribute("sidePanel", sidePanelStatusDTO);
        model.addAttribute("page", pageDTO);
        return "tradeBuyer/orderList";
    }

    @RequestMapping("/orderListSearch/{userId}")
    public String orderListSearch(@PathVariable("userId")String userId,
                                  @RequestParam("orderId") String orderId,
                                  @RequestParam("orderName") String orderName,
                                  @RequestParam("orderDes") String orderDes,
                                  @RequestParam("itemName") String itemName,
                                  @RequestParam("id") String id,
                                  @RequestParam("type") String type,
                                  @RequestParam("startTime") String startTime,
                                  @RequestParam("endTime") String endTime,
                                  Model model){
        SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
        sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.TRADE_BUYER_ID);
        sidePanelStatusDTO.setCurSubMenu(Switcher.MenuSwitcher.TRADE_BUYER_ORDER_DETAIL_ID);
        sidePanelStatusDTO.setUserId(userId);

        OrderSearchDTO orderSearchDTO = new OrderSearchDTO();
        orderSearchDTO.setOrderName(orderName);
        orderSearchDTO.setOrderDescription(orderDes);
        orderSearchDTO.setOrderId(orderId);
        orderSearchDTO.setItemName(itemName);
        orderSearchDTO.setType(type);
        orderSearchDTO.setWitchTime(0);
        orderSearchDTO.setStartTime(startTime);
        orderSearchDTO.setEndTime(endTime);

        ResultDTO<TradeDTO> resultDTO = orderService.OrderSearchPage(orderSearchDTO, userId, id);
        if(resultDTO.getCode() < 0){
            return "error/404";
        }
        PageDTO pageDTO = new PageDTO();
        pageDTO.setCur(1);
        pageDTO.setUrl("/orderListPage/"+userId);

        List<OrderInfo> allList = resultDTO.getData().getOrders();
        List<OrderInfo> showList = allList.subList(fromIndex(pageDTO.getCur()),
                toIndex(pageDTO.getCur(), allList.size()));
        String json = JSONArray.toJSON(allList).toString();

        pageDTO.setTot(PageControlUtil.totPage(allList.size()));
        pageDTO.setStyle(PageControlUtil.setStyle(pageDTO));
        ListUrlDTO urlDTO = new ListUrlDTO();
        urlDTO.setSearchUrl("/orderListSearch/");
        urlDTO.setDetailUrl("/orderDetail/");

        model.addAttribute("url", urlDTO);

        model.addAttribute("user", resultDTO.getData().getUser());
        model.addAttribute("orders", showList);
        model.addAttribute("list", json);
        model.addAttribute("sidePanel", sidePanelStatusDTO);
        model.addAttribute("page", pageDTO);

        return "tradeBuyer/orderList";
    }

    @RequestMapping("/orderListPage/{userId}")
    public String page(@PathVariable("userId") String userId,
                       @RequestParam("page") String page,
                       @RequestParam("list") String json,
                       Model model){

        List<OrderInfo> list =
                JsonListUtil.jsonToList(json, OrderInfo.class);

        SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
        sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.TRADE_BUYER_ID);
        sidePanelStatusDTO.setCurSubMenu(Switcher.MenuSwitcher.TRADE_BUYER_ORDER_DETAIL_ID);
        sidePanelStatusDTO.setUserId(userId);

        ResultDTO<User> userResultDTO = accountService.getUserById(userId);
        if(userResultDTO.getCode() < 0){
            return "error/404";
        }
        PageDTO pageDTO = new PageDTO();
        pageDTO.setCur(Integer.parseInt(page));
        pageDTO.setUrl("/orderListPage/"+userId);

        List<OrderInfo> showList = list.subList(fromIndex(pageDTO.getCur()),
                toIndex(pageDTO.getCur(), list.size()));

        pageDTO.setTot(PageControlUtil.totPage(list.size()));
        pageDTO.setStyle(PageControlUtil.setStyle(pageDTO));

        ListUrlDTO urlDTO = new ListUrlDTO();
        urlDTO.setSearchUrl("/orderListSearch/");
        urlDTO.setDetailUrl("/orderDetail/");

        model.addAttribute("url", urlDTO);
        model.addAttribute("user", userResultDTO.getData());
        model.addAttribute("orders", showList);
        model.addAttribute("list", json);
        model.addAttribute("sidePanel", sidePanelStatusDTO);
        model.addAttribute("page", pageDTO);

        return "tradeBuyer/orderList";
    }

    @RequestMapping("/orderDetail/{userId}/{orderId}")
    public String orderDetail(@PathVariable("userId")String userId,
                              @PathVariable("orderId")String orderId,
                              Model model){
        System.out.println(orderId);
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
