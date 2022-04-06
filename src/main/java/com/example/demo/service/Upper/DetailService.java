package com.example.demo.service.Upper;

import com.example.demo.model.DTO.DetailDTO;
import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.Model.Order;
import com.example.demo.model.Model.User;
import com.example.demo.service.lower.AccountBasicService;
import com.example.demo.service.lower.DetailBasicService;
import com.example.demo.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DetailService {

    @Autowired
    AccountBasicService accountBasicService;

    @Autowired
    DetailBasicService detailBasicService;

    public ResultDTO<DetailDTO> getOrderDetail(String orderId, String userId){
        ResultDTO<DetailDTO> resultDTO = new ResultDTO<>();
        DetailDTO detailDTO = new DetailDTO();
        resultDTO.setData(detailDTO);
        resultDTO.setCode(-1);
        try{
            ResultDTO<User> userResultDTO = accountBasicService.getUserById(userId);
            if(userResultDTO.getCode()<0){
                return resultDTO;
            }
            ResultDTO<Order> orderResultDTO = detailBasicService.getOrderDetail(orderId);
            if(orderResultDTO.getCode()<0){
                return resultDTO;
            }
            detailDTO.setOrder(orderResultDTO.getData());
            detailDTO.setUser(userResultDTO.getData());
        }catch (Exception e){
            LogUtil.errorLog(e, getClass().getName());
        }
        resultDTO.setCode(0);
        return resultDTO;
    }
}
