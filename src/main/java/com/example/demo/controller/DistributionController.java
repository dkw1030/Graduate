package com.example.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.example.demo.model.Constant.Switcher;
import com.example.demo.model.DTO.*;
import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.Model.OrderItem;
import com.example.demo.model.Model.User;
import com.example.demo.model.Model.resultType.OrderInfo;
import com.example.demo.service.Upper.*;
import com.example.demo.utils.JsonListUtil;
import com.example.demo.utils.PageControlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.example.demo.utils.PageControlUtil.*;

@Controller
public class DistributionController {
    @Autowired
    OrderService orderService;

    @Autowired
    AccountService accountService;

    @Autowired
    DetailService detailService;

    /**
     * 等待发货的部分
     * @param userId
     * @param model
     * @return
     */

    @RequestMapping("/distribution/waitDeliverOrderList/{userId}")
    public String waitDeliverOrderList(@PathVariable("userId")String userId,
                                       Model model){
        SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
        OrderSearchDTO orderSearchDTO = new OrderSearchDTO();

        orderSearchDTO.setStatus(4);
        orderSearchDTO.setStatusS(7);
        orderSearchDTO.setWitchTime(1);
        ResultDTO<TradeDTO> resultDTO = orderService.OrderSearchPage(orderSearchDTO,userId, null);

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

        List<OrderInfo> allList =resultDTO.getData().getOrders();
        PageDTO pageDTO = generatePageDTO(allList, 1);
        List<OrderInfo> showList = allList.subList(fromIndex(pageDTO.getCur()),
                toIndex(pageDTO.getCur(), allList.size()));
        String json = JSONArray.toJSON(allList).toString();

        ListUrlDTO urlDTO = new ListUrlDTO();
        urlDTO.setDetailUrl("/distribution/waitDeliverOrderDetail/");
        urlDTO.setSearchUrl("/distribution/waitDeliverOrderListSearch/");
        pageDTO.setUrl("/distribution/waitDeliverOrderListPage/"+userId);

        model.addAttribute("url", urlDTO);
        model.addAttribute("user", resultDTO.getData().getUser());
        model.addAttribute("orders", showList);
        model.addAttribute("list", json);
        model.addAttribute("sidePanel", sidePanelStatusDTO);
        model.addAttribute("page", pageDTO);
        return "/orderList/orderList";
    }

    @RequestMapping("/distribution/waitDeliverOrderListSearch/{userId}")
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


        OrderSearchDTO orderSearchDTO = new OrderSearchDTO();
        orderSearchDTO.setStatus(4);
        orderSearchDTO.setStatusS(7);
        orderSearchDTO.setOrderName(orderName);
        orderSearchDTO.setOrderDescription(orderDes);
        orderSearchDTO.setOrderId(orderId);
        orderSearchDTO.setItemName(itemName);
        orderSearchDTO.setType(type);
        orderSearchDTO.setWitchTime(1);
        orderSearchDTO.setStartTime(startTime);
        orderSearchDTO.setEndTime(endTime);

        ResultDTO<TradeDTO> resultDTO = orderService.OrderSearchPage(orderSearchDTO, userId, id);
        if(resultDTO.getCode() < 0){
            return "error/404";
        }
        SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.DISTRIBUTION_ID);
        sidePanelStatusDTO.setCurSubMenu(Switcher.MenuSwitcher.DISTRIBUTION_WAIT_DELIVER_ID);
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
        urlDTO.setDetailUrl("/distribution/waitDeliverOrderDetail/");
        urlDTO.setSearchUrl("/distribution/waitDeliverOrderListSearch/");
        pageDTO.setUrl("/distribution/waitDeliverOrderListPage/"+userId);

        model.addAttribute("url", urlDTO);
        model.addAttribute("user", resultDTO.getData().getUser());
        model.addAttribute("orders", showList);
        model.addAttribute("list", json);
        model.addAttribute("sidePanel", sidePanelStatusDTO);
        model.addAttribute("page", pageDTO);

        return "/orderList/orderList";
    }

    @RequestMapping("/distribution/waitDeliverOrderListPage/{userId}")
    public String page(@PathVariable("userId") String userId,
                       @RequestParam("page") String page,
                       @RequestParam("list") String json,
                       Model model){

        List<OrderInfo> list =
                JsonListUtil.jsonToList(json, OrderInfo.class);

        SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.DISTRIBUTION_ID);
        sidePanelStatusDTO.setCurSubMenu(Switcher.MenuSwitcher.DISTRIBUTION_WAIT_DELIVER_ID);
        sidePanelStatusDTO.setUserId(userId);

        ResultDTO<User> userResultDTO = accountService.getUserById(userId);
        if(userResultDTO.getCode() < 0){
            return "error/404";
        }
        if(userResultDTO.getData().getUserRole()==0){
            sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
        }else{
            sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.SellerSidePanel);
        }

        PageDTO pageDTO = generatePageDTO(list, Integer.parseInt(page));

        List<OrderInfo> showList = list.subList(fromIndex(pageDTO.getCur()),
                toIndex(pageDTO.getCur(), list.size()));
        ListUrlDTO urlDTO = new ListUrlDTO();
        urlDTO.setDetailUrl("/distribution/waitDeliverOrderDetail/");
        urlDTO.setSearchUrl("/distribution/waitDeliverOrderListSearch/");
        pageDTO.setUrl("/distribution/waitDeliverOrderListPage/"+userId);

        model.addAttribute("url", urlDTO);
        model.addAttribute("user", userResultDTO.getData());
        model.addAttribute("orders", showList);
        model.addAttribute("list", json);
        model.addAttribute("sidePanel", sidePanelStatusDTO);
        model.addAttribute("page", pageDTO);

        return "/orderList/orderList";
    }

    @RequestMapping("/distribution/waitDeliverOrderDetail/{userId}/{orderId}")
    public String orderDetail(@PathVariable("userId")String userId,
                              @PathVariable("orderId")String orderId,
                              Model model){
        ResultDTO<DetailDTO> resultDTO = detailService.getOrderDetail(orderId, userId);
        if(resultDTO.getCode() < 0){
            return "error/404";
        }
        int show = 1;
        for (OrderItem item :
                resultDTO.getData().getOrder().getOrderItems()) {
            if(item.getProcess()<100){
                show = 0;
                break;
            }
        }
        model.addAttribute("show", show);
        model.addAttribute("user", resultDTO.getData().getUser());
        model.addAttribute("order", resultDTO.getData().getOrder().getOrderInfo());
        model.addAttribute("item", resultDTO.getData().getOrder().getOrderItems());
        return "distribution/waitDeliverOrderDetail";
    }

    /**
     * 运输中的部分
     */
    @RequestMapping("/distribution/deliveringOrderList/{userId}")
    public String deliveringOrderList(@PathVariable("userId")String userId,
                                       Model model){
        SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
        OrderSearchDTO orderSearchDTO = new OrderSearchDTO();


        orderSearchDTO.setStatus(5);
        orderSearchDTO.setWitchTime(2);
        ResultDTO<TradeDTO> resultDTO = orderService.OrderSearchPage(orderSearchDTO,userId, null);

        if(resultDTO.getData().getUser().getUserRole()==0){
            sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
        }else{
            sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.SellerSidePanel);
        }
        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.DISTRIBUTION_ID);
        sidePanelStatusDTO.setCurSubMenu(Switcher.MenuSwitcher.DISTRIBUTION_DELIVERING_ID);
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
        urlDTO.setDetailUrl("/distribution/deliveringOrderDetail/");
        urlDTO.setSearchUrl("/distribution/deliveringOrderListSearch/");
        pageDTO.setUrl("/distribution/deliveringOrderListPage/"+userId);

        model.addAttribute("url", urlDTO);
        model.addAttribute("user", resultDTO.getData().getUser());
        model.addAttribute("orders", showList);
        model.addAttribute("list", json);
        model.addAttribute("sidePanel", sidePanelStatusDTO);
        model.addAttribute("page", pageDTO);
        return "/orderList/orderList";
    }

    @RequestMapping("/distribution/deliveringOrderListSearch/{userId}")
    public String deliveringOrderListSearch(@PathVariable("userId")String userId,
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

        OrderSearchDTO orderSearchDTO = new OrderSearchDTO();
        orderSearchDTO.setStatus(5);
        orderSearchDTO.setOrderName(orderName);
        orderSearchDTO.setOrderDescription(orderDes);
        orderSearchDTO.setOrderId(orderId);
        orderSearchDTO.setItemName(itemName);
        orderSearchDTO.setType(type);
        orderSearchDTO.setWitchTime(2);
        orderSearchDTO.setStartTime(startTime);
        orderSearchDTO.setEndTime(endTime);

        ResultDTO<TradeDTO> resultDTO = orderService.OrderSearchPage(orderSearchDTO, userId, id);
        if(resultDTO.getCode() < 0){
            return "error/404";
        }
        if(resultDTO.getData().getUser().getUserRole()==0){
            sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
        }else{
            sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.SellerSidePanel);
        }
        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.DISTRIBUTION_ID);
        sidePanelStatusDTO.setCurSubMenu(Switcher.MenuSwitcher.DISTRIBUTION_DELIVERING_ID);
        sidePanelStatusDTO.setUserId(userId);

        List<OrderInfo> allList =resultDTO.getData().getOrders();
        PageDTO pageDTO = generatePageDTO(allList, 1);
        List<OrderInfo> showList = allList.subList(fromIndex(pageDTO.getCur()),
                toIndex(pageDTO.getCur(), allList.size()));
        String json = JSONArray.toJSON(allList).toString();

        ListUrlDTO urlDTO = new ListUrlDTO();
        urlDTO.setDetailUrl("/distribution/deliveringOrderDetail/");
        urlDTO.setSearchUrl("/distribution/deliveringOrderListSearch/");
        pageDTO.setUrl("/distribution/deliveringOrderListPage/"+userId);

        model.addAttribute("url", urlDTO);
        model.addAttribute("user", resultDTO.getData().getUser());
        model.addAttribute("orders", showList);
        model.addAttribute("list", json);
        model.addAttribute("sidePanel", sidePanelStatusDTO);
        model.addAttribute("page", pageDTO);

        return "/orderList/orderList";
    }

    @RequestMapping("/distribution/deliveringOrderListPage/{userId}")
    public String deliveringPage(@PathVariable("userId") String userId,
                       @RequestParam("page") String page,
                       @RequestParam("list") String json,
                       Model model){

        List<OrderInfo> list =
                JsonListUtil.jsonToList(json, OrderInfo.class);

        SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();

        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.DISTRIBUTION_ID);
        sidePanelStatusDTO.setCurSubMenu(Switcher.MenuSwitcher.DISTRIBUTION_WAIT_DELIVER_ID);
        sidePanelStatusDTO.setUserId(userId);

        ResultDTO<User> userResultDTO = accountService.getUserById(userId);
        if(userResultDTO.getCode() < 0){
            return "error/404";
        }
        if(userResultDTO.getData().getUserRole()==0){
            sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
        }else{
            sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.SellerSidePanel);
        }

        PageDTO pageDTO = generatePageDTO(list, Integer.parseInt(page));

        List<OrderInfo> showList = list.subList(fromIndex(pageDTO.getCur()),
                toIndex(pageDTO.getCur(), list.size()));
        ListUrlDTO urlDTO = new ListUrlDTO();
        urlDTO.setDetailUrl("/distribution/deliveringOrderDetail/");
        urlDTO.setSearchUrl("/distribution/deliveringOrderListSearch/");
        pageDTO.setUrl("/distribution/deliveringOrderListPage/"+userId);

        model.addAttribute("url", urlDTO);
        model.addAttribute("user", userResultDTO.getData());
        model.addAttribute("orders", showList);
        model.addAttribute("list", json);
        model.addAttribute("sidePanel", sidePanelStatusDTO);
        model.addAttribute("page", pageDTO);

        return "/orderList/orderList";
    }

    @RequestMapping("/distribution/deliveringOrderDetail/{userId}/{orderId}")
    public String deliveringOrderDetail(@PathVariable("userId")String userId,
                              @PathVariable("orderId")String orderId,
                              Model model){
        ResultDTO<DetailDTO> resultDTO = detailService.getOrderDetail(orderId, userId);
        if(resultDTO.getCode() < 0){
            return "error/404";
        }
        model.addAttribute("user", resultDTO.getData().getUser());
        model.addAttribute("order", resultDTO.getData().getOrder().getOrderInfo());
        model.addAttribute("item", resultDTO.getData().getOrder().getOrderItems());
        return "distribution/deliveringOrderDetail";
    }


    /**
     * 已完成的部分
     */
    @RequestMapping("/distribution/waitReceiveOrderList/{userId}")
    public String waitReceiveOrderList(@PathVariable("userId")String userId,
                                       Model model){
        SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
        OrderSearchDTO orderSearchDTO = new OrderSearchDTO();

        orderSearchDTO.setWitchTime(3);
        orderSearchDTO.setStatus(6);
        ResultDTO<TradeDTO> resultDTO = orderService.OrderSearchPage(orderSearchDTO,userId, null);

        if(resultDTO.getData().getUser().getUserRole()==0){
            sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
        }else{
            sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.SellerSidePanel);
        }
        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.DISTRIBUTION_ID);
        sidePanelStatusDTO.setCurSubMenu(Switcher.MenuSwitcher.DISTRIBUTION_RESULT_ID);
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
        urlDTO.setDetailUrl("/distribution/waitReceiveOrderDetail/");
        urlDTO.setSearchUrl("/distribution/waitReceiveOrderListSearch/");
        pageDTO.setUrl("/distribution/waitReceiveOrderListPage/"+userId);

        model.addAttribute("url", urlDTO);
        model.addAttribute("user", resultDTO.getData().getUser());
        model.addAttribute("orders", showList);
        model.addAttribute("list", json);
        model.addAttribute("sidePanel", sidePanelStatusDTO);
        model.addAttribute("page", pageDTO);
        return "/orderList/orderList";
    }

    @RequestMapping("/distribution/waitReceiveOrderListSearch/{userId}")
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
        SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();

        OrderSearchDTO orderSearchDTO = new OrderSearchDTO();
        orderSearchDTO.setStatus(6);
        orderSearchDTO.setOrderName(orderName);
        orderSearchDTO.setOrderDescription(orderDes);
        orderSearchDTO.setOrderId(orderId);
        orderSearchDTO.setItemName(itemName);
        orderSearchDTO.setType(type);
        orderSearchDTO.setWitchTime(3);
        orderSearchDTO.setStartTime(startTime);
        orderSearchDTO.setEndTime(endTime);

        ResultDTO<TradeDTO> resultDTO = orderService.OrderSearchPage(orderSearchDTO, userId, id);
        if(resultDTO.getCode() < 0){
            return "error/404";
        }
        if(resultDTO.getData().getUser().getUserRole()==0){
            sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
        }else{
            sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.SellerSidePanel);
        }
        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.DISTRIBUTION_ID);
        sidePanelStatusDTO.setCurSubMenu(Switcher.MenuSwitcher.DISTRIBUTION_RESULT_ID);
        sidePanelStatusDTO.setUserId(userId);

        List<OrderInfo> allList =resultDTO.getData().getOrders();
        PageDTO pageDTO = generatePageDTO(allList, 1);
        List<OrderInfo> showList = allList.subList(fromIndex(pageDTO.getCur()),
                toIndex(pageDTO.getCur(), allList.size()));
        String json = JSONArray.toJSON(allList).toString();

        ListUrlDTO urlDTO = new ListUrlDTO();
        urlDTO.setDetailUrl("/distribution/waitReceiveOrderDetail/");
        urlDTO.setSearchUrl("/distribution/waitReceiveOrderListSearch/");
        pageDTO.setUrl("/distribution/waitReceiveOrderListPage/"+userId);

        model.addAttribute("url", urlDTO);
        model.addAttribute("user", resultDTO.getData().getUser());
        model.addAttribute("orders", showList);
        model.addAttribute("list", json);
        model.addAttribute("sidePanel", sidePanelStatusDTO);
        model.addAttribute("page", pageDTO);

        return "/orderList/orderList";
    }

    @RequestMapping("/distribution/waitReceiveOrderListPage/{userId}")
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
        urlDTO.setDetailUrl("/distribution/waitReceiveOrderDetail/");
        urlDTO.setSearchUrl("/distribution/waitReceiveOrderListSearch/");
        pageDTO.setUrl("/distribution/waitReceiveOrderListPage/"+userId);

        model.addAttribute("url", urlDTO);
        model.addAttribute("user", userResultDTO.getData());
        model.addAttribute("orders", showList);
        model.addAttribute("list", json);
        model.addAttribute("sidePanel", sidePanelStatusDTO);
        model.addAttribute("page", pageDTO);

        return "/orderList/orderList";
    }

    @RequestMapping("/distribution/waitReceiveOrderDetail/{userId}/{orderId}")
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
        return "distribution/waitReceiveOrderDetail";
    }
}
