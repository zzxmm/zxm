package com.shouzan.back.rest;

import com.github.pagehelper.PageHelper;
import com.shouzan.back.biz.AdvertBiz;
import com.shouzan.back.biz.impl.AdvertBizImpl;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.Advert;
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
 * @Date: 2018/9/18 17:52
 * @Description: 广告管理
 */
@Controller
@RequestMapping("/advert")
@Validated
@Slf4j
public class AdvertController extends BaseController<AdvertBizImpl, Advert> {

    @Autowired
    private AdvertBiz advertBiz;

    @Autowired
    private SystemUserController systemUserController;

    /**
     * 功能描述:  分页查询
     *
     * @auther: bin.yang
     * @date: 2018/9/19 下午2:21
     */
    @RequestMapping(value = "/page" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Advert> page(@RequestParam(defaultValue = "10") int pageSize , @RequestParam(defaultValue = "1") int current,
                                           SearchSatisfy search){
        try {
            int count = advertBiz.queryPageCount(search);
            PageHelper.startPage(current,pageSize,false);
            List<Advert> list = advertBiz.queryPageList(search);
            log.info("request : 广告列表查询 , 每页显示[{}] , 当前显示[{}] . parameter : [{}]",pageSize,current,search);
            return new ObjectRestResponse<Advert>().total(count).rows(list).rel(Response.SUCCESS);
        } catch (Exception e) {
            log.error("广告分页查询异常 : 异常信息 : "+e.getMessage());
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("广告分页查询异常 : 异常信息 : "+e.getMessage());
        }
    }

    /**
     * 功能描述:  查询详细信息
     *
     * @auther: bin.yang
     * @date: 2018/9/19 下午2:21
     */
    @RequestMapping(value = "/findInfo/{id}" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Advert> findInfo(@PathVariable int id){
        log.info("request : 广告详情展示 , parameter : 广告 ID[{}]",id);
        return advertBiz.queryAdvertById(id);
    }

    /**
     * 功能描述:  修改广告状态
     *
     * @auther: bin.yang
     * @date: 2018/9/19 下午2:21
     */
    @RequestMapping(value = "/update" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Advert> update(@NotNull(message = "请选择广告") String id , @NotNull(message = "请选择状态") Integer status){
        try {
            log.info("request : 广告状态修改 , parameter :  广告ID[{}] , 修改状态[{}]",id,status);
            return advertBiz.updateAdvertStatus(id,status);
        } catch (Exception e) {
            log.error("广告状态修改异常 : 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("广告状态修改异常 : 异常信息 : "+e.getMessage());
        }
    }

    /**
     * 功能描述:  修改广告信息
     *
     * @auther: bin.yang
     * @date: 2018/9/19 下午2:21
     */
    @RequestMapping(value = "/updateInfo" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Advert> updateInfo(@Valid Advert entity){
        try {
            Integer userId = systemUserController.getUserId();
            entity.setLastEditId(userId);
            log.info("request : 广告信息修改 , parameter : 广告信息修改[{}]",entity);
            return advertBiz.updateAdvertInfoById(entity);
        } catch (Exception e) {
            log.error("修改广告信息异常 :  异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("修改广告信息异常 : 异常信息 : "+e.getMessage());
        }
    }

    /**
     * 功能描述:  新增广告
     *
     * @auther: bin.yang
     * @date: 2018/9/19 下午2:21
     */
    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Advert> add(@Valid Advert entity){
        try {
            Integer userId = systemUserController.getUserId();
            entity.setCreatorId(userId);
            log.info("request : 添加广告 , parameter : 广告信息[{}]",entity);
            return advertBiz.insertAdvertInfo(entity);
        } catch (Exception e) {
            log.error("新增广告信息异常 :  异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("新增广告信息异常 : 异常信息 : "+e.getMessage());
        }
    }

}


