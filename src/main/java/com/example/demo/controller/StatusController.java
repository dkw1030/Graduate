package com.example.demo.controller;

import com.example.demo.model.DTO.DetailDTO;
import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.Model.OrderItem;
import com.example.demo.service.Upper.DetailService;
import com.example.demo.service.Upper.InfoSellerService;
import com.example.demo.service.Upper.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class StatusController {
    @Autowired
    DetailService detailService;

    @Autowired
    OrderService orderService;

    @Autowired
    InfoSellerService infoSellerService;

    @RequestMapping("/confirm/{userId}/{orderId}/{statusStr}")
    public String confirm(@PathVariable("userId")String userId,
                          @PathVariable("orderId")String orderId,
                          @PathVariable("statusStr")String statusStr,
                          Model model){
        ResultDTO<DetailDTO> resultDTO = detailService.getOrderDetail(orderId, userId);
        if(resultDTO.getCode() < 0){
            return "error/404";
        }
        int status = Integer.parseInt(statusStr);
        //firstTime
        int f = resultDTO.getData().getOrder().getOrderInfo().getFirstTime();
        ResultDTO<String> temp = new ResultDTO<>();
        int curStatus = resultDTO.getData().getOrder().getOrderInfo().getOrderStatus();
        String companyId = resultDTO.getData().getOrder().getOrderInfo().getSellerId();
        switch (status){
            case 3:
                if(curStatus!= 1){
                    return "error/404";
                }
                if(f==0){
                    temp = orderService.updateFirstTime(orderId,1);
                    if(temp.getCode() < 0){
                        return "error/404";
                    }
                    temp = infoSellerService.addCount(2,companyId);
                    if(temp.getCode() < 0){
                        return "error/404";
                    }
                }
                break;
            case 4:
                if(curStatus!= 1){
                    return "error/404";
                }
                if(f==0){
                    temp = infoSellerService.addCount(1,companyId);
                    if(temp.getCode() < 0){
                        return "error/404";
                    }
                }else{
                    temp = orderService.updateFirstTime(orderId,0);
                    if(temp.getCode() < 0){
                        return "error/404";
                    }
                }
                break;
            case 6:
                if(curStatus!= 5){
                    return "error/404";
                }
                if(f==0){
                    temp = infoSellerService.addCount(3,companyId);
                    if(temp.getCode() < 0){
                        return "error/404";
                    }
                }
                break;
            case 7:
                if(curStatus!= 5){
                    return "error/404";
                }
                if(f==0){
                    temp = infoSellerService.addCount(4,companyId);
                    if(temp.getCode() < 0){
                        return "error/404";
                    }
                    temp = orderService.updateFirstTime(orderId,1);
                    if(temp.getCode() < 0){
                        return "error/404";
                    }
                }
                break;
        }
        //status
        ResultDTO<String> statusResult = orderService.updateOrderStatus(orderId, status);
        resultDTO.getData().getOrder().getOrderInfo().setOrderStatus(status);


        model.addAttribute("user", resultDTO.getData().getUser());
        model.addAttribute("order", resultDTO.getData().getOrder().getOrderInfo());
        model.addAttribute("item", resultDTO.getData().getOrder().getOrderItems());

        //return
        switch (status){
            case 1:
                if(curStatus!= 0){
                    return "error/404";
                }
                return "tradeBuyer/orderDetail";
            case 3:
            case 4:
                return "tradeSeller/orderSellerConfirmDetail";
            case 5:
                if(curStatus!= 4){
                    return "error/404";
                }

                return "distribution/waitDeliverOrderDetail";
            case 6:
            case 7:
                return "distribution/deliveringOrderDetail";
        }
        return "error/404";
    }
}
