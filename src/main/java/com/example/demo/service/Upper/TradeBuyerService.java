package com.example.demo.service.Upper;

import com.example.demo.model.Constant.Switcher;
import com.example.demo.model.DTO.OrderSearchDTO;
import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.DTO.TradeDTO;
import com.example.demo.model.Model.Order;
import com.example.demo.model.Model.User;
import com.example.demo.model.Model.resultType.OrderInfo;
import com.example.demo.service.lower.AccountBasicService;
import com.example.demo.service.lower.OrderBasicService;
import com.example.demo.utils.FileUtil;
import com.example.demo.utils.LogUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class TradeBuyerService {
    @Autowired
    AccountBasicService accountBasicService;

    @Autowired
    OrderBasicService orderBasicService;

//    public ResultDTO<TradeDTO> TradeBuyerPage(String userId){
//        ResultDTO<TradeDTO> resultDTO = new ResultDTO<>();
//        TradeDTO tradeDTO = new TradeDTO();
//        resultDTO.setData(tradeDTO);
//        resultDTO.setCode(-1);
//        try{
//            ResultDTO<User> userResultDTO = accountBasicService.getUserById(userId);
//            tradeDTO.setUser(userResultDTO.getData());
//            ResultDTO<List<OrderInfo>> orderResult = orderBasicService.getOrderByUser(userResultDTO.getData());
//            tradeDTO.setOrders(orderResult.getData());
//        }catch(Exception e){
//            LogUtil.errorLog(e, getClass().getName());
//        }
//        resultDTO.setCode(0);
//        return resultDTO;
//    }

    public ResultDTO<User> UploadOrderPage(String userId){
        ResultDTO<User> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        try{
            ResultDTO<User> userResultDTO = accountBasicService.getUserById(userId);
            resultDTO.setData(userResultDTO.getData());
        }catch(Exception e){
            LogUtil.errorLog(e, getClass().getName());
        }
        resultDTO.setCode(0);
        return resultDTO;
    }

    public ResultDTO<String> uploadOrderInfo(MultipartFile multipartFile){
        ResultDTO<String> resultDTO = new ResultDTO<>();
        int code = -1;
        resultDTO.setCode(-1);
        try {
            if(multipartFile.isEmpty()){
                resultDTO.setData("?????????????????????????????????");
                resultDTO.setCode(code);
                return resultDTO;
            }
            File file = new File(Switcher.FilePathSwitcher.clout_file_path_order + multipartFile.getOriginalFilename());
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
            code = -2;
            //???????????????
            List<List<String>> data = FileUtil.ReadExcel(file.getPath());
            LogUtil.log(getClass().getName(), "generate data success\n" + FileUtil.outData(data));

            //????????????????????????
            ResultDTO<String> checkResult = checkOrderData(data);
            if(checkResult.getCode() < 0){
                code = -3;
                resultDTO.setData(checkResult.getData());
                return resultDTO;
            }
            //???data????????????
            ResultDTO<String> insertResult = orderBasicService.uploadOrder(data);
        } catch (Exception e) {
            e.printStackTrace();
            resultDTO.setData("??????????????????");
            LogUtil.errorLog(e, getClass().getName(), code);
        }
        resultDTO.setCode(0);
        resultDTO.setData("????????????");
        return resultDTO;
    }

    public ResultDTO<String> changeOrderInfo(MultipartFile multipartFile, String orderId){
        ResultDTO<String> resultDTO = new ResultDTO<>();
        int code = -1;
        resultDTO.setCode(-1);
        try {
            if(multipartFile.isEmpty()){
                resultDTO.setData("?????????????????????????????????");
                resultDTO.setCode(code);
                return resultDTO;
            }
            File file = new File(Switcher.FilePathSwitcher.clout_file_path_order + multipartFile.getOriginalFilename());
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
            code = -2;
            //???????????????
            List<List<String>> data = FileUtil.ReadExcel(file.getPath());
            LogUtil.log(getClass().getName(), "generate data success\n" + FileUtil.outData(data));

            //????????????????????????
            ResultDTO<String> checkResult = checkOrderData(data, orderId);
            if(checkResult.getCode() < 0){
                code = 1;
                resultDTO.setCode(1);
                resultDTO.setData(checkResult.getData());
                return resultDTO;
            }
            //???data????????????
            ResultDTO<String> insertResult = orderBasicService.changeOrder(data);
            resultDTO.setCode(0);
            resultDTO.setData("????????????");
        } catch (Exception e) {
            e.printStackTrace();
            resultDTO.setData("??????????????????");
            LogUtil.errorLog(e, getClass().getName(), code);
        }

        return resultDTO;
    }

    public ResultDTO<String> checkOrderData(List<List<String>> data){
        ResultDTO<String> resultDTO = new ResultDTO<>();
        resultDTO.setCode(0);
        resultDTO.setData("??????");
        return resultDTO;
    }

    public ResultDTO<String> checkOrderData(List<List<String>> data, String orderId){
        ResultDTO<String> resultDTO = checkOrderData(data);
        if(resultDTO.getCode()<0){
            return resultDTO;
        }
        if (!data.get(1).get(0).equals(orderId)){
            resultDTO.setCode(-1);
            resultDTO.setData("order id?????????");
            return resultDTO;
        }
        resultDTO.setData("??????");
        return resultDTO;
    }
}
