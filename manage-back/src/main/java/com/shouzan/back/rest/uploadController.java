package com.shouzan.back.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.Bank;
import com.shouzan.back.entity.Merchants;
import com.shouzan.back.entity.Store;
import com.shouzan.back.mapper.MerchantsMapper;
import com.shouzan.back.util.CodeValid;
import com.shouzan.back.util.ExcelUtil;
import com.shouzan.common.msg.ObjectRestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/11/14 11:05
 * @Description:  初始数据导入
 */
@Controller
@RequestMapping("/upload")
@Validated
@Slf4j
public class uploadController {

    @Autowired
    private MerController merController;

    @Autowired
    private StoreController storeController;

    @Autowired
    private BankController bankController;

    @Autowired
    private MerchantsMapper merchantsMapper;

    @RequestMapping(value = "getKey" ,method = RequestMethod.GET)
    @ResponseBody
    public String getKey (){
        RestTemplate restTemplate = new RestTemplate();
        String template = restTemplate.getForObject(CodeValid.UPURL, String.class);
        return template;
    }

    /**
     * @Description: (初始化数据 批量导入)
     * @param upfile
     * @[param] [upfile]
     * @return com.shouzan.common.msg.ObjectRestResponse<java.lang.Object>
     * @author:  bin.yang
     * @date:  2018/11/29 5:19 PM
     */
    @RequestMapping(value = "/importExcel" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Object> importExcel(MultipartFile upfile) throws Exception {

        InputStream in = upfile.getInputStream();
        //读取excel中的内容
        ObjectRestResponse response = ExcelUtil.getList(in);
        int flag = 2;
        int count = 0;
        int err = 0;
        String names = "";
        if(response.getRel()){
            if(response.getMsg().indexOf("商户") >= 0){
                List<Merchants> rows = response.getRows();
                for (Merchants bean : rows) {
                    flag++;
                    ObjectRestResponse<Merchants> add = merController.add(bean, bean.getMerType() + "");
                    if(add.getRel()){
                        count++;
                    }else {
                        err++;
                        names += flag+"-"+bean.getMerName()+",";
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        log.error("异常信息 : [{}]" , e);
                    }
                }
            }else if(response.getMsg().indexOf("门店") >= 0){
                List<Store> rows = response.getRows();
                for (Store bean : rows) {
                    flag++;
                    int id = merchantsMapper.findIdByCenterId(bean.getMerCenterId());
                    bean.setMerId(id);
                    Store validation = Validation(bean);
                    ObjectRestResponse<Store> add = storeController.add(validation);
                    if(add.getRel()){
                        count++;
                    }else {
                        err++;
                        names += flag+"-"+bean.getStoreName()+",";
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }else if(response.getMsg().indexOf("银行") >= 0){
                List<Bank> rows = response.getRows();
                for (Bank bean : rows) {
                    flag++;
                    ObjectRestResponse<Bank> add = bankController.addInfo(bean);
                    if (add.getRel()) {
                        count++;
                    } else {
                        err++;
                        names += flag+"-"+bean.getBankName() + ",";
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            log.info("导入成功 , 成功添加 [{}] 条 , 添加失败 [{}] 条 , 添加失败信息 : [{}]",count,err,names);
            return new ObjectRestResponse<>().rel(Response.SUCCESS).msg(" 导入成功 , 成功添加 "+count+" 条数据 , 添加失败 "+err+" 条 , 失败信息 : ["+names+"]");
        }else{
            log.info("文件解析失败 ");
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(" 文件解析失败 , 请重新上传文件 ");

        }
    }

    /**
     * @Description: (日期转换及地标获取)
     * @param store
     * @[param] [store]
     * @return com.shouzan.back.entity.Store
     * @author:  bin.yang
     * @date:  2018/11/29 5:21 PM
     */
    public Store Validation(Store store){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String businessHours = store.getBusinessHours();
        String[] split = businessHours.split("-");
        String Hours = "";
        try {
            Date start = format.parse("2010-10-10 " + split[0]);
            Date end = format.parse("2010-10-10 " + split[1]);
            Hours = start.getTime()+"_"+end.getTime();
        } catch (ParseException e) {
            log.error("异常信息 : [{}]" , e);
        }
        store.setBusinessHours(Hours);
        String province= "";
        String city= "";
        String area= "";
        List<JSONObject> jsonObjects = JSON.parseArray(CodeValid.PROVINCE, JSONObject.class);
        for (JSONObject jsonObject : jsonObjects) {
            if(jsonObject.get("label").toString().indexOf(store.getProvince()) >= 0){
                province = jsonObject.get("value").toString();
            }
        }
        JSONObject jsonCity = JSON.parseObject(CodeValid.CITY);
        Object obcity = jsonCity.get(province);
        List<JSONObject> jsonObjectsCity = JSON.parseArray(obcity.toString(), JSONObject.class);
        for (JSONObject jsonObject : jsonObjectsCity) {
            if(jsonObject.get("label").toString().indexOf(store.getCity())  >= 0){
                city = jsonObject.get("value").toString();
            }
        }
        RestTemplate restTemplate = new RestTemplate();
        String forObject = restTemplate.getForObject(CodeValid.GAODE+store.getProvince()+store.getCity()+store.getArea(), String.class);
        JSONObject jsonObject = JSONObject.parseObject(forObject);
        if(Integer.parseInt(jsonObject.get("status").toString()) == 1){
            List<JSONObject> geocodes = JSON.parseArray(jsonObject.getString("geocodes"), JSONObject.class);
            JSONObject jsonObject1 = geocodes.get(0);
            area = jsonObject1.get("adcode").toString();
        }
        store.setGeocoding(province+","+city+","+area);
        return store;
    }
}
