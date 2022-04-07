package com.example.demo.service.Upper;

import com.example.demo.model.DTO.OrderSearchDTO;
import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.DTO.TradeDTO;
import com.example.demo.model.Model.User;
import com.example.demo.model.Model.resultType.OrderInfo;
import com.example.demo.service.lower.AccountBasicService;
import com.example.demo.service.lower.OrderBasicService;
import com.example.demo.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    AccountBasicService accountBasicService;

    @Autowired
    OrderBasicService orderBasicService;

    public ResultDTO<TradeDTO> OrderSearchPage(OrderSearchDTO orderSearchDTO, String userId, String id){
        ResultDTO<TradeDTO> resultDTO = new ResultDTO<>();
        TradeDTO tradeDTO = new TradeDTO();
        resultDTO.setData(tradeDTO);
        resultDTO.setCode(-1);
        try{
            ResultDTO<User> userResultDTO = accountBasicService.getUserById(userId);
            tradeDTO.setUser(userResultDTO.getData());
            if(userResultDTO.getData().getUserRole() == 0){
                orderSearchDTO.setBuyerId(userId);
                orderSearchDTO.setCompanyId(id);
            }else {
                orderSearchDTO.setBuyerId(id);
                orderSearchDTO.setCompanyId(userResultDTO.getData().getCompanyId());
            }

            ResultDTO<List<OrderInfo>> orderResult = orderBasicService.searchOrders(orderSearchDTO);
            tradeDTO.setOrders(orderResult.getData());
        }catch(Exception e){
            LogUtil.errorLog(e, getClass().getName());
        }
        resultDTO.setCode(0);
        return resultDTO;
    }
}
