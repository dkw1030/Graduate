package com.example.demo.service.Upper;

import com.example.demo.model.DTO.CompanyDetailDTO;
import com.example.demo.model.DTO.FrameworkDTO;
import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.Model.CompanyDetail;
import com.example.demo.model.Model.resultType.OrderPercentage;
import com.example.demo.service.lower.OrderBasicService;
import com.example.demo.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalysisService {
    @Autowired
    OrderBasicService orderBasicService;

    @Autowired
    InfoSellerService infoSellerService;

    public ResultDTO<OrderPercentage> analysisBuyer(String userId){
        ResultDTO<OrderPercentage> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        try {
            ResultDTO<OrderPercentage> orderPercentageTot = orderBasicService.getOrderPercentage(userId, 0);
            ResultDTO<OrderPercentage> orderCost = orderBasicService.getOrderCost(userId, 0);
            orderPercentageTot.getData().setDelivering(orderCost.getData().getDelivering());
            orderPercentageTot.getData().setFinish(orderCost.getData().getFinish());
            orderPercentageTot.getData().setProducing(orderCost.getData().getProducing());
            resultDTO = orderPercentageTot;
        }catch (Exception e){
            LogUtil.errorLog(e, getClass().getName());
        }
        return resultDTO;
    }

    public ResultDTO<CompanyDetailDTO> analysisSeller(String companyId){
        ResultDTO<CompanyDetailDTO> resultDTO = new ResultDTO<>();
        CompanyDetailDTO companyDetailDTO = new CompanyDetailDTO();
        resultDTO.setCode(-1);
        try {
            ResultDTO<OrderPercentage> orderPercentageTot = orderBasicService.getOrderPercentage(companyId, 1);
            ResultDTO<OrderPercentage> orderCost = orderBasicService.getOrderCost(companyId, 1);
            orderPercentageTot.getData().setDelivering(orderCost.getData().getDelivering());
            orderPercentageTot.getData().setFinish(orderCost.getData().getFinish());
            orderPercentageTot.getData().setProducing(orderCost.getData().getProducing());
            companyDetailDTO.setOrderPercentage(orderPercentageTot.getData());
            ResultDTO<CompanyDetail> companyDetail = infoSellerService.getCompanyDetailByCompanyId(companyId);
            companyDetailDTO.setCompanyDetail(companyDetail.getData());
            resultDTO.setData(companyDetailDTO);
            resultDTO.setCode(0);
        }catch (Exception e){
            LogUtil.errorLog(e, getClass().getName());
        }
        return resultDTO;
    }
}
