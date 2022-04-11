package com.example.demo.controller;

import com.example.demo.model.Constant.Switcher;
import com.example.demo.model.DTO.FrameworkDTO;
import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.DTO.SidePanelStatusDTO;
import com.example.demo.model.Model.CompanyDetail;
import com.example.demo.model.Model.User;
import com.example.demo.service.Upper.AccountService;
import com.example.demo.service.Upper.InfoSellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private InfoSellerService infoSellerService;

    @RequestMapping("")
    public String loginLayout(Model model){
        return "account/login";
    }

    @RequestMapping("/login")
    public String login(@RequestParam("userId") long id,
                        @RequestParam("password") String password,
                        Model model){
        ResultDTO<User> userResult = accountService.login(id, password);
        int code = userResult.getCode();
        if(code > 0){
            String errorText;
            if(code == 1){
                errorText = "账号不存在";
            }else if(code == 2){
                errorText = "账号或密码不正确";
            }else{
                errorText = "unknown error code = " + code;
            }
            model.addAttribute("errorText", errorText);
            return "account/login";
        } else if(code == 0){
            SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
            sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.HOME_ID);
            User user = userResult.getData();
            sidePanelStatusDTO.setUserId(id+"");
            if(user.getUserRole() == 0){
                sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
            }else {
                sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.SellerSidePanel);
            }
            model.addAttribute("sidePanel", sidePanelStatusDTO);
            model.addAttribute("user", user);
            return "main/home";
        } else {
            return "error/404";
        }
    }

    @RequestMapping("/signUp/{type}")
    public String signUp(@PathVariable("type")String type, Model model){

        model.addAttribute("companyId", "0");
        model.addAttribute("departmentId", "0");
        model.addAttribute("type", type);
        return "account/signUp";
    }

//    @RequestMapping("/signUp/{companyId}/{type}")
//    public String signUp(@PathVariable("type")String type,@PathVariable("companyId")String companyId, Model model){
//
//        model.addAttribute("companyId", companyId);
//        model.addAttribute("departmentId", "0");
//        model.addAttribute("type", type);
//        return "account/signUp";
//    }

    @RequestMapping("/signUpAction/{type}")
    public String signUpAction(@PathVariable("type")String type,
                         @RequestParam("name") String name,
                               @RequestParam("userId") String userId,
                         @RequestParam("password") String password,
                         @RequestParam("companyId") String companyId,
                         @RequestParam("departmentId") String departmentId,
                         Model model){
        int role = Integer.parseInt(type);
        ResultDTO<String> userResult = accountService.signUp(name, role, companyId, departmentId, password);
        int code = userResult.getCode();
        if(code<0){
            return "error/404";
        }
        if(type.equals("0")){
            model.addAttribute("msg", userResult.getData());
            model.addAttribute("type", type);
            return "account/signUp";
        }else{
            ResultDTO<User> userResultDTO = accountService.getUserById(userId);
            ResultDTO<CompanyDetail> companyResult = infoSellerService.getCompanyDetailByCompanyId(companyId);
            model.addAttribute("user", userResultDTO.getData());
            model.addAttribute("company", companyResult.getData());

            return "redirect:/addUser/"+companyId+"/"+userId;
        }

    }


    @RequestMapping("/userInfo/{userId}/{type}")
    public String userInfo(@PathVariable("userId") String userId,
                           @PathVariable("type") String type,
                           Model model){
        ResultDTO<User> userInfo = accountService.getUserInfo(userId);

        if(userInfo.getCode()<0){
            return "error/404";
        }
        model.addAttribute("user", userInfo.getData());
        model.addAttribute("type", type);

        return "account/userInfo";
    }

    @RequestMapping("/userInfoChange/{userId}")
    public String userInfoChange(@PathVariable("userId") String userId,
                                 Model model){
        ResultDTO<User> userInfo = accountService.getUserInfo(userId);

        if(userInfo.getCode()<0){
            return "error/404";
        }
        model.addAttribute("user", userInfo.getData());

        return "account/userInfoChange";
    }

    @RequestMapping("/changeUserInfo/{userId}")
    public String ChangeAction(@PathVariable("userId") String userId,
                               @RequestParam("email")String email,
                               @RequestParam("name")String name,
                               @RequestParam("sex")String sex,
                               @RequestParam("phone")int phone,
                               Model model){
//        System.out.println("0");
        User user = new User();
        user.setUserId(userId);
        user.setUserName(name);
        user.setPhone(phone);
        user.setSex(Integer.parseInt(sex));
//        user.setSex(1);
        user.setEmail(email);
        ResultDTO<String> change = accountService.change(user);
        if(change.getCode()<0){
            return "error/404";
        }
        ResultDTO<User> userInfo = accountService.getUserInfo(userId);

        if(userInfo.getCode()<0){
            return "error/404";
        }
        model.addAttribute("user", userInfo.getData());
        model.addAttribute("type", "0");

        return "account/userInfo";
    }
}
