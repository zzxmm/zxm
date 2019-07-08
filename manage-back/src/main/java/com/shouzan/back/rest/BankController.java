package com.shouzan.back.rest;


import com.github.pagehelper.PageHelper;
import com.shouzan.back.biz.BankBiz;
import com.shouzan.back.biz.impl.BankBizImpl;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.Bank;
import com.shouzan.back.mapper.SystemUserMapper;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;
import com.shouzan.common.rest.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * @Author: bin.yang
 * @Date: 2018/9/11 10:55
 * @Description:  银行管理
 */
@Controller
@RequestMapping("/bank")
@Validated
@Slf4j
public class BankController extends BaseController<BankBizImpl,Bank>{

    @Autowired
    private BankBiz bankBiz;

    @Autowired
    private SystemUserMapper systemUserMapper;

    /**
     * 功能描述: 分页查询
     *
     * @auther: bin.yang
     * @date: 2018/9/14 下午4:37
     */
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Bank> page(@RequestParam(defaultValue = "10") int pageSize , @RequestParam(defaultValue = "1") int current,
                                         SearchSatisfy search){
        try {
            log.info("request : 银行列表查询 , 每页显示[{}] , 当前显示[{}] . parameter : [{}]",pageSize,current,search);
            int count = bankBiz.queryBankListCount(search);
            PageHelper.startPage(current,pageSize,false);
            List<Bank> list = bankBiz.queryBankList(search);
            return  new ObjectRestResponse<Bank>().total(count).rows(list).rel(Response.SUCCESS);
        } catch (Exception e) {
            log.error("银行分页查询异常 :  异常信息 : [{}]" , e);
            return new ObjectRestResponse<Bank>().rel(Response.FAILURE).msg("银行分页查询异常 : 异常信息 : "+e.getMessage());
        }
    }

    /**
     * 功能描述: 查看详细信息
     *
     * @auther: bin.yang
     * @date: 2018/9/14 下午4:37
     */
    @RequestMapping(value = "/bankInfo/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Bank> bankInfo(@PathVariable int id){
        try {
            log.info("request : 查询银行详情 . parameter : 银行ID[{}]",id);
            return bankBiz.queryBankInfo(id);
        } catch (Exception e) {
            log.error("银行详情查询异常 :  异常信息 : [{}]" , e);
            return new ObjectRestResponse<Bank>().rel(Response.FAILURE).msg("银行详情查询异常 : 异常信息 : "+e.getMessage());
        }
    }

    /**
     * 功能描述: 修改银行启用状态
     *
     * @auther: bin.yang
     * @date: 2018/9/14 下午4:37
     */
    @RequestMapping(value = "/updateBank",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Bank> updateBank(@NotNull(message = "选择修改银行") String id,@NotNull(message = "选择修改状态") Integer status){
        try {
            log.info("request : 修改银行状态 . parameter : 银行ID[{}] , 修改状态[{}]",id,status);
            return  bankBiz.updateBank(id,status);
        } catch (Exception e) {
            log.error("银行状态修改异常 :  异常信息 : [{}]" , e);
            return new ObjectRestResponse<Bank>().rel(Response.FAILURE).msg("银行状态修改异常 : 异常信息 : "+e.getMessage());
        }
    }

    /**
     * 功能描述: 新增银行信息
     *
     * @auther: bin.yang
     * @date: 2018/9/14 下午4:37
     */
    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Bank> addInfo(@Valid Bank entity){
        try {
            String currentUserId = getCurrentUserId();
            int userId = systemUserMapper.findIdByAccount(currentUserId);
            entity.setCreatorId(userId);
            log.info("request : 新增银行信息 . parameter : 银行信息[{}]",entity);
            return bankBiz.addInfo(entity);
        } catch (Exception e) {
            log.error("新增银行信息异常 :  异常信息 : [{}]" , e);
            return new ObjectRestResponse<Bank>().rel(Response.FAILURE).msg("新增银行信息异常 : 异常信息 : "+e.getMessage());
        }
    }

    /**
     * 功能描述: 银行信息修改
     *
     * @auther: bin.yang
     * @date: 2018/9/14 下午4:37
     */
    @RequestMapping(value = "/updateInfo" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Bank> updateInfo(@Valid Bank entity){
        try {
            String currentUserId = getCurrentUserId();
            int userId = systemUserMapper.findIdByAccount(currentUserId);
            entity.setLastEditId(userId);
            log.info("request : 修改银行信息 . parameter : 银行信息[{}]",entity);
            return bankBiz.updateBankInfo(entity);
        } catch (Exception e) {
            log.error("修改银行信息异常 !  异常信息 : [{}]" , e);
            return new ObjectRestResponse<Bank>().rel(Response.FAILURE).msg("修改银行信息异常 : 异常信息 : "+e.getMessage());
        }
    }


}
