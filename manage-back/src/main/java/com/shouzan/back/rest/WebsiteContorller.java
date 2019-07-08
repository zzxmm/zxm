package com.shouzan.back.rest;


import com.github.pagehelper.PageHelper;
import com.shouzan.back.biz.WebsiteCustomerBiz;
import com.shouzan.back.biz.WebsiteTextBiz;
import com.shouzan.back.biz.impl.WebsiteCustomerBizImp;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.Bank;
import com.shouzan.back.entity.website.WebsiteCustomer;
import com.shouzan.back.entity.website.WebsiteText;
import com.shouzan.back.util.FileUpLoadUtil;
import com.shouzan.back.util.ImageUtils;
import com.shouzan.back.util.WeChatCoupon;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;
import com.shouzan.common.rest.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;


/**
 * @ClassName: com.shouzan.back.rest.WebsiteContorller
 * @Author: man.z
 * @Date: 2019-05-08 18:01
 * @Description: 官网运营系统-回访合作商/合作商申请
 */
@Controller
@RequestMapping("/websiteCustomer")
@Validated
@Slf4j
public class WebsiteContorller extends BaseController<WebsiteCustomerBizImp, WebsiteCustomer> {

    @Autowired
    private WebsiteCustomerBiz websiteCustomerBiz;

    @Autowired
    private SystemUserController systemUserController;

    /**
     * @Description: 分页查询回访客户

     * @[param] [pageSize, current, search]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Bank>
     * @author:  man.z
     * @date:  2019-05-08 18:24
     */
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<WebsiteCustomer> page(@RequestParam(defaultValue = "10") int pageSize , @RequestParam(defaultValue = "1") int current, SearchSatisfy search){

        try {
            log.info("request : 回访客户信息分页列表 , 每页显示[{}] , 当前显示[{}] . parameter : [{}]",pageSize,current,search);
            int count = websiteCustomerBiz.queryWebsiteCustomerListCount(search);
            PageHelper.startPage(current,pageSize,false);
            List<WebsiteCustomer> list = websiteCustomerBiz.queryWebsiteCustomerList(search);
            return  new ObjectRestResponse<WebsiteCustomer>().total(count).rows(list).rel(Response.SUCCESS);
        } catch (Exception e) {
            log.error("回访客户分页查询异常 :  异常信息 : [{}]" , e);
            return new ObjectRestResponse<WebsiteCustomer>().rel(Response.FAILURE).msg("回访客户分页查询异常 : 异常信息 : "+e.getMessage());
        }
    }

    /**
     * @Description: 添加合作商信息

     * @[param] [entity]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.website.WebsiteCustomer>
     * @author:  man.z
     * @date:  2019-05-08 18:34
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public  ObjectRestResponse<WebsiteCustomer> addInfo(@Valid WebsiteCustomer entity){

        try {
            log.info("request : 添加合作商信息 . parameter : 合作商信息[{}]",entity);
            return websiteCustomerBiz.insertInfo(entity);
        } catch (Exception e) {
            log.error("添加合作商异常 :  异常信息 : [{}]" , e);
            return new ObjectRestResponse<WebsiteCustomer>().rel(Response.FAILURE).msg("合作商信息添加异常 : 异常信息 : "+e.getMessage());
        }


    }

    /**
     * @Description: 修改回访状态

     * @[param] [id, state]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.website.WebsiteCustomer>
     * @author:  man.z
     * @date:  2019-05-09 14:22
     */
    @RequestMapping(value ="/update",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<WebsiteCustomer> updateState(@NotNull(message = "选择合作商") Integer id, @NotNull(message = "选择修改状态") Integer state){

        try {
            Integer userId = systemUserController.getUserId();
            log.info("request : 修改回访状态 . parameter : 合作商ID[{}] , 修改状态[{}]",id,state);
            return websiteCustomerBiz.updateWebsiteBystate(id,state,userId);
        } catch (Exception e) {
            log.error("回访状态修改异常 :  异常信息 : [{}]" , e);
            return new ObjectRestResponse<Bank>().rel(Response.FAILURE).msg("回访状态修改异常 : 异常信息 : "+e.getMessage());
        }

    }
}

/**
 * @Author: man.z
 * @Date: 2019-05-09 14:40
 *
 * @Description: 官网运营系统-案例
 */
@Controller
@RequestMapping("/websiteText")
@Validated
@Slf4j
class WebsiteTextController{

    @Autowired
    private WebsiteTextBiz websiteTextBiz;

    @Autowired
    private SystemUserController systemUserController;

    /**
     * @Description: 案例添加

     * @[param] [entity]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.website.WebsiteText>
     * @author:  man.z
     * @date:  2019-05-09 15:10
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public  ObjectRestResponse <WebsiteText> addInfo(@Valid @RequestBody WebsiteText entity){

        try {
            Integer userId = systemUserController.getUserId();
            entity.setCreatorId(userId);
            log.info("request : 添加案例信息 . parameter : 案例信息[{}]",entity);
            return websiteTextBiz.insertWebsiteTextInfo(entity);
        } catch (Exception e) {
            log.error("添加案例异常 : 异常信息 : [{}]" , e);
            return new ObjectRestResponse<WebsiteCustomer>().rel(Response.FAILURE).msg("案例信息添加异常 : 异常信息 : "+e.getMessage());
        }

    }

    /**
     * @Description: 分页显示案例信息

     * @[param] [pageSize, current, search]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.website.WebsiteCustomer>
     * @author:  man.z
     * @date:  2019-05-09 15:25
     */
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<WebsiteCustomer> page(@RequestParam(defaultValue = "12") int pageSize , @RequestParam(defaultValue = "1") int current,
                                                    SearchSatisfy search){
        try {
            log.info("request : 案例信息分页列表 , 每页显示[{}] , 当前显示[{}] . parameter : [{}]",pageSize,current,search);
            int count = websiteTextBiz.queryPageCount(search);
            List<WebsiteText> list = websiteTextBiz.queryPageList(search,pageSize,current);
            return  new ObjectRestResponse<WebsiteCustomer>().total(count).rows(list).rel(Response.SUCCESS);
        } catch (Exception e) {
            log.error("案例分页查询异常 :  异常信息 : [{}]" , e);
            return new ObjectRestResponse<WebsiteCustomer>().rel(Response.FAILURE).msg("案例分页查询异常 : 异常信息 : "+e.getMessage());
        }

    }

   /**
    * @Description: 修改案例信息

    * @[param] [entity]
    * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.website.WebsiteText>
    * @author:  man.z
    * @date:  2019-05-09 15:59
    */
    @RequestMapping(value ="/update",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<WebsiteText> upadteWebsiteText(@Valid @RequestBody WebsiteText entity) {

        try {
            Integer userId = systemUserController.getUserId();
            entity.setLastEditId(userId);
            log.info("request : 修改案例信息 . parameter : 案例信息[{}]", entity);
            return websiteTextBiz.updateWebsiteByIdInfo(entity);
        } catch (Exception e){
        log.error("添加案例异常 :  异常信息 : [{}]" , e);
        return new ObjectRestResponse<WebsiteText>().rel(Response.FAILURE).msg("案例信息修改异常 : 异常信息 : " + e.getMessage());

        }

    }

    /**
     * @Description: 根据id查询案例详情

     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.website.WebsiteText>
     * @author:  man.z
     * @date:  2019-05-09 16:19
     */
    @RequestMapping(value = "/selectById/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<WebsiteText> selectWebsiteById (@PathVariable Integer id){

        try{
            log.info("request : 查询宣案例信息 . parameter : 案例ID[{}]",id);
            return websiteTextBiz.selectWebsiteByIdInfo(id);
        }catch (Exception e){
            log.error("案例查询异常 :  异常信息 : [{}]" , e);
            return new ObjectRestResponse<WebsiteText>().rel(Response.FAILURE).msg("案例查询异常 : 异常信息 : "+e.getMessage());
        }

    }

    /**
     * @Description: 根据id删除案例

     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.website.WebsiteText>
     * @author:  man.z
     * @date:  2019-05-16 11:30
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<WebsiteText> deleteWebsite (@NotNull(message = "id不能为空") Integer id){

        try {
            log.info("request : 删除案例信息 . parameter ： 案例ID[{}]",id);
            return  websiteTextBiz.deleteWebsiteByIdInfo(id);
        }catch (Exception e){
            log.error("案例删除异常 :  异常信息 : [{}]" , e);
            return  new ObjectRestResponse<WebsiteText>().rel(Response.FAILURE).msg("案例删除异常 ：异常信息 ："+e.getMessage());
        }
    }
}

/**
 * @Author: man.z
 * @Date: 2019-05-13 14:07
 *
 * @Description: 图片上传和多倍图处理
 */
@Controller
@RequestMapping("/fileUpLoad")
@Slf4j
@Validated
class WebsiteFileUpLoadController{

    private final static String PATH = "shouzan-static/common_static/images/common/";
    @Value("${private_path}")
    private  String PRIVATE_PATH;
    @Value("${private_cospath}")
    private  String PRIVATE_COS;

    /**
     * @Description: 图片上传及缩倍处理

     * @[param] [file]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  man.z
     * @date:  2019-05-13 14:48
     */
    @RequestMapping(value = "/upLoad",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse upLoadFile(@RequestParam(value = "file" , required = false) MultipartFile file){

        //获取文件名
        String fileName = FileUpLoadUtil.getFileName(file.getOriginalFilename());
        //上传图片到服务器
        WeChatCoupon.upload(file,PRIVATE_PATH,fileName);
        //获取文件全路径
        String fullPathName = FileUpLoadUtil.getFullPathName(PRIVATE_PATH, fileName);

        // 上传@1到COS服务器
        ObjectRestResponse response = getImgRestResponse(fileName, fullPathName);
        if(!response.getRel()){
            return response;
        }

        //@2
        ImageUtils imageUtils = null;
        try {
            imageUtils = new ImageUtils(fullPathName,"@2");
            imageUtils.zoomByScale(0.5);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String imgName = FileUpLoadUtil.getImgName(fileName, "@2");
        String pathName = FileUpLoadUtil.getImgName(fullPathName, "@2");
        ObjectRestResponse response2 = getImgRestResponse(imgName, pathName);
        if(!response2.getRel()){
            return response.result(PATH+fileName);
        }

        //@3
        try {
            imageUtils = new ImageUtils(fullPathName,"@3");
            imageUtils.zoomByScale(0.33);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String imgName3 = FileUpLoadUtil.getImgName(fileName, "@3");
        String pathName3 = FileUpLoadUtil.getImgName(fullPathName, "@3");
        ObjectRestResponse response3 = getImgRestResponse(imgName3, pathName3);
        if(!response3.getRel()){
            return response.result(PATH+fileName);
        }

        //删除 临时文件
        FileUpLoadUtil.deleteFile(PRIVATE_PATH , fileName);
        FileUpLoadUtil.deleteFile(PRIVATE_PATH , imgName);
        FileUpLoadUtil.deleteFile(PRIVATE_PATH , imgName3);
        return response.result(PATH+fileName);
    }

    // 上传路径
    /**
     * @Description: 上传路径

     * @[param] [fileName, fullPathName]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  man.z
     * @date:  2019-05-16 11:03
     */
    @RequestMapping(value = "/getImgNa",method = RequestMethod.POST)
    @ResponseBody
    private ObjectRestResponse getImgRestResponse(String fileName, String fullPathName) {
        boolean object = FileUpLoadUtil.HttpPostCOS(fullPathName,fileName);
        if(object){
            return new ObjectRestResponse().rel(Response.SUCCESS);
        }else{
            return new ObjectRestResponse().rel(Response.FAILURE).msg("上传失败");
        }
    }

}