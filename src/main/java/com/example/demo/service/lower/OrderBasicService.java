package com.example.demo.service.lower;

import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.Model.Order;
import com.example.demo.model.Model.User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class OrderBasicService {

    public ResultDTO<List<Order>> getOrderByUser(User user){
        List<Order> orders = Collections.emptyList();
        ResultDTO<List<Order>> resultDTO = new ResultDTO<>();
        resultDTO.setCode(0);
        resultDTO.setData(orders);
        return resultDTO;
    }
}
