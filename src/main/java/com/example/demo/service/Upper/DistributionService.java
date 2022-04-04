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
public class DistributionService {
    @Autowired
    OrderBasicService orderBasicService;
    @Autowired
    AccountBasicService accountBasicService;

    public ResultDTO<TradeDTO> deliverOrderList(String userId, OrderSearchDTO orderSearchDTO){
        ResultDTO<TradeDTO> resultDTO = new ResultDTO<>();
        TradeDTO tradeDTO = new TradeDTO();

        resultDTO.setCode(-1);
        try{
            ResultDTO<User> userResultDTO = accountBasicService.getUserById(userId);
            if(userResultDTO.getData().getUserRole()==0){
                orderSearchDTO.setBuyerId(userId);
            }else {
                orderSearchDTO.setCompanyId(userResultDTO.getData().getCompanyId());
            }
            ResultDTO<List<OrderInfo>> orderList = orderBasicService.searchOrders(orderSearchDTO);
            tradeDTO.setUser(userResultDTO.getData());
            tradeDTO.setOrders(orderList.getData());
        }catch (Exception e){
            LogUtil.errorLog(e, getClass().getName());
            return resultDTO;
        }
        resultDTO.setCode(0);
        resultDTO.setData(tradeDTO);
        return resultDTO;
    }
}
