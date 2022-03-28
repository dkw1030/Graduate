package com.example.demo.controller;

import com.example.demo.model.Constant.Switcher;
import com.example.demo.model.DTO.SidePanelStatusDTO;
import com.example.demo.service.lower.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
public class InfoSellerController {

    @Autowired
    private AccountService accountService;



    @RequestMapping("/inputSeller")
    public String loginLayout(Model model){
        model.addAttribute("fileName", "Order.xlsx");

        SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
        sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.INFO_SELLER_ID);
        sidePanelStatusDTO.setCurSubMenu(Switcher.MenuSwitcher.INFO_SELLER_INPUT_INFO_ID);

        model.addAttribute("sidePanel", sidePanelStatusDTO);

        return "infoSeller/sellerUpload";
    }

    //单一文件上传
    @RequestMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model){
        String msg="";
        try {
            if(file.isEmpty()){
                model.addAttribute("msg","上传失败，请选择文件！");
                return "account/login";
            }
            String filename = file.getOriginalFilename();
            String filePath = "D:\\Project\\FileArea\\path2\\";

            //避免文件重复覆盖
            String uuid= UUID.randomUUID().toString().replaceAll("-", "");
            //时间戳分类文件
            String time = new SimpleDateFormat("YYYY-MM").format(new Date());

            String realPath = filePath + filename;
            File dest = new File(realPath);
//            LogUtil.log("infoSellerController uploadFile", realPath);
            //检测是否存在目录，无，则创建
            if(!dest.getParentFile().exists()){
                dest.getParentFile().mkdirs();//新建文件夹 多级目录
            }

            file.transferTo(dest);//文件写入

        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("msg","文件上传成功！");
        return "account/login";

    }

    /**
     * 文件下载
     * @param fileName
     */
    @ResponseBody
    @RequestMapping("/download/{fileName}")
    public void downloadFile(@PathVariable("fileName")String fileName, HttpServletResponse response) throws Exception {
        String realPath = "D:\\Project\\FileArea\\path1";
        // 1.去指定目录读取文件
        File file = new File(realPath, fileName);
        // 2.将文件读取为文件输入流
        FileInputStream is = new FileInputStream(file);
        // 2.1 获取响应流之前  一定要设置以附件形式下载
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        // 3.获取响应输出流
        ServletOutputStream os = response.getOutputStream();

        FileCopyUtils.copy(is,os);
    }



}
