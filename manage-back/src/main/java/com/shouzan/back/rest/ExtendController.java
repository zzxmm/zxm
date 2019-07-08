package com.shouzan.back.rest;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.shouzan.back.biz.ExtendBiz;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.extend.ExtendExchange;
import com.shouzan.back.entity.extend.ExtendRecord;
import com.shouzan.back.entity.extend.ExtendRule;
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
 * @Author: bin.yang
 * @Date: 2019/2/18 18:10
 * @Description: 分销系统 - 规则管理
 */
@Controller
@RequestMapping("/extendRule")
@Validated
@Slf4j
public class ExtendController {

    @Autowired
    private ExtendBiz extendBiz;

    @Autowired
    private SystemUserController systemUser;

    /**
     * @Description: (分页查询规则信息集合)
     * @param pageSize
     * @param current
     * @param search
     * @[param] [pageSize, current, search]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/2/19 3:07 PM
     */
    @RequestMapping(value = "/page" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse page(@RequestParam(defaultValue = "10") int pageSize,@RequestParam(defaultValue = "1") int current,
                                   SearchSatisfy search){
        try {
            log.info("request : 分销规则分页查询 , 每页显示[{}] , 当前页数[{}] . parameter : [{}]",pageSize,current,search);
            int count = extendBiz.queryPageCount(search);
            PageHelper.startPage(current,pageSize,false);
            List<ExtendRule> list = extendBiz.queryPageList(search);
            return new ObjectRestResponse().rel(Response.SUCCESS).total(count).rows(list);
        } catch (Exception e) {
            log.error("分销规则分页查询异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse().rel(Response.FAILURE).msg(Response.SYSTEM_TIP);
        }
    }

    /**
     * @Description: (新增分销规则信息)
     * @param rule
     * @[param] [rule]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/2/19 3:07 PM
     */
    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse add (@Valid @RequestBody ExtendRule rule){
        try {
            log.info("request : 新增分销规则 , parameter : [{}]",rule);
            rule.setCreatorId(systemUser.getUserId());
            return extendBiz.addExtendRuleInfo(rule);
        } catch (Exception e) {
            log.error("新增分销规则异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse().rel(Response.FAILURE).msg(Response.SYSTEM_TIP);
        }
    }

    /**
     * @Description: (修改分销规则信息)
     * @param rule
     * @[param] [rule]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/2/19 3:07 PM
     */
    @RequestMapping(value = "/updateInfo" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse updateInfo (@Valid @RequestBody ExtendRule rule){
        try {
            log.info("request : 修改分销规则信息 , parameter : [{}]",rule);
            rule.setLastEditId(systemUser.getUserId());
            return extendBiz.updateExtendRuleInfo(rule);
        } catch (Exception e) {
            log.error("修改分销规则异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse().rel(Response.FAILURE).msg(Response.SYSTEM_TIP);
        }
    }

    /**
     * @Description: (批量修改规则信息状态)
     * @param id
     * @param status
     * @[param] [id, status]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/2/19 3:06 PM
     */
    @RequestMapping(value = "/update" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse update (@NotNull(message = "选择规则") String id , @NotNull(message = "选择要修改状态") int status){
        try {
            log.info("request : 修改分销规则状态 , 规则ID : [{}] , 修改状态 : [{}] , 操作人 : [{}]",id,status,systemUser.getUserId());
            return extendBiz.updateExtendRuleStatus(id,status);
        } catch (Exception e) {
            log.error("分销规则状态修改异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse().rel(Response.FAILURE).msg(Response.SYSTEM_TIP);
        }
    }

    /**
     * @Description: (通过ID查询详细信息)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/2/19 3:06 PM
     */
    @RequestMapping(value = "/findInfo/{id}" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse findInfo (@PathVariable int id){
        try {
            log.info("request : 查询分销规则详细信息 , 规则ID : [{}] ",id);
            ExtendRule rule = extendBiz.findInfoById(id);
            return new ObjectRestResponse().rel(Response.SUCCESS).result(rule);
        } catch (Exception e) {
            log.error("分销规则详细查询异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse().rel(Response.FAILURE).msg(Response.SYSTEM_TIP);
        }
    }
}

/**
 * @Author: bin.yang
 * @Date: 2019/2/19 10:39 AM
 *
 * @Description:  分销系统 - 分销记录
 */
@Controller
@RequestMapping("/extendRecord")
@Validated
@Slf4j
 class ExtendRecordController {

    @Autowired
    private ExtendBiz extendBiz;

    /**
     * @Description: (分销记录分页查询)
     * @param pageSize
     * @param current
     * @param search
     * @[param] [pageSize, current, search]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/2/21 2:47 PM
     */
    @RequestMapping(value = "/page" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse page(@RequestParam(defaultValue = "10") int pageSize,@RequestParam(defaultValue = "1") int current,
                                   SearchSatisfy search){
        try {
            log.info("request : 分销记录分页查询 , 每页显示[{}] , 当前页数[{}] . parameter : [{}]",pageSize,current,search);
            int count = extendBiz.queryPageRecordCount(search);
            PageHelper.startPage(current,pageSize,false);
            List<ExtendRecord> list = extendBiz.queryPageRecordList(search);
            return new ObjectRestResponse().rel(Response.SUCCESS).total(count).rows(list);
        } catch (Exception e) {
            log.error("分销记录分页查询异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse().rel(Response.FAILURE).msg(Response.SYSTEM_TIP);
        }
    }
}

/**
 * @Author: bin.yang
 * @Date: 2019/2/19 10:40 AM
 *
 * @Description:  分销系统 - 佣金提现
 */
@Controller
@RequestMapping("/extendBonus")
@Validated
@Slf4j
class ExtendBonusController {

    @Autowired
    private ExtendBiz extendBiz;

    @Autowired
    private SystemUserController systemUser;

    @Autowired
    private MobileFeignService mobileFeignService;

    private static final String SUCCESS = "SUCCESS";
    private static final String RETURN = "return_code";
    private static final String RESULT = "result_code";
    @Autowired
    private UserMapper userMapper;

    /**
     * @Description: (佣金记录分页查询)
     * @param pageSize
     * @param current
     * @param search
     * @[param] [pageSize, current, search]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/2/22 10:10 AM
     */
    @RequestMapping(value = "/page" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse page(@RequestParam(defaultValue = "10") int pageSize,@RequestParam(defaultValue = "1") int current,
                                   SearchSatisfy search){
        try {
            log.info("request : 佣金提现分页查询 , 每页显示[{}] , 当前页数[{}] . parameter : [{}]",pageSize,current,search);
            int count = extendBiz.queryPageBonusCount(search);
            PageHelper.startPage(current,pageSize,false);
            List<ExtendExchange> list = extendBiz.queryPageBonusList(search);
            return new ObjectRestResponse().rel(Response.SUCCESS).total(count).rows(list);
        } catch (Exception e) {
            log.error("佣金记录分页查询异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse().rel(Response.FAILURE).msg(Response.SYSTEM_TIP);
        }
    }

    /**
     * @Description: (佣金记录详细查询)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/2/22 10:11 AM
     */
    @RequestMapping(value = "/findInfo/{id}" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse findInfo(@PathVariable int id){
        try {
            log.info("request : 佣金提现详细查询 . parameter : [{}]",id);
            return extendBiz.findInfoBonusById(id);
        } catch (Exception e) {
            log.error("佣金记录详细异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse().rel(Response.FAILURE).msg(Response.SYSTEM_TIP);
        }
    }

    /**
     * @Description: (佣金提现操作)
     * @param id
     * @param status
     * @param notes
     * @[param] [id, status, notes]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/2/22 10:11 AM
     */
    @RequestMapping(value = "/operation" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse operation(@NotNull(message = "请选择要执行的数据")Integer id,
                                        @NotNull(message = "请选择要执行的操作")Integer status, String notes){
        try {
            int s = userMapper.findUserStatusById(id);
            if(s == 1){
                return new ObjectRestResponse().rel(Response.FAILURE).msg("该用户已被停用 , 无法进行操作");
            }
            Integer userId = systemUser.getUserId();
            log.info("request : 佣金提现操作 . parameter : [{}] , 状态 : [{}] , 备注 : [{}] , 操作人 : [{}]",id,status,notes,userId);
            if(status == 2){
                return requestWeChatOperation(id,status,notes,userId);
            }else if(status == 3){
                return extendBiz.updateBonusStatus(id,status,notes,userId);
            }else {
                return new ObjectRestResponse().rel(Response.FAILURE).msg("操作状态无效");
            }
        } catch (Exception e) {
            log.error("佣金提现异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse().rel(Response.FAILURE).msg(Response.SYSTEM_TIP);
        }
    }

    /**
     * @Auther: bin.yang
     * @Date: 2019/2/22 10:11 AM
     *
     * @Description:   请求微信商户号后台
     */
    public ObjectRestResponse requestWeChatOperation(Integer id, Integer status, String notes, Integer userId){
        try {
            WithdrawalRequest request = new WithdrawalRequest();
            request.setWithdrawalType("1");
            request.setExtendExchangeId(id);
            String response = mobileFeignService.sendRedPack(request);
            ObjectRestResponse restResponse = JSON.parseObject(response, ObjectRestResponse.class);
            if(restResponse.getRel()){
                Map map = (Map)restResponse.getResult();
                if(SUCCESS.equals(map.get(RETURN)) && SUCCESS.equals(map.get(RESULT))){
                    log.error("提现成功 ! 提现流水ID [{}]",id);
                    return extendBiz.updateBonusStatus(id,status,notes,userId);
                }else{
                    log.error("提现失败 ! 异常信息 : [{}{}]",map.get("return_msg"),map.get("err_code"));
                    int s = 1;
                    String m = map.get("return_msg").toString()+map.get("err_code").toString();
                    extendBiz.updateBonusStatus(id,s,m,userId);
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