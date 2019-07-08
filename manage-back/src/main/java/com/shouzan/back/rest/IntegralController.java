package com.shouzan.back.rest;


import com.github.pagehelper.PageHelper;
import com.shouzan.back.biz.IntegralRecordBiz;
import com.shouzan.back.biz.IntegralRuleBiz;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.integral.IntegralRecord;
import com.shouzan.back.entity.integral.IntegralRule;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: man.z
 * @Date: 2019-05-27 16:13
 *
 * @Description: 会员积分管理
 */
@Controller
@RequestMapping("/integral")
@Slf4j
@Validated
public class IntegralController {
    @Autowired
    private IntegralRecordBiz integralRecordBiz;
    /**
     * @Description: 分页查询会员积分记录数据

     * @[param] [pageSize, current, searchSatisfy]
     * @return com.shouzan.common.msg.ObjectRestResponse<scala.math.Integral>
     * @author:  man.z
     * @date:  2019-05-28 14:46
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<IntegralRecord> page(@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "1") int current, SearchSatisfy search){
        try {
            log.info("request : 会员积分记录分页查询列表 , 每页显示[{}] , 当前页数[{}] . parameter : [{}] ",pageSize, current, search);
            int count = integralRecordBiz.queryPageCount(search);
            PageHelper.startPage(current,pageSize,false);
            List<IntegralRecord> list = integralRecordBiz.queryPageList(search);
            return new ObjectRestResponse<IntegralRecord>().total(count).rows(list).rel(Response.SUCCESS);
        }catch (Exception e){
            log.error("会员积分记录分页查询列表异常 : 异常信息 : [{}] " , e);
            return new ObjectRestResponse<IntegralRecord>().rel(Response.FAILURE).msg("分页查询会员积分异常 : 异常信息 [{}] "+e.getMessage() );
        }
    }

    /**
     * @Description: 根据openid查用户积分记录明细列表

     * @[param] [pageSize, current, search]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.integral.IntegralRecord>
     * @author:  man.z
     * @date:  2019-05-28 15:37
     */
    @RequestMapping(value = "/integralPage" ,method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<IntegralRecord> integralPage(@RequestParam(defaultValue = "5") int pageSize, @RequestParam(defaultValue = "1") int current ,SearchSatisfy search){
        try {
            log.info("request : 用户积分记录明细列表 , 每页显示[{}] , 当前页数[{}] . parameter : [{}]",pageSize , current , search);
            int count = integralRecordBiz.queryPageIntegralCount(search);
            PageHelper.startPage(current,pageSize,false);
            List<IntegralRecord> integralList = integralRecordBiz.queryPageIntegralList(search);
            return new ObjectRestResponse<IntegralRecord>().total(count).rows(integralList).rel(Response.SUCCESS);
        }catch (Exception e){
            log.error("用户积分明细列表查询异常 : 异常信息 : [{}]", e);
            return new ObjectRestResponse<IntegralRecord>().rel(Response.FAILURE).msg("用户积分明细记录查询异常"+e.getMessage());

        }
    }



}
/**
 * @Author: man.z
 * @Date: 2019-05-29 10:43
 *
 * @Description: 积分规则
 */
@RequestMapping("/rule")
@Controller
@Slf4j
@Validated
class IntegralRuleController{
    @Autowired
    private IntegralRuleBiz integralRuleBiz;
    @Autowired
    private SystemUserController systemUserController;

    /**
     * @Description: 积分规则分页列表

     * @[param] [pageSize, current, satisfy]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  man.z
     * @date:  2019-05-29 14:36
     */
    @RequestMapping(value = "/rulePage", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<IntegralRule> rulePage(@RequestParam(defaultValue = "3")int pageSize , @RequestParam(defaultValue = "1") int current, SearchSatisfy satisfy ){
        try {
            log.info(" request: 积分规则分页列表展示 [{}] , 每页显示 [{}] , 当前页数 [{}] . parameter : [{}] ",pageSize, current, satisfy);
            int count = integralRuleBiz.queryPageCount(satisfy);
            PageHelper.startPage(current, pageSize, false);
            List list = integralRuleBiz.queryPageList(satisfy);
            return new ObjectRestResponse().total(count).rows(list).rel(Response.SUCCESS);
        }catch (Exception e){
            log.error("积分规则分页列表查询异常 : 异常信息 [{}]", e);
            return new ObjectRestResponse().rel(Response.FAILURE).msg("积分规则列表查询异常"+e.getMessage());
        }
    }

    /**
     * @Description: 积分规则详情

     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  man.z
     * @date:  2019-05-29 14:52
     */
    @RequestMapping(value = "/selectById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse queryById(@PathVariable Integer id){
        try {
            log.info("request : 积分规则详情 [{}] . parameter : 规则id [{}] ", id);
            return new ObjectRestResponse().result(integralRuleBiz.selectById(id)).rel(Response.SUCCESS) ;
        }catch (Exception e){
            log.error(" 积分规则详情查询异常 : 异常信息 [{}]",e);
            return new ObjectRestResponse().rel(Response.FAILURE).msg("积分规则详情查询异常 "+e.getMessage());
        }
    }

    /**
     * @Description: 修改积分规则

     * @[param]
     * @return
     * @author:  man.z
     * @date:  2019-05-29 15:29
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse updateIntegralRule(@Valid IntegralRule entity){
        try {
            log.info("request : 修改积分规则 . parameter : 积分规则对象 [{}]" , entity);
            Integer userId = systemUserController.getUserId();
            entity.setLastEditId(userId);
            return new ObjectRestResponse().result(integralRuleBiz.updateByIdInfo(entity)).rel(Response.SUCCESS);
        }catch (Exception e){
            return new ObjectRestResponse().rel(Response.FAILURE).msg("规则修改异常"+e.getMessage());
        }
    }

}