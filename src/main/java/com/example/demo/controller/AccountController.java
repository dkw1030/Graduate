package com.example.demo.controller;

import com.example.demo.model.DTO.CheckAccountDTO;
import com.example.demo.model.DemoModel;
import com.example.demo.service.AccountService;
import com.example.demo.service.DemoService;
import com.example.demo.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.util.List;

@Controller
public class AccountController {

    @Autowired
    DataSource dataSource;


    @Autowired
    private AccountService accountService;

    @RequestMapping("/user/login")
    public String login(@RequestParam("userId") int id,
                        @RequestParam("password") String password,
                        Model model){
        try{
            CheckAccountDTO checkAccountDTO = accountService.login(id, password);
            model.addAttribute("data", checkAccountDTO);
            model.addAttribute("location1", 1);
            model.addAttribute("location2", 2);
            return checkAccountDTO.getNextPage();
        }catch(Exception e){
            LogUtil.errorLog(e);
            return "error/404";
        }
    }
}
