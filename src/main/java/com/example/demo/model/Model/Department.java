package com.example.demo.model.Model;

import com.example.demo.model.Model.resultType.DepartmentInfo;
import lombok.Data;

import java.util.List;
@Data
public class Department extends DepartmentInfo {
    private User monitor;

    private List<User> users;
}
