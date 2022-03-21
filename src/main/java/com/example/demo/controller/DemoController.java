package com.example.demo.controller;

import com.example.demo.model.DemoModel;
import com.example.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Controller
public class DemoController {
    @Autowired
    DataSource dataSource;


    @Autowired
    private DemoService demoService;

    /**
     * 最初级的测试，测试controller层是否能返回页面
     * @return
     */
    @RequestMapping("/test1")
    public String index() {
        return "index1";
    }

    /**
     * 进阶测试，测试controller层与service层链接
     * @param model
     * @return
     */
    @RequestMapping("/test2")
    public String data(Model model) {
        List<Integer> list = demoService.getList();
        model.addAttribute("data",list);
        return "/data/Demo/data";
    }


    /**
     * 进阶测试，测试数据库链接是否成功
     * @param model
     * @return
     * @throws SQLException
     */
    @RequestMapping("/test3")
    public String dataBase(Model model) throws SQLException {
        System.out.println(dataSource.getClass());
        System.out.println(dataSource.getConnection());
        return "index1";
    }


    /**
     * 从数据库取数据之后直接展示
     * @param model
     * @return
     */
    @RequestMapping("/test4")
    public String queryData(Model model){
        List<DemoModel> list = demoService.queryData();
        model.addAttribute("data", list);
        return "data/Demo/queryData";
    }

    /**
     * 有入参的数据展示
     * @param model
     * @return
     */
    @RequestMapping("/test5")
    public String findData(Model model){
        List<DemoModel> list = demoService.findData(1);
        model.addAttribute("data", list);
        return "data/Demo/queryData";
    }

    /**
     * 通过href传递参数
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/test6/{id}")
    public String pathVirable(@PathVariable("id")Integer id, Model model){
        model.addAttribute("data", id);
        return "data/Demo/pathVirable";
    }

    @RequestMapping("/test7/")
    public String login(Model model){
        return "login";
    }
}
