package com.example.demo.model.Model;

import com.example.demo.model.Model.resultType.CompanyInfo;
import lombok.Data;

@Data
public class CompanyOverView {

    private String companyId;
    private String companyName;
    private String companyDescription;
    private String userName;
    private int count;
    private int valid;
}
