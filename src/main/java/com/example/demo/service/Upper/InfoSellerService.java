package com.example.demo.service.Upper;

import com.example.demo.model.Constant.Switcher;
import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.Model.Company;
import com.example.demo.model.Model.resultType.CompanyInfo;
import com.example.demo.service.lower.InfoSellerBasicService;
import com.example.demo.utils.FileUtil;
import com.example.demo.utils.LogUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class InfoSellerService {
    private final String location = "Service Upper InfoSellerService ";

    @Autowired
    InfoSellerBasicService infoSellerBasicService;

    public ResultDTO<String> uploadSellerInfo(MultipartFile multipartFile){
        ResultDTO<String> resultDTO = new ResultDTO<>();
        int code = -1;
        resultDTO.setCode(-1);
        try {
            if(multipartFile.isEmpty()){
                resultDTO.setData("上传失败，请选择文件！");
                resultDTO.setCode(code);
                return resultDTO;
            }
            File file = new File(Switcher.FilePathSwitcher.cloud_file_path_company + multipartFile.getOriginalFilename());
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
            code = -2;
            //文件转数据
            List<List<String>> data = FileUtil.ReadExcel(file.getPath());

            //核验数据是否有误
            ResultDTO<String> checkResult = checkCompanyData(data);
            if(checkResult.getCode() < 0){
                code = -3;
                resultDTO.setData(checkResult.getData());
                return resultDTO;
            }
            //对data进行处理
            ResultDTO<String> insertResult = infoSellerBasicService.uploadSeller(data);
        } catch (Exception e) {
            e.printStackTrace();
            resultDTO.setData("文件上传失败");
            LogUtil.errorLog(e, location + getClass().getName(), code);
        }
        resultDTO.setCode(0);
        resultDTO.setData("上传成功");
        return resultDTO;
    }

    public ResultDTO<String> checkCompanyData(List<List<String>> data){
        ResultDTO<String> resultDTO = new ResultDTO<>();
        resultDTO.setCode(0);
        resultDTO.setData("成功");
        return resultDTO;
    }
}
