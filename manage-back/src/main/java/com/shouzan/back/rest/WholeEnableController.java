package com.shouzan.back.rest;

import com.shouzan.back.biz.AchievementRuleBiz;
import com.shouzan.back.biz.ExtendBiz;
import com.shouzan.back.biz.IntegralRuleBiz;
import com.shouzan.back.constant.Response;
import com.shouzan.common.msg.ObjectRestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;

/**
 * @ClassName: com.shouzan.back.rest.WholeEnableController
 * @Author: bin.yang
 * @Date: 2019/5/13 17:49
 * @Description: TODO   全局系统上下线状态控制
 */
@Controller
@RequestMapping("/whole")
@Validated
@Slf4j
public class WholeEnableController {

    @Autowired
    private ExtendBiz extendBiz;

    @Autowired
    private IntegralRuleBiz integralRuleBiz;

    @Autowired
    private SystemUserController systemUserController;

    @Autowired
    private AchievementRuleBiz achievementRuleBiz;

    @RequestMapping(value = "/enable" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse updateEnable(@RequestParam(defaultValue = "0") Integer type , @NotNull(message = "选择操作状态") Integer status){
        try {
            Integer userId = systemUserController.getUserId();
            return extendBiz.updateEnableStatus(status,userId);
        } catch (Exception e) {
            log.error("分销系统操作异常 ! 异常信息 : "+e.getMessage());
            return new ObjectRestResponse().rel(Response.FAILURE).msg(Response.SYSTEM_TIP);
        }
    }

    @RequestMapping(value = "/findEnable" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse findEnable(){
        try {
            return extendBiz.findEnableStatus();
        } catch (Exception e) {
            log.error("分销状态查询异常 ! 异常信息 : "+e.getMessage());
            return new ObjectRestResponse().rel(Response.FAILURE).msg(Response.SYSTEM_TIP);
        }
    }

    /**
     * @Description: 积分全局控制

     * @[param] [type, status]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  man.z
     * @date:  2019-05-31 14:44
     */
    @RequestMapping(value = "/integralEnable",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse updateEnableIntegral(@RequestParam(defaultValue = "0") Integer type , @NotNull(message = "选择操作状态") Integer status){
        try {
            log.info("request : 积分状态修改 修改雷修 [{}] , 修改状态 [{}] . parameter [{}] : ", type, status);
            Integer userId = systemUserController.getUserId();
            return integralRuleBiz.updateIntegralEnableStatus(status,userId);
        } catch (Exception e){
            log.error(" 积分全局修改异常 : 异常信息 ",e );
            return new ObjectRestResponse().rel(Response.FAILURE).msg(Response.SYSTEM_TIP);
        }
    }
    /**
     * @Description: 查询启用状态

     * @[param] []
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  man.z
     * @date:  2019-05-31 16:13
     */
    @RequestMapping(value = "/findStatu" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse findStatu(){
        try {
            log.info("request : 查询积分状态 ");
            return integralRuleBiz.findIntegralEnableStatus();
        } catch (Exception e){
            log.error(" 查询积分状态异常 : 异常信息 ", e);
            return new ObjectRestResponse().rel(Response.FAILURE).msg(Response.SYSTEM_TIP);
        }
    }

    /***
     * @Description: 绩效状态全局控制

     * @[param] [type, status]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  man.z
     * @date:  2019-06-26 17:31
     */
    @RequestMapping(value = "/achievement",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse updateEnableAchievement(@RequestParam(defaultValue = "0") Integer type , @NotNull(message = "选择操作状态") Integer status){
        try {
            log.info("request : 绩效状态修改 修改雷修 [{}] , 修改状态 [{}] . parameter [{}] : ", type, status);
            Integer userId = systemUserController.getUserId();
            return achievementRuleBiz.updateAchievementEnableStatus(status,userId);
        } catch (Exception e){
            log.error(" 绩效全局修改异常 : 异常信息 ",e );
            return new ObjectRestResponse().rel(Response.FAILURE).msg(Response.SYSTEM_TIP);
        }
    }

    /**
     * @Description: 查询绩效启用状态

     * @[param] []
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  man.z
     * @date:  2019-06-26 17:35
     */
    @RequestMapping(value = "/findAchievement" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse findAchievement(){
        try {
            log.info("request : 查询绩效状态 ");
            return achievementRuleBiz.findIAchievementEnableStatus();
        } catch (Exception e){
            log.error(" 查询绩效状态异常 : 异常信息 ", e);
            return new ObjectRestResponse().rel(Response.FAILURE).msg(Response.SYSTEM_TIP);
        }
    }

}
