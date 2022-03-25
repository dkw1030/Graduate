package com.example.demo.mapper;

import com.example.demo.model.DemoModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AccountMapper {
    String getPasswordById(int id);

}
