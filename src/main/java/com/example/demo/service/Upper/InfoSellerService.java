package com.example.demo.service.Upper;

import com.example.demo.model.Constant.Switcher;
import com.example.demo.model.DTO.FrameworkDTO;
import com.example.demo.model.DTO.Result.ResultDTO;
import com.example.demo.model.DTO.SellerListDTO;
import com.example.demo.model.Model.CompanyDetail;
import com.example.demo.model.Model.CompanyOverView;
import com.example.demo.model.Model.User;
import com.example.demo.model.Model.resultType.DepartmentInfo;
import com.example.demo.service.lower.AccountBasicService;
import com.example.demo.service.lower.InfoSellerBasicService;
import com.example.demo.utils.FileUtil;
import com.example.demo.utils.LogUtil;
import com.example.demo.utils.TimeUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class InfoSellerService {
    @Autowired
    InfoSellerBasicService infoSellerBasicService;


    @Autowired
    private AccountBasicService accountBasicService;

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
            LogUtil.log(getClass().getName(), "generate data success\n" + FileUtil.outData(data));

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
            LogUtil.errorLog(e, getClass().getName(), code);
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

    public ResultDTO<SellerListDTO> sellerListPage(String userId){
        ResultDTO<SellerListDTO> resultDTO = new ResultDTO<>();
        SellerListDTO sellerListDTO = new SellerListDTO();
        resultDTO.setCode(-1);
        try{
            ResultDTO<User> userResultDTO = accountBasicService.getUserById(userId);
            ResultDTO<List<CompanyOverView>> companyResultDTO = infoSellerBasicService.getAllCompany();
            sellerListDTO.setUser(userResultDTO.getData());
            sellerListDTO.setCompanyOverViewList(companyResultDTO.getData());
            resultDTO.setData(sellerListDTO);
        }catch (Exception e){
            LogUtil.errorLog(e, getClass().getName());
        }
        resultDTO.setCode(0);
        return resultDTO;
    }

    public ResultDTO<SellerListDTO> searchSellerListPage(String userId,String companyId, String companyName){
        ResultDTO<SellerListDTO> resultDTO = new ResultDTO<>();
        SellerListDTO sellerListDTO = new SellerListDTO();
        resultDTO.setCode(-1);
        try{
            ResultDTO<User> userResultDTO = accountBasicService.getUserById(userId);
            ResultDTO<List<CompanyOverView>> companyResultDTO = infoSellerBasicService.getCompany(companyId,companyName);
            sellerListDTO.setUser(userResultDTO.getData());
            sellerListDTO.setCompanyOverViewList(companyResultDTO.getData());
            resultDTO.setData(sellerListDTO);
        }catch (Exception e){
            LogUtil.errorLog(e, getClass().getName());
        }
        resultDTO.setCode(0);
        return resultDTO;
    }

    public ResultDTO<String> addCount(int type, String orderId){
        ResultDTO<String> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        try{
            resultDTO = infoSellerBasicService.addCount(type, orderId);
        }catch (Exception e){
            LogUtil.errorLog(e,getClass().getName());
        }
        return resultDTO;
    }

    public ResultDTO<FrameworkDTO> getCompanyDetail(String userId){
        ResultDTO<FrameworkDTO> resultDTO = new ResultDTO<>();
        FrameworkDTO frameworkDTO = new FrameworkDTO();
        resultDTO.setData(frameworkDTO);
        resultDTO.setCode(-1);
        try {
            ResultDTO<User> userById = accountBasicService.getUserById(userId);
            User user = userById.getData();
            frameworkDTO.setUser(user);
            ResultDTO<CompanyDetail> companyDetail = infoSellerBasicService.getCompanyDetail(user.getCompanyId());
            frameworkDTO.setCompanyDetail(companyDetail.getData());
            resultDTO.setCode(0);
        }catch (Exception e){
            LogUtil.errorLog(e, getClass().getName());
        }
        return resultDTO;
    }

    public ResultDTO<CompanyDetail> getCompanyDetailByCompanyId(String companyId){
        ResultDTO<CompanyDetail> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        try {
            resultDTO = infoSellerBasicService.getCompanyDetail(companyId);
        }catch (Exception e){
            LogUtil.errorLog(e, getClass().getName());
        }
        return resultDTO;
    }

    public ResultDTO<String> addDep(String companyId, String departmentName){
        ResultDTO<String> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        DepartmentInfo departmentInfo = new DepartmentInfo();
        departmentInfo.setDepartmentName(departmentName);
        departmentInfo.setDepartmentId(TimeUtil.getTimeStamp());
        departmentInfo.setCompanyId(companyId);
        try{
            resultDTO = infoSellerBasicService.addDep(departmentInfo);
        }catch (Exception e){
            LogUtil.errorLog(e,getClass().getName());
        }
        return resultDTO;
    }

    public ResultDTO<String> deleteDep(String companyId, String departmentId){
        ResultDTO<String> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        DepartmentInfo departmentInfo = new DepartmentInfo();
        departmentInfo.setDepartmentId(departmentId);
        departmentInfo.setCompanyId(companyId);
        try{
            resultDTO = infoSellerBasicService.deleteDep(departmentInfo);
        }catch (Exception e){
            LogUtil.errorLog(e,getClass().getName());
        }
        return resultDTO;
    }

    public ResultDTO<String> validCompany(String companyId, int type){
        ResultDTO<String> resultDTO = new ResultDTO<>();
        resultDTO.setCode(-1);
        try{
            infoSellerBasicService.validCompany(companyId, type);
            resultDTO.setCode(0);
        }catch (Exception e){
            LogUtil.errorLog(e,getClass().getName());
        }
        return resultDTO;
    }
}
