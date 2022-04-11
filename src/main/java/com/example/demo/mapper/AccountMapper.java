package com.example.demo.mapper;

import com.example.demo.model.DemoModel;
import com.example.demo.model.Model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AccountMapper {
    String getPasswordById(String id);

    User getUserById(String id);

    List<User> getEmployeeByCompanyId(String companyId);

    int change(User user);

    int changePassword(String userId, String password);


    int changeMonitorToEmployee(@Param("companyId")String companyId, @Param("departmentId")String departmentId);
    int changeEmployeeToMonitor(@Param("companyId")String companyId,
                                @Param("departmentId")String departmentId,
                                @Param("userId")String userId);

    int changeDep(@Param("companyId")String companyId,
                  @Param("departmentId")String departmentId,
                  @Param("userId")String userId);

    int signUpAccount(@Param("userId")String userId, @Param("password")String password);
    int signUpUsers(User user);
}
