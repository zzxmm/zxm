package com.shouzan.back.rest;

import com.shouzan.back.biz.AdvertBiz;
import com.shouzan.back.biz.impl.AdvertBizImpl;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.Advert;
import com.shouzan.back.mapper.BankMapper;
import com.shouzan.back.util.ExcelUtil;
import com.shouzan.common.msg.ObjectRestResponse;
import com.shouzan.common.rest.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2019/1/28 15:09
 * @Description:  福利社
 */
@RequestMapping("/wel")
@Controller
@Slf4j
@Validated
public class WelfareController extends BaseController<AdvertBizImpl,Advert> {

    @Autowired
    private AdvertBiz advertBiz;

    @Autowired
    private BankMapper bankMapper;

    /**
     * @Description: (excel 导入 福利 列表)
     * @param file
     * @[param] [file]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/1/29 3:45 PM
     */
    @RequestMapping(value = "/importExcel" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse importExcel(MultipartFile file){
        String current= getCurrentUserId();
        HashMap<String, String> map = new HashMap<>();
        InputStream in = null;
        try {
            in = file.getInputStream();
        } catch (IOException e) {
            log.error("异常信息 : [{}]" , e);
            new ObjectRestResponse<>().rel(Response.FAILURE).msg("文件无法解析,请重新上传");
        }
        //读取excel中的内容
        try {
            List<Advert> excelList = ExcelUtil.getExcelList(in);
            ObjectRestResponse<Advert> response = validAdvertInfo(excelList);
            if(response.getRel()){
                return advertBiz.importExcelAdvertInfo(response.getRows(),current);
            }else{
                return response;
            }
        } catch (IOException e) {
            log.error("异常信息 : [{}]" , e);
        }
        return new ObjectRestResponse<>().rel(Response.FAILURE).msg("文件解析失败 , 请重新上传文件");
    }

    /**
     * @Description: (效验数据集合)
     * @param excelList
     * @[param] [excelList]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Advert>
     * @author:  bin.yang
     * @date:  2019/1/29 3:46 PM
     */
    public ObjectRestResponse<Advert> validAdvertInfo(List<Advert> excelList){
        String id = bankMapper.findIdStringAll();
        for (Advert advert : excelList) {
            if(advert.getAdvertName() == null || "".equals(advert.getAdvertName())){
                return new ObjectRestResponse().rel(Response.FAILURE).msg("不允许存在 广告名称 为空的信息 ! ");
            }
            if(id.indexOf(advert.getBankId()+"") < 0){
                return new ObjectRestResponse().rel(Response.FAILURE).msg("不存在ID为 "+advert.getBankId()+" 的银行信息 !");
            }
//            if(advert.getAdvertUrl() == null || "".equals(advert.getAdvertUrl())){
//                return new ObjectRestResponse().rel(false).msg("不允许存在 广告外链 为空的信息 !");
//            }
            if(advert.getUpshelfTime() != null && advert.getDownshelfTime() != null){
               if(advert.getUpshelfTime().getTime() > advert.getDownshelfTime().getTime()){
                   return new ObjectRestResponse().rel(Response.FAILURE).msg(" 上架时间 不能晚于 下架时间 ! Info : "+advert.getAdvertName());
               }
               advert.getDownshelfTime().setHours(23);
            }else{
                return new ObjectRestResponse().rel(Response.FAILURE).msg(" 上下架时间 , 不能为空 ! ");
            }
        }
        return new ObjectRestResponse<Advert>().rel(Response.SUCCESS).rows(excelList);
    }
}
