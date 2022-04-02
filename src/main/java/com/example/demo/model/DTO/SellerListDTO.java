package com.example.demo.model.DTO;

import com.example.demo.model.Model.CompanyOverView;
import com.example.demo.model.Model.User;
import lombok.Data;

import java.util.List;

@Data
public class SellerListDTO {
    User user;
    List<CompanyOverView> companyOverViewList;
}
