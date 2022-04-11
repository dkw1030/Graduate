package com.example.demo.model.DTO;

import com.example.demo.model.Model.CompanyDetail;
import com.example.demo.model.Model.resultType.OrderPercentage;
import lombok.Data;

@Data
public class CompanyDetailDTO {

    private OrderPercentage orderPercentage;
    private CompanyDetail companyDetail;
}
