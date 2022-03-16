package com.example.demo.service;

import com.example.demo.mapper.DemoMapper;
import com.example.demo.model.DemoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DemoService {


    @Autowired
    private DemoMapper demoMapper;

    public List<Integer> getList(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        return list;
    }

    public List<DemoModel> queryData(){
        return demoMapper.queryData();
    }

    public List<DemoModel> findData(int id){
        return demoMapper.findData(id);
    }
}
