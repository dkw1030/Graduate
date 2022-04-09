package com.example.demo.mapper;

import com.example.demo.model.DemoModel;
import com.example.demo.model.Model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AccountMapper {
    String getPasswordById(String id);

    User getUserById(String id);

    List<User> getEmployeeByCompanyId(String companyId);

    int change(String userId, String email, int phone);

    int changePassword(String userId, String password);
}
