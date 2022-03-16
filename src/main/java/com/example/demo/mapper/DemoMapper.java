package com.example.demo.mapper;

import com.example.demo.model.DemoModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DemoMapper {
    List<DemoModel> queryData();

    List<DemoModel> findData(int id);

    int addData(DemoModel demoModel);

    int deleteData(int id);

    int changeData(int oldid,int newid);
}
