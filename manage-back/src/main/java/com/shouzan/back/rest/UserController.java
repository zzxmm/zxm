package com.shouzan.back.rest;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.shouzan.back.biz.UserBiz;
import com.shouzan.back.biz.impl.UserBizImpl;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.User;
import com.shouzan.back.rpc.UserFeignService;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;
import com.shouzan.common.rest.BaseController;
import com.shouzan.uuc.util.ResponseMsg;
import com.shouzan.uuc.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/9/12 12:59
 * @Description:  用户管理
 */
@Controller
@RequestMapping("user")
@Validated
@Slf4j
public class UserController  extends BaseController<UserBizImpl, User> {

    @Autowired
    private UserBiz userBiz;

    @Autowired
    private UserFeignService userFeignService;

    /**
     * @Description: (分页查询用户信息)
     * @param pageSize
     * @param current
     * @param searchSatisfy
     * @[param] [pageSize, current, searchSatisfy]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.User>
     * @author:  bin.yang
     * @date:  2018/11/21 11:20 AM
     */
    @RequestMapping(value = "/page" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<User> page (@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "1") int current,
                                                SearchSatisfy searchSatisfy){
        log.info("request : 用户列表查询  , 每页显示[{}] , 当前显示[{}] . parameter : [{}]",pageSize,current,searchSatisfy);
        try {
                int count = userBiz.queryUserCount(searchSatisfy);
                PageHelper.startPage(current,pageSize,false);
                List<User> list = userBiz.queryUserList(searchSatisfy);
                return new ObjectRestResponse<User>().rel(Response.SUCCESS).rows(list).total(count);
        } catch (Exception e) {
            log.error("用户分页查询异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<User>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (通过ID查询用户详细信息)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.User>
     * @author:  bin.yang
     * @date:  2018/11/21 11:21 AM
     */
    @RequestMapping(value = "/findInfo/{id}" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<User> findInfo(@PathVariable int id){
        log.info("request : 查询用户详细信息 , parameter:用户信息[{}] ",id);
        try {
            User info = userBiz.findInfoById(id);
            return  new ObjectRestResponse<>().rel(Response.SUCCESS).result(info);
        } catch (Exception e) {
            log.error("用户详细查询异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<User>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (修改用户启用状态)
     * @param id
     * @param status
     * @[param] [id, status]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.User>
     * @author:  bin.yang
     * @date:  2018/11/21 11:21 AM
     */
    @RequestMapping(value = "/update" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<User> update(@NotNull(message = "请选择用户") String id, @NotNull(message = "请选择状态") Integer status){
        try {
            log.info("request : 修改用户状态 , parameter:用户id[{}] , 修改状态[{}]",id,status);
           return userBiz.updateUserStatus(id,status);
        } catch (Exception e) {
            log.error("用户状态修改异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<User>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

}
