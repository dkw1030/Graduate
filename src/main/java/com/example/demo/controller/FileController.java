package com.example.demo.controller;

import com.example.demo.model.Constant.Switcher;
import com.example.demo.model.DTO.DetailDTO;
import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.DTO.SidePanelStatusDTO;
import com.example.demo.model.Model.CompanyDetail;
import com.example.demo.model.Model.Order;
import com.example.demo.model.Model.OrderItem;
import com.example.demo.model.Model.User;
import com.example.demo.service.Upper.AccountService;
import com.example.demo.service.Upper.DetailService;
import com.example.demo.service.Upper.InfoSellerService;
import com.example.demo.service.Upper.TradeBuyerService;
import com.example.demo.utils.FileUtil;
import com.example.demo.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.example.demo.utils.TimeUtil.printTime;

@Controller
public class FileController {

    @Autowired
    private InfoSellerService infoSellerService;

    @Autowired
    private AccountService accountService;

    @Autowired
    DetailService detailService;

    @Autowired
    TradeBuyerService tradeBuyerService;



    //单一文件上传
    @RequestMapping("/uploadFileDemo")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model){
        SidePanelStatusDTO sidePanelStatusDTO = new SidePanelStatusDTO();
        sidePanelStatusDTO.setSidePanel(Switcher.MenuSwitcher.BuyerSidePanel);
        sidePanelStatusDTO.setCurMenu(Switcher.MenuSwitcher.INFO_SELLER_ID);
        sidePanelStatusDTO.setCurSubMenu(Switcher.MenuSwitcher.INFO_SELLER_UPLOAD_SELLER_ID);

        model.addAttribute("sidePanel", sidePanelStatusDTO);

        String msg="";
        try {
            if(file.isEmpty()){
                model.addAttribute("msg","上传失败，请选择文件！");
                return "infoSeller/uploadSeller";
            }
            String filename = file.getOriginalFilename();
            String filePath = "D:\\Project\\FileArea\\path2\\";


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
        return "infoSeller/uploadSeller";

    }

    /**
     * 文件下载
     * @param fileName
     */
    @ResponseBody
    @RequestMapping("/downloadDemo/{fileName}")
    public void downloadFileDemo(@PathVariable("fileName")String fileName, HttpServletResponse response) throws Exception {
        String realPath = "D:\\Project\\FileArea\\Graduate\\Company";
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


    @ResponseBody
    @RequestMapping("/download/{userId}/{orderId}")
    public void downloadFile(@PathVariable("userId")String userId,
                             @PathVariable("orderId")String orderId,
                             HttpServletResponse response) throws Exception {


        ResultDTO<DetailDTO> resultDTO = detailService.getOrderDetail(orderId,userId);
        //转data
        List<List<String>> data = genData(resultDTO.getData().getOrder());
        //输出excel
        String fileName= TimeUtil.getTimeStamp()+".xlsx";
        String realPath = Switcher.FilePathSwitcher.clout_file_path_order;
        String path = realPath+fileName;
        FileUtil.WriteExcel(data,path);
        //下载
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

    @ResponseBody
    @RequestMapping("/downloadCompany/{companyId}")
    public void downloadCompany(
                             @PathVariable("companyId")String companyId,
                             HttpServletResponse response) throws Exception {


        ResultDTO<CompanyDetail> resultDTO = infoSellerService.getCompanyDetailByCompanyId(companyId);
        //转data
        List<List<String>> data = genCompanyData(resultDTO.getData());

        //输出excel
        String fileName= TimeUtil.getTimeStamp()+".xlsx";
        String realPath = Switcher.FilePathSwitcher.clout_file_path_order;
        String path = realPath+fileName;
        FileUtil.WriteExcel(data,path);
        //下载
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

    public List<List<String>> genData(Order order){
        List<List<String>> data = new ArrayList<>();
        List<String> row1 = Arrays.asList("订单号","订单名称","订单状态","订单上传时间","订单确认时间","订单寄出时间","订单完结时间");
        List<String> row2 = Arrays.asList(order.getOrderInfo().getOrderId(),
                order.getOrderInfo().getOrderName(),
                order.getOrderInfo().getOrderStatus()+"",
                printTime(order.getOrderInfo().getOrderUploadTime()),
                printTime(order.getOrderInfo().getOrderConfirmTime()),
                printTime(order.getOrderInfo().getOrderDeliverTime()),
                printTime(order.getOrderInfo().getOrderFinishTime()));
        List<String> row3 = Arrays.asList("供应商","采购员","订单描述");
        List<String> row4 = Arrays.asList(order.getOrderInfo().getSellerId(),
                order.getOrderInfo().getBuyerId(),
                order.getOrderInfo().getOrderDescription());
        List<String> row5 = Arrays.asList("物料名","型号","数量","单价","进度（%）");
        data.add(row1);
        data.add(row2);
        data.add(row3);
        data.add(row4);
        data.add(row5);
        for (OrderItem item:
             order.getOrderItems()) {
            data.add(Arrays.asList(item.getItemName(),
                    item.getItemType(),
                    item.getNumber()+"",
                    item.getCost()+"",
                    item.getProcess()+""));
        }
        return data;
    }

    public List<List<String>> genCompanyData(CompanyDetail company){
        List<List<String>> data = new ArrayList<>();
        List<String> row1 = Arrays.asList("公司编号","公司名","公司简介");
        List<String> row2 = Arrays.asList(company.getCompanyInfo().getCompanyId(),
                company.getCompanyInfo().getCompanyName(),
                company.getCompanyInfo().getCompanyDescription());
        List<String> row3 = Arrays.asList("订单确认数","订单拒绝数","订单完成数","订单驳回数","订单首次确认率","订单首次签收率");
        List<String> row4 = Arrays.asList(company.getCompanyInfo().getOrderConfirmed()+"",
                company.getCompanyInfo().getOrderRejected()+"",
                company.getCompanyInfo().getOrderCompleted()+"",
                company.getCompanyInfo().getOrderFailed()+"",
                (company.getCompanyInfo().getOrderConfirmed()+0.5)*100/(company.getCompanyInfo().getOrderConfirmed()+company.getCompanyInfo().getOrderRejected()+0.5)+"%",
                (company.getCompanyInfo().getOrderCompleted()+0.5)*100/(company.getCompanyInfo().getOrderCompleted()+company.getCompanyInfo().getOrderFailed()+0.5)+"%");
        List<String> row5 = Arrays.asList("用户名","用户账号","职位","部门","编号\n");
        data.add(row1);
        data.add(row2);
        data.add(row3);
        data.add(row4);
        data.add(row5);

        for (User user :
                company.getAllUsers()) {
            String roleStr = "";
            switch (user.getUserRole()){
                case 1:
                    roleStr = "老板";
                    break;
                case 2:
                    roleStr = "主管";
                    break;
                case 3:
                    roleStr = "员工";
                    break;
            }
            String departmentName = "";
            if(user.getDepartmentName()!=null){
                departmentName = user.getDepartmentName();
            }
            data.add(Arrays.asList(user.getUserName(),
                    user.getUserId(),
                    roleStr,
                    departmentName,
                    user.getDepartmentId()));
        }


        return data;
    }

    public static void main(String[] args) {
        Date d = new Date();
        Date a = null;
        System.out.println(a.toString());
    }

}
