package com.shouzan.back.rest;

import com.github.pagehelper.PageHelper;
import com.shouzan.back.biz.ActivityBiz;
import com.shouzan.back.biz.CouponBiz;
import com.shouzan.back.biz.impl.ActivityBizImpl;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.Activity;
import com.shouzan.back.entity.ActivityParam;
import com.shouzan.back.entity.Coupon;
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
 * @Date: 2018/9/14 10:35
 *
 * @Description: 活动管理
 */
@Controller
@RequestMapping("/activity")
@Validated
@Slf4j
public class ActivityController extends BaseController<ActivityBizImpl, Activity> {

    @Autowired
    private ActivityBiz activityBiz;

    @Autowired
    private CouponBiz couponBiz;

    @Autowired
    private SystemUserMapper systemUserMapper;

    /**
     * 功能描述: 分页查询
     *
     * @auther: bin.yang
     * @date: 2018/9/14 下午4:37
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Activity> page(@RequestParam(defaultValue = "10") int pageSize , @RequestParam(defaultValue = "1") int current,
                                             SearchSatisfy search){
        try {
            int count = activityBiz.queryPageCount(search);
            PageHelper.startPage(current,pageSize,false);
            List<Activity> list = activityBiz.queryPageList(search);
            log.info("request : 活动列表查询 , 每页显示[{}] , 当前显示[{}] . parameter : [{}]",pageSize,current,search);
            return new ObjectRestResponse<Activity>().total(count).rows(list).rel(Response.SUCCESS);
        } catch (Exception e) {
            log.error("活动分页查询异常 :  异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("活动分页查询异常 : 异常信息 : "+e.getMessage());
        }
    }

    /**
     * 功能描述: 查看详情
     *
     * @auther: bin.yang
     * @date: 2018/9/14 下午4:37
     */
    @RequestMapping(value = "/findInfo/{id}" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Activity> couInfo(@PathVariable int id){
        try {
            log.info("request : 活动详情查询 . parameter : 活动ID[{}]",id);
            return activityBiz.activityInfoById(id);
        } catch (Exception e) {
            log.error("活动详情查询异常 :  异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("活动详情查询异常 : 异常信息 : "+e.getMessage());
        }
    }

    /**
     * 功能描述: 活动上下架
     *
     * @auther: bin.yang
     * @date: 2018/9/14 下午4:37
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Activity> update(@NotNull(message = "选择活动") String id, @NotNull(message = "选择修改状态") Integer status){
        try {
            log.info("request : 修改活动状态 . parameter : 活动ID[{}] , 修改状态[{}]",id,status);
            return activityBiz.updateStatus(id,status);
        } catch (Exception e) {
            log.error("活动状态修改异常 :  异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("活动状态修改异常 : 异常信息 : "+e.getMessage());
        }
    }

    /**
     * 功能描述: 新增活动
     *
     * @auther: bin.yang
     * @date: 2018/9/14 下午4:37
     */
    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Activity> addInfo(@Valid Activity entity , @Valid ActivityParam activityParam){
        try {
            String currentUserId = getCurrentUserId();
            int userId = systemUserMapper.findIdByAccount(currentUserId);
            entity.setCreatorId(userId);
            log.info("request : 新增活动信息 . parameter : 活动信息[{}] , 活动参数[{}]",entity,activityParam);
            return activityBiz.addInfo(entity,activityParam);
        } catch (Exception e) {
            log.error("新增活动信息异常 :  异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("新增活动信息异常 : 异常信息 : "+e.getMessage());
        }
    }

    /**
     * 功能描述: 活动修改
     *
     * @auther: bin.yang
     * @date: 2018/9/14 下午4:37
     */
    @RequestMapping(value = "/updateInfo" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Activity> updateInfo (@Valid Activity entity , @Valid ActivityParam activityParam){
        try {
            String currentUserId = getCurrentUserId();
            int userId = systemUserMapper.findIdByAccount(currentUserId);
            entity.setLastEditId(userId);
            log.info("request : 修改活动信息 . parameter : 活动信息[{}] , 活动参数[{}]",entity,activityParam);
            return activityBiz.updateActivityAndParam(entity,activityParam);
        } catch (Exception e) {
            log.error("修改活动信息异常 :  异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("修改活动信息异常 : 异常信息 : "+e.getMessage());
        }
    }

    @RequestMapping(value = "/couponList" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Coupon> couponList (){
        try {
            log.info("request : 获取卡卷信息列表 . parameter : [无]");
            return couponBiz.selectCouponAllInfo();
        } catch (Exception e) {
            log.error("卡卷列表查询异常 :  异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("卡卷列表查询异常 : 异常信息 : "+e.getMessage());
        }
    }

}
