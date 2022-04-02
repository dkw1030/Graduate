package com.example.demo.service.lower;

import com.example.demo.model.DTO.Result.ResultDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeBasicService {

    public ResultDTO<String> uploadOrder(List<List<String>> data){
        return new ResultDTO<>();
    }
}
