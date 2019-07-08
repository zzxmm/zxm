package com.shouzan.back.rest;

import com.github.pagehelper.PageHelper;
import com.shouzan.back.biz.TypeBiz;
import com.shouzan.back.biz.impl.TypeBizImpl;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.Advert;
import com.shouzan.back.entity.Types;
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
 * @author: bin.yang
 * @Date: 2018/9/19 17:50
 * @Description:   类型管理
 */
@Controller
@RequestMapping("/types")
@Validated
@Slf4j
public class TypeController extends BaseController<TypeBizImpl, Types> {

    @Autowired
    private TypeBiz typeBiz;

    @Autowired
    private SystemUserMapper systemUserMapper;

    /**
     * 功能描述:  查询 类型分页列表
     *
     * @auther: bin.yang
     * @date: 2018/9/25 下午4:55
     */
    @RequestMapping(value = "/page" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Types> page (@RequestParam(defaultValue = "10") int pageSize , @RequestParam(defaultValue = "1") int current,
                                           SearchSatisfy search){
        try {
            int count = typeBiz.queryPageCount(search);
            PageHelper.startPage(current,pageSize,false);
            List<Types> list = typeBiz.queryPageList(search);
            log.info("request : 类型列表查询 , 每页显示[{}] , 当前显示[{}] . parameter : [{}]",pageSize,current,search);
            return new ObjectRestResponse<Advert>().total(count).rows(list).rel(Response.SUCCESS);
        } catch (Exception e) {
            log.error("类型分页查询异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * 功能描述:  修改类型状态
     *
     * @auther: bin.yang
     * @date: 2018/9/25 下午4:55
     */
    @RequestMapping(value = "/update" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Types> update (@NotNull(message = "请选择类型")String id , @NotNull(message = "请选择修改状态")Integer status){
        try {
            log.info("request : 类型状态修改 ,  parameter : 类型ID[{}] , 修改状态[{}]",id,status);
            return typeBiz.updateTypeStatus(id,status);
        } catch (Exception e) {
            log.error("类型状态修改异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * 功能描述:  查看类型详细
     *
     * @auther: bin.yang
     * @date: 2018/9/25 下午4:55
     */
    @RequestMapping(value = "/findInfo/{id}" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Types> findInfo (@PathVariable int id){
        try {
            log.info("request : 类型详细查询 ,  parameter : 类型ID[{}] ",id);
            return typeBiz.selectTypeInfoById(id);
        } catch (Exception e) {
            log.error("类型详细查询异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * 功能描述:  新增类型信息
     *
     * @auther: bin.yang
     * @date: 2018/9/25 下午4:55
     */
    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Types> add (@Valid Types entity){
        try {
            String currentUserId = getCurrentUserId();
            int userId = systemUserMapper.findIdByAccount(currentUserId);
            entity.setCreatorId(userId);
            log.info("request : 新增类型信息 ,  parameter : 类型信息[{}] ",entity);
            return typeBiz.insertTypeInfo(entity);
        } catch (Exception e) {
            log.error("新增类型信息异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * 功能描述: 修改类型信息
     *
     * @auther: bin.yang
     * @date: 2018/9/25 下午4:55
     */
    @RequestMapping(value = "/updateInfo" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Types> updateInfo (@Valid Types entity){
        try {
            String currentUserId = getCurrentUserId();
            int userId = systemUserMapper.findIdByAccount(currentUserId);
            entity.setLastEditId(userId);
            log.info("request : 修改类型信息 ,  parameter : 类型信息[{}] ",entity);
            return typeBiz.updateTypeInfo(entity);
        } catch (Exception e) {
            log.error("修改类型信息异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

}
