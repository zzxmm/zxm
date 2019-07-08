package com.shouzan.back.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shouzan.back.biz.SystemUserBiz;
import com.shouzan.back.biz.impl.SystemUserBizImpl;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.SystemUser;
import com.shouzan.back.mapper.SystemUserMapper;
import com.shouzan.back.rpc.UserFeignService;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;
import com.shouzan.common.rest.BaseController;
import com.shouzan.uuc.util.Page;
import com.shouzan.uuc.util.ResponseMsg;
import com.shouzan.uuc.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/10/11 20:55
 * @Description:  系统用户管理
 */
@Controller
@RequestMapping("/systemUser")
@Validated
@Slf4j
public class SystemUserController extends BaseController<SystemUserBizImpl, SystemUser> {

    @Autowired
    private UserFeignService userFeignService;

    @Autowired
    private SystemUserBiz systemUserBiz;

    @Autowired
    private SystemUserMapper systemUserMapper;

    /**
     * @Description: (系统用户 列表查询)
     * @param pageSize
     * @param current
     * @param searchSatisfy
     * @[param] [pageSize, current, searchSatisfy]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.SystemUser>
     * @author:  bin.yang
     * @date:  2018/10/16 上午11:50
     */
    @RequestMapping(value = "/page" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<SystemUser> page (@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "1") int current,
                                           SearchSatisfy searchSatisfy){
        Page<UserVo> page = new Page<>();
        page.setEveryPage(pageSize);
        page.setCurrentPage(current);
        UserVo userVo = new UserVo();
        userVo.setName(searchSatisfy.getKeywords());
        userVo.setStartTime(searchSatisfy.getCreateTimeStart());
        userVo.setEndTime(searchSatisfy.getCreateTimeEnd());
        userVo.setUserState(searchSatisfy.getStatus());
        userVo.setDataSource(Byte.parseByte("1"));
        page.setCondition(userVo);
        log.info("request : 系统用户列表查询  , 每页显示[{}] , 当前显示[{}] . parameter : [{}]",pageSize,current,searchSatisfy);
        try {
            int usersCount = userFeignService.findUsersCount(page);
            ResponseMsg<Object> usersPage = userFeignService.findUsersPage(page);
            if(usersPage.getIsSuccess()){
                Object data = usersPage.getData();
                List<UserVo> userVoList = JSONObject.parseArray(JSON.toJSONString(data), UserVo.class);
                List<SystemUser> systemUserList = new ArrayList<>();
                SystemUser systemUser = null;
                if(userVoList != null && userVoList.size() > 0){
                    for (UserVo vo : userVoList) {
                        systemUser = new SystemUser();
                        systemUser.setId(Integer.parseInt(vo.getUserId()+""));
                        systemUser.setUserName(vo.getName());
                        systemUser.setSex(vo.getSex());
                        systemUser.setEmail(vo.geteMail());
                        systemUser.setPhone(vo.getMobile());
                        systemUserList.add(systemUser);
                    }
                }
                return new ObjectRestResponse<SystemUser>().rel(Response.SUCCESS).rows(systemUserList).total(usersCount);
            }else{
                return new ObjectRestResponse<SystemUser>().rel(Response.FAILURE).msg("查询列表失败");
            }
        } catch (Exception e) {
            log.error("系统用户分页查询异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<SystemUser>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (修改系统用户信息)
     * @param systemUser
     * @[param] [systemUser]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.SystemUser>
     * @author:  bin.yang
     * @date:  2018/10/11 下午9:36
     */
    @RequestMapping(value = "/update" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<SystemUser> update(@Valid SystemUser systemUser){
        UserVo userVo = new UserVo();
        userVo.setUserId(systemUser.getId());
        userVo.setName(systemUser.getUserName());
        userVo.setLoginId(systemUser.getUserAccount());
        userVo.setPassword(systemUser.getUserPassword());
        userVo.setSex(systemUser.getSex());
        userVo.seteMail(systemUser.getEmail());
        userVo.setMobile(systemUser.getPhone());
        userVo.setUserState(systemUser.getUserState());
        try {
            if ("1".equals(systemUser.getId())) {
                return  new ObjectRestResponse<>().rel(Response.FAILURE).msg("不能修改系统用户");
            }else {
                log.info("request : 修改系统用户信息 , parameter:用户信息[{}] ", systemUser);
                ResponseMsg<Object> responseMsg = userFeignService.UpdateUserById(userVo);
                if (responseMsg.getIsSuccess()) {
                    systemUser.setUserCenterId(Integer.parseInt(responseMsg.getData().toString()));
                    return systemUserBiz.updateSystemUserInfo(systemUser);
                } else {
                    return new ObjectRestResponse<>().rel(Response.FAILURE).msg(responseMsg.getMessage());
                }
            }
        } catch (Exception e) {
            log.error("修改系统用户信息异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<SystemUser>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (删除系统用户信息)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.SystemUser>
     * @author:  bin.yang
     * @date:  2018/10/11 下午9:37
     */
    @RequestMapping(value = "/delete" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<SystemUser> delete(@NotNull(message = "选择要删除的用户") String id){
        try {
            if ("1".equals(id)) {
                return  new ObjectRestResponse<>().rel(Response.FAILURE).msg("不能删除系统用户");
            }else {
                log.info("request : 删除系统用户信息 , parameter:用户ID[{}] ",id);
                ResponseMsg<Object> responseMsg = userFeignService.delUserById(id);
                if(responseMsg.getIsSuccess()){
                    return  systemUserBiz.deleteSystemUserInfo(id);
                }else{
                    return  new ObjectRestResponse<>().rel(Response.FAILURE).msg(responseMsg.getMessage());
                }
            }
        } catch (Exception e) {
            log.error("删除系统用户信息异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<SystemUser>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (新增系统用户信息)
     * @param systemUser
     * @[param] [systemUser]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.SystemUser>
     * @author:  bin.yang
     * @date:  2018/10/11 下午9:37
     */
    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<SystemUser> add(@Valid SystemUser systemUser){
        UserVo userVo = new UserVo();
        userVo.setMobile(systemUser.getPhone());
        ResponseMsg<Object> responseMobile = userFeignService.findUserByInfo(userVo);
        if(responseMobile.getIsSuccess()){
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("手机号已被注册");
        }
        userVo = new UserVo();
        userVo.setLoginId(systemUser.getUserAccount());
        ResponseMsg<Object> responseFlag = userFeignService.findUserByInfo(userVo);
        if(responseFlag.getIsSuccess()){
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("账号已被注册");
        }
        userVo.setMobile(systemUser.getPhone());
        userVo.setName(systemUser.getUserName());
        userVo.setLoginId(systemUser.getUserAccount());
        userVo.setPassword(systemUser.getUserPassword());
        userVo.setSex(systemUser.getSex());
        userVo.seteMail(systemUser.getEmail());
        userVo.setUserState(systemUser.getUserState());
        userVo.setDataSource((byte)1);
        try {
            log.info("request : 新增系统用户信息 , parameter:用户信息[{}] ",systemUser);
            ResponseMsg<Object> responseMsg = userFeignService.saveUser(userVo);
            if(responseMsg.getIsSuccess()){
                systemUser.setUserCenterId(Integer.parseInt(responseMsg.getData().toString()));
                int i = systemUserBiz.selectUserInfoByCenterId(responseMsg.getData().toString());
                if(i > 0){
                    return systemUserBiz.updateSystemUserInfo(systemUser);
                }else{
                    return systemUserBiz.addSystemUserInfo(systemUser);
                }
            }else{
                return  new ObjectRestResponse<>().rel(Response.FAILURE).msg(responseMsg.getMessage());
            }
        } catch (Exception e) {
            log.error("新增系统用户信息异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<SystemUser>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (修改回显)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.SystemUser>
     * @author:  bin.yang
     * @date:  2018/10/11 下午10:06
     */
    @RequestMapping(value = "/findInfo/{id}" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<SystemUser> findInfo(@PathVariable int id){
        UserVo userVo = new UserVo();
        userVo.setUserId(id);
        log.info("request : 查询用户详细信息 , parameter:用户信息[{}] ",id);
        try {
            ResponseMsg<Object> userByInfo = userFeignService.findUserByInfo(userVo);
            if(userByInfo.getIsSuccess()){
                Object data = userByInfo.getData();
                UserVo parseObject = JSONObject.parseObject(JSON.toJSONString(data), UserVo.class);
                SystemUser systemUser = new SystemUser();
                if(parseObject != null){
                    systemUser.setId(parseObject.getUserId());
                    systemUser.setUserName(parseObject.getName());
                    systemUser.setSex(parseObject.getSex());
                    systemUser.setUserAccount(parseObject.getLoginId());
                    systemUser.setUserPassword(parseObject.getPassword());
                    systemUser.setPhone(parseObject.getMobile());
                    systemUser.setEmail(parseObject.geteMail());
                    systemUser.setUserState(parseObject.getUserState());
                }
                return  new ObjectRestResponse<>().rel(Response.SUCCESS).result(systemUser);
            }else{
                return  new ObjectRestResponse<>().rel(Response.FAILURE).msg(userByInfo.getMessage());
            }
        } catch (Exception e) {
            log.error("系统用户详细查询异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<SystemUser>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (验证用户信息)
     * @param
     * @[param] []
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/1/11 2:22 PM
     */
    @RequestMapping(value = "/AuthUser" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse AuthUser(){
        try {
            String acc = getCurrentUserId();
            if(!"".equals(acc)){
                return new ObjectRestResponse().rel(Response.SUCCESS).result(acc);
            }else {
                return new ObjectRestResponse().rel(Response.FAILURE).msg("获取用户信息失败");
            }
        } catch (Exception e) {
            log.error("异常信息 : [{}]" , e);
            return new ObjectRestResponse().rel(Response.FAILURE).msg("获取用户信息失败");
        }
    }

    /**
     * @Description: (获取用户ID)
     * @param
     * @[param] []
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/1/11 2:22 PM
     */
    @RequestMapping(value = "/getUserId")
    @ResponseBody
    public Integer getUserId(){
        Integer userId = null;
        try {
            String acc = getCurrentUserId();
            userId = systemUserMapper.findIdByAccount(acc);
        } catch (Exception e) {
            userId = 1;
        }
        return userId;
    }
}