package com.shouzan.back.rest;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.shouzan.back.biz.AchievementRecordBiz;
import com.shouzan.back.biz.AchievementRuleBiz;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.Advert;
import com.shouzan.back.entity.achievement.AchievementsExchange;
import com.shouzan.back.entity.achievement.AchievementsRecord;
import com.shouzan.back.entity.achievement.AchievementsRule;
import com.shouzan.back.mapper.UserMapper;
import com.shouzan.back.rpc.MobileFeignService;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.back.vo.WithdrawalRequest;
import com.shouzan.common.msg.ObjectRestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: com.shouzan.back.rest.AchievementController
 * @Author: shouzan
 * @Date: 2019-06-26 15:53
 * @Description: TODO
 */
@Controller
@RequestMapping("/achievement")
@Slf4j
@Validated
public class AchievementController {

    @Autowired
    private AchievementRuleBiz achievementRuleBiz;

    /***
     * @Description: 查看绩效规则

     * @[param] []
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  man.z
     * @date:  2019-06-26 17:13
     */
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse page(@RequestParam(defaultValue = "10") int pageSize , @RequestParam(defaultValue = "1") int current,
                                   SearchSatisfy search){
        try {
            int count = achievementRuleBiz.queryPageCount(search);
            PageHelper.startPage(current,pageSize,false);
            List<AchievementsRule> list = achievementRuleBiz.queryPageList(search);
            log.info("request : 活动列表查询 , 每页显示[{}] , 当前显示[{}] . parameter : [{}]",pageSize,current,search);
            return new ObjectRestResponse<AchievementsRule>().total(count).rows(list).rel(Response.SUCCESS);
        } catch (Exception e) {
            log.error("活动分页查询异常 :  异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("活动分页查询异常 : 异常信息 : "+e.getMessage());
        }
    }

    /***
     * @Description: 绩效规则详情

     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  man.z
     * @date:  2019-07-01 16:44id
     */
    @RequestMapping(value = "/selectById/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse selectById(Integer id){
        try {
            log.info("request : 绩效规则详情查询 . parameter : 绩效规则ID[{}]",id);
            return achievementRuleBiz.findById(id);
        } catch (Exception e) {
            log.error("活动详情查询异常 :  异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("绩效规则详情查询异常 : 异常信息 : "+e.getMessage());
        }
    }



    /**
     * @Description: 修改规则信息

     * @[param] [achievementsRule]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  man.z
     * @date:  2019-06-30 16:18
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse updateRule(@Valid AchievementsRule achievementsRule){
        try {
            log.info("request : 绩效规则修改 [{}] . parameter : [{}]",achievementsRule);
            return achievementRuleBiz.updateInfo(achievementsRule);
        }catch (Exception e){
            log.error(" 绩效规则修改异常 : 异常信息 [{}]",e);
            return new ObjectRestResponse().rel(Response.FAILURE).msg("绩效规则详情查询异常 "+e.getMessage());
        }
    }
}

@Controller
@RequestMapping("/achievementRecord")
@Validated
@Slf4j
class AchievementRecordController {

    @Autowired
    private AchievementRecordBiz achievementRecordBiz;

    /***
     * @Description: 绩效记录分页查询

     * @[param] [pageSize, current, search]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  man.z
     * @date:  2019-06-28 16:17
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse page(@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "1") int current,
                                   SearchSatisfy search) {
        try {
            int count = achievementRecordBiz.queryPageCount(search);
            PageHelper.startPage(current, pageSize, false);
            List<AchievementsRecord> list = achievementRecordBiz.queryPageList(search);
            log.info("request : 绩效记录列表查询 , 每页显示[{}] , 当前显示[{}] . parameter : [{}]", pageSize, current, search);
            return new ObjectRestResponse<Advert>().total(count).rows(list).rel(Response.SUCCESS);
        } catch (Exception e) {
            log.error("绩效记录分页查询异常 : 异常信息 : " + e.getMessage());
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("绩效记录分页查询异常 : 异常信息 : " + e.getMessage());
        }


    }
}

@Controller
@RequestMapping("/achievementBonus")
@Validated
@Slf4j
class AchievementBonusController {

    @Autowired
    private SystemUserController systemUser;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AchievementRecordBiz achievementRecordBiz;

    @Autowired
    private MobileFeignService mobileFeignService;

    private static final String SUCCESS = "SUCCESS";

    private static final String RETURN = "return_code";

    private static final String RESULT = "result_code";

    /**
     * @Description: 提现记录分页查询

     * @[param] [pageSize, current, search]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  man.z
     * @date:  2019-06-30 16:19
     */
    @RequestMapping(value = "/page" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse page(@RequestParam(defaultValue = "10") int pageSize,@RequestParam(defaultValue = "1") int current,
                                   SearchSatisfy search){
        try {
            log.info("request : 绩效佣金提现分页查询 , 每页显示[{}] , 当前页数[{}] . parameter : [{}]",pageSize,current,search);
            int count = achievementRecordBiz.queryPageBonusCount(search);
            PageHelper.startPage(current,pageSize,false);
            List<AchievementsExchange> list = achievementRecordBiz.queryPageBonusList(search);
            return new ObjectRestResponse().rel(Response.SUCCESS).total(count).rows(list);
        } catch (Exception e) {
            log.error("佣金记录分页查询异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse().rel(Response.FAILURE).msg(Response.SYSTEM_TIP);
        }
    }

    /**
     * @Description: 提现记录查询详情

     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  man.z
     * @date:  2019-06-30 16:19
     */
    @RequestMapping(value = "/findInfo/{id}" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse findInfo(@PathVariable int id){
        try {
            log.info("request : 佣金提现详细查询 . parameter : [{}]",id);
            return achievementRecordBiz.findInfoBonusById(id);
        } catch (Exception e) {
            log.error("佣金记录详细异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse().rel(Response.FAILURE).msg(Response.SYSTEM_TIP);
        }
    }

    /**
     * @Description: 提现操作

     * @[param] [id, status, notes]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  man.z
     * @date:  2019-06-30 16:19
     */
    @RequestMapping(value = "/operation" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse operation(@NotNull(message = "请选择要执行的数据")Integer id,
                                        @NotNull(message = "请选择要执行的操作")Integer status, String notes){
        try {
//            int s = userMapper.findUserStatusById(id);
            int s = 0;
            if(s == 1){
                return new ObjectRestResponse().rel(Response.FAILURE).msg("该用户已被停用 , 无法进行操作");
            }
            Integer userId = systemUser.getUserId();
            log.info("request : 佣金提现操作 . parameter : [{}] , 状态 : [{}] , 备注 : [{}] , 操作人 : [{}]",id,status,notes,userId);
            if(status == 2){
                return requestWeChatOperation(id,status,notes,userId);
            }else if(status == 3){
                return achievementRecordBiz.updateBonusStatus(id,status,notes,userId);
            }else {
                return new ObjectRestResponse().rel(Response.FAILURE).msg("操作状态无效");
            }
        } catch (Exception e) {
            log.error("佣金提现异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse().rel(Response.FAILURE).msg(Response.SYSTEM_TIP);
        }
    }

    /**
     * @Description: 发起红包提现

     * @[param] [id, status, notes, userId]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  man.z
     * @date:  2019-06-30 16:20
     */
    public ObjectRestResponse requestWeChatOperation(Integer id, Integer status, String notes, Integer userId){
        try {
            WithdrawalRequest request = new WithdrawalRequest();
            request.setWithdrawalType("2");
            request.setAchievementsExchangeId(id);
            String response = mobileFeignService.sendRedPack(request);
            ObjectRestResponse restResponse = JSON.parseObject(response, ObjectRestResponse.class);
            if(restResponse.getRel()){
                Map map = (Map)restResponse.getResult();
                if(SUCCESS.equals(map.get(RETURN)) && SUCCESS.equals(map.get(RESULT))){
                    log.error("提现成功 ! 提现流水ID [{}]",id);
                    return achievementRecordBiz.updateBonusStatus(id,status,notes,userId);
                }else{
                    log.error("提现失败 ! 异常信息 : [{}{}]",map.get("return_msg"),map.get("err_code"));
                    int s = 1;
                    String m = map.get("return_msg").toString()+map.get("err_code").toString();
                    achievementRecordBiz.updateBonusStatus(id,s,m,userId);
                    return new ObjectRestResponse().rel(false).msg("提现失败 ! 异常信息 : [{"+m+"}] ");
                }
            }else {
                log.error("提现失败 : mobile 服务端异常");
                return new ObjectRestResponse().rel(false).msg("提现失败 : mobile 服务端异常 , 请联系技术人员");
            }
        } catch (Exception e) {
            log.error("佣金提现异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse().rel(Response.FAILURE).msg("提现失败 : mobile 服务端异常 , 请联系技术人员");
        }
    }

}