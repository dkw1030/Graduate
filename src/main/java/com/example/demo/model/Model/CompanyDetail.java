package com.example.demo.model.Model;

import com.example.demo.model.Model.resultType.CompanyInfo;
import lombok.Data;

import java.util.List;

@Data
public class CompanyDetail extends CompanyInfo {
    /**
     *
     */
    private List<Department> departments;
}
