package com.example.demo.service.Upper;

import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.DTO.TradeDTO;
import com.example.demo.model.Model.Order;
import com.example.demo.model.Model.User;
import com.example.demo.service.lower.AccountBasicService;
import com.example.demo.service.lower.OrderBasicService;
import com.example.demo.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeBuyerService {
    @Autowired
    AccountBasicService accountBasicService;

    @Autowired
    OrderBasicService orderBasicService;

    public ResultDTO<TradeDTO> TradeBuyerPage(String userId){
        ResultDTO<TradeDTO> resultDTO = new ResultDTO<>();
        TradeDTO tradeDTO = new TradeDTO();
        resultDTO.setData(tradeDTO);
        resultDTO.setCode(-1);
        try{
            ResultDTO<User> userResultDTO = accountBasicService.getUserById(userId);
            tradeDTO.setUser(userResultDTO.getData());
            ResultDTO<List<Order>> orderResult = orderBasicService.getOrderByUser(userResultDTO.getData());
            tradeDTO.setOrders(orderResult.getData());
        }catch(Exception e){
            LogUtil.errorLog(e, getClass().getName());
        }
        resultDTO.setCode(0);
        return resultDTO;
    }
}
