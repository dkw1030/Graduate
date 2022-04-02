package com.example.demo.service.lower;

import com.example.demo.mapper.InfoSellerMapper;
import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.Model.CompanyOverView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyBasicService {
    @Autowired
    InfoSellerMapper infoSellerMapper;

    public ResultDTO<List<CompanyOverView>> getAllCompany() throws Exception{
        ResultDTO<List<CompanyOverView>> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        System.out.println();
        List<CompanyOverView> companyOverViewList = infoSellerMapper.getAllCompanyOverViewList();
//        List<CompanyOverView> companyOverViewList = Arrays.asList();

        resultDTO.setData(companyOverViewList);
        resultDTO.setCode(0);
        return resultDTO;

    }

    public ResultDTO<List<CompanyOverView>> getCompany(String companyId, String companyName) throws Exception{
        ResultDTO<List<CompanyOverView>> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        System.out.println();
        List<CompanyOverView> companyOverViewList = infoSellerMapper.getCompanyOverViewList(companyId, companyName);
//        List<CompanyOverView> companyOverViewList = Arrays.asList();

        resultDTO.setData(companyOverViewList);
        resultDTO.setCode(0);
        return resultDTO;

    }


}
