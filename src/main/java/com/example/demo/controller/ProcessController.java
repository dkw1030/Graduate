package com.example.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.example.demo.model.Constant.Switcher;
import com.example.demo.model.DTO.*;
import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.Model.User;
import com.example.demo.model.Model.resultType.OrderInfo;
import com.example.demo.service.Upper.AccountService;
import com.example.demo.service.Upper.DetailService;
import com.example.demo.service.Upper.OrderService;
import com.example.demo.utils.JsonListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.example.demo.utils.PageControlUtil.*;

@Controller
public class ProcessController {
    @Autowired
    OrderService orderService;

    @Autowired
    AccountService accountService;

    @Autowired
    DetailService detailService;


    @RequestMapping("/process/processOrderList/{userId}")
    public String waitReceiveOrderList(@PathVariable("userId")String userId,
                                       Model model){
        SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
        OrderSearchDTO orderSearchDTO = new OrderSearchDTO();


        orderSearchDTO.setStatus(7);
        orderSearchDTO.setStatusS(4);
        orderSearchDTO.setWitchTime(1);
        ResultDTO<TradeDTO> resultDTO = orderService.OrderSearchPage(orderSearchDTO,userId, null);

        if(resultDTO.getData().getUser().getUserRole()==0){
            sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
        }else{
            sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.SellerSidePanel);
        }
        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.PROCESS_ID);
        sidePanelStatusDTO.setUserId(userId);


        if(resultDTO.getCode() < 0){
            return "error/404";
        }

        List<OrderInfo> allList =resultDTO.getData().getOrders();
        PageDTO pageDTO = generatePageDTO(allList, 1);
        List<OrderInfo> showList = allList.subList(fromIndex(pageDTO.getCur()),
                toIndex(pageDTO.getCur(), allList.size()));
        String json = JSONArray.toJSON(allList).toString();

        ListUrlDTO urlDTO = new ListUrlDTO();
        urlDTO.setDetailUrl("/process/processOrderDetail/");
        urlDTO.setSearchUrl("/process/processOrderListSearch/");
        pageDTO.setUrl("/process/processOrderListPage/"+userId);

        model.addAttribute("url", urlDTO);
        model.addAttribute("user", resultDTO.getData().getUser());
        model.addAttribute("orders", showList);
        model.addAttribute("list", json);
        model.addAttribute("sidePanel", sidePanelStatusDTO);
        model.addAttribute("page", pageDTO);
        return "/orderList/orderList";
    }

    @RequestMapping("/process/processOrderListSearch/{userId}")
    public String waitReceiveOrderListSearch(@PathVariable("userId")String userId,
                                             @RequestParam("orderId") String orderId,
                                             @RequestParam("orderName") String orderName,
                                             @RequestParam("orderDes") String orderDes,
                                             @RequestParam("itemName") String itemName,
                                             @RequestParam("id") String id,
                                             @RequestParam("type") String type,
                                             @RequestParam("startTime") String startTime,
                                             @RequestParam("endTime") String endTime,
                                             Model model){
        OrderSearchDTO orderSearchDTO = new OrderSearchDTO();
        orderSearchDTO.setStatus(7);
        orderSearchDTO.setStatusS(4);
        orderSearchDTO.setWitchTime(1);
        orderSearchDTO.setOrderName(orderName);
        orderSearchDTO.setOrderDescription(orderDes);
        orderSearchDTO.setOrderId(orderId);
        orderSearchDTO.setItemName(itemName);
        orderSearchDTO.setType(type);
        orderSearchDTO.setStartTime(startTime);
        orderSearchDTO.setEndTime(endTime);

        ResultDTO<TradeDTO> resultDTO = orderService.OrderSearchPage(orderSearchDTO, userId, id);
        if(resultDTO.getCode() < 0){
            return "error/404";
        }
        SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.PROCESS_ID);
        sidePanelStatusDTO.setUserId(userId);
        if(resultDTO.getData().getUser().getUserRole()==0){
            sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
        }else{
            sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.SellerSidePanel);
        }


        List<OrderInfo> allList =resultDTO.getData().getOrders();
        PageDTO pageDTO = generatePageDTO(allList, 1);
        List<OrderInfo> showList = allList.subList(fromIndex(pageDTO.getCur()),
                toIndex(pageDTO.getCur(), allList.size()));
        String json = JSONArray.toJSON(allList).toString();

        ListUrlDTO urlDTO = new ListUrlDTO();
        urlDTO.setDetailUrl("/process/processOrderDetail/");
        urlDTO.setSearchUrl("/process/processOrderListSearch/");
        pageDTO.setUrl("/process/processOrderListPage/"+userId);

        model.addAttribute("url", urlDTO);
        model.addAttribute("user", resultDTO.getData().getUser());
        model.addAttribute("orders", showList);
        model.addAttribute("list", json);
        model.addAttribute("sidePanel", sidePanelStatusDTO);
        model.addAttribute("page", pageDTO);

        return "/orderList/orderList";
    }

    @RequestMapping("/process/processOrderListPage/{userId}")
    public String waitReceivePage(@PathVariable("userId") String userId,
                                  @RequestParam("page") String page,
                                  @RequestParam("list") String json,
                                  Model model){

        List<OrderInfo> list =
                JsonListUtil.jsonToList(json, OrderInfo.class);

        SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();

        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.DISTRIBUTION_ID);
        sidePanelStatusDTO.setCurSubMenu(Switcher.MenuSwitcher.DISTRIBUTION_RESULT_ID);
        sidePanelStatusDTO.setUserId(userId);

        ResultDTO<User> userResultDTO = accountService.getUserById(userId);
        if(userResultDTO.getCode() < 0){
            return "error/404";
        }
        if(userResultDTO.getData().getUserRole()==0){
            sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
        }else {
            sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.SellerSidePanel);
        }

        PageDTO pageDTO = generatePageDTO(list, Integer.parseInt(page));

        List<OrderInfo> showList = list.subList(fromIndex(pageDTO.getCur()),
                toIndex(pageDTO.getCur(), list.size()));
        ListUrlDTO urlDTO = new ListUrlDTO();
        urlDTO.setDetailUrl("/process/processOrderDetail/");
        urlDTO.setSearchUrl("/process/processOrderListSearch/");
        pageDTO.setUrl("/process/processOrderListPage/"+userId);

        model.addAttribute("url", urlDTO);
        model.addAttribute("user", userResultDTO.getData());
        model.addAttribute("orders", showList);
        model.addAttribute("list", json);
        model.addAttribute("sidePanel", sidePanelStatusDTO);
        model.addAttribute("page", pageDTO);

        return "/orderList/orderList";
    }

    @RequestMapping("/process/processOrderDetail/{userId}/{orderId}")
    public String waitReceiveOrderDetail(@PathVariable("userId")String userId,
                                         @PathVariable("orderId")String orderId,
                                         Model model){
        ResultDTO<DetailDTO> resultDTO = detailService.getOrderDetail(orderId, userId);
        if(resultDTO.getCode() < 0){
            return "error/404";
        }
        model.addAttribute("user", resultDTO.getData().getUser());
        model.addAttribute("order", resultDTO.getData().getOrder().getOrderInfo());
        model.addAttribute("item", resultDTO.getData().getOrder().getOrderItems());
        return "process/processSellerOrderDetail";
    }

    @RequestMapping("/changeProcess")
    public String processChange(@RequestParam("process")int process,
                                @RequestParam("orderId")String orderId,
                                @RequestParam("userId")String userId,
                                @RequestParam("itemName")String itemName,
                                Model model){
        ItemChangeDTO itemChangeDTO = new ItemChangeDTO();
        itemChangeDTO.setProcess(process);
        itemChangeDTO.setItemName(itemName);
        itemChangeDTO.setOrderId(orderId);
        ResultDTO<String> changeResult = orderService.changeProcess(itemChangeDTO);
        ResultDTO<DetailDTO> resultDTO = detailService.getOrderDetail(orderId, userId);
        if(resultDTO.getCode() < 0){
            return "error/404";
        }
        model.addAttribute("user", resultDTO.getData().getUser());
        model.addAttribute("order", resultDTO.getData().getOrder().getOrderInfo());
        model.addAttribute("item", resultDTO.getData().getOrder().getOrderItems());
        return "process/processSellerOrderDetail";
    }
}
