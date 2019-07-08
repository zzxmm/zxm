package com.shouzan.back.rest;

import com.github.pagehelper.PageHelper;
import com.shouzan.back.biz.*;
import com.shouzan.back.biz.impl.CouponBizImpl;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.Bank;
import com.shouzan.back.entity.Coupon;
import com.shouzan.back.entity.Merchants;
import com.shouzan.back.entity.Store;
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
 * @Date: 2018/9/13 09:56
 * @Description:   卡卷管理
 */
@Controller
@RequestMapping("/coupon")
@Validated
@Slf4j
public class CouponController extends BaseController<CouponBizImpl, Coupon> {

    @Autowired
    private CouponBiz couponBiz;

    @Autowired
    private BankBiz bankBiz;

    @Autowired
    private MerBiz merBiz;

    @Autowired
    private StoreBiz storeBiz;

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Autowired
    private SystemUserController systemUserController;

    @Autowired
    private RuleBiz ruleBiz;

    @Autowired
    private RuleController ruleController;


    /**
     * 功能描述: 分页查询
     *
     * @auther: bin.yang
     * @date: 2018/9/14 下午4:37
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Coupon> page(@RequestParam(defaultValue = "10") int pageSize , @RequestParam(defaultValue = "1") int current,
                                           SearchSatisfy search){
        try {
            int count = couponBiz.queryPageCount(search);
            PageHelper.startPage(current,pageSize,false);
            List<Coupon> list = couponBiz.queryPageList(search);
            log.info("request : 卡卷列表查询 , 每页显示[{}] , 当前显示[{}] . parameter : [{}]",pageSize,current,search);
            return new ObjectRestResponse<Coupon>().total(count).rows(list).rel(Response.SUCCESS);
        } catch (Exception e) {
            log.error("卡卷分页查询异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * 功能描述: 查看详细信息
     *
     * @auther: bin.yang
     * @date: 2018/9/14 下午4:37
     */
    @RequestMapping(value = "/couInfo/{id}" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Coupon> couInfo(@PathVariable int id){
        try {
            log.info("request : 卡卷详情查询  . parameter : 卡卷ID[{}]",id);
            Coupon coupon = couponBiz.couponInfoById(id);
            if(coupon != null){
                return ruleBiz.selectCouponRuleList(coupon);
            }else {
                return new ObjectRestResponse<>().rel(Response.FAILURE).msg("查询详情失败");
            }
        } catch (Exception e) {
            log.error("卡卷详细查询异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * 功能描述: 新增卡卷
     *
     * @auther: bin.yang
     * @date: 2018/9/14 下午4:37
     */
    /*@RequestMapping(value = "/add" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Coupon> add(@Valid Coupon entity){
        try {
            String storeId = entity.getStoreId();
            String currentUserId = getCurrentUserId();
            int userId = systemUserMapper.findIdByAccount(currentUserId);
            entity.setCreatorId(userId);
            log.info("request : 新增卡卷信息  . parameter : 卡卷信息[{}] , 门店信息[{}]", entity,storeId);
            ObjectRestResponse<Coupon> response = couponBiz.addInfo(entity, storeId);
            if(response.getRel()){
                if(entity.getPurcRuleList() != null || entity.getSellRuleList() != null){
                    return ruleBiz.addRuleList(entity);
                }else {
                    return response;
                }
            }else {
                return response;
            }
        } catch (Exception e) {
            log.error("新增卡卷信息异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }*/

    /**
     * 功能描述: 修改卡卷信息
     *
     * @auther: bin.yang
     * @date: 2018/9/14 下午4:37
     */
    /*@RequestMapping(value = "/updateInfo" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Coupon> updateInfo(@Valid Coupon entity){
        try {
            String storeId = entity.getStoreId();
            String currentUserId = getCurrentUserId();
            int userId = systemUserMapper.findIdByAccount(currentUserId);
            entity.setLastEditId(userId);
            log.info("request : 修改卡卷信息  . parameter : 卡卷信息[{}] , 门店信息[{}]", entity,storeId);
            ObjectRestResponse<Coupon> response = couponBiz.updateCouponInfo(entity, storeId);
            if(response.getRel()){
                return ruleBiz.updateRuleList(entity);
            }else {
                return response;
            }
        } catch (Exception e) {
            log.error("修改卡卷信息异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }*/

    /**
     * 功能描述: 修改卡卷上下架状态
     * @auther: bin.yang
     * @date: 2018/9/14 下午4:37
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Coupon> update(@NotNull(message = "选择卡卷") String id,@NotNull(message = "选择状态") Integer status){
        try {
            log.info("request : 修改卡卷状态  . parameter : 卡卷ID[{}] , 修改状态[{}]",id,status);
            return couponBiz.updateStatus(id,status);
        } catch (Exception e) {
            log.error("卡卷状态修改异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (获取银行列表)
     * @param
     * @[param] []
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Bank>
     * @author:  bin.yang
     * @date:  2018/12/18 5:30 PM
     */
    @RequestMapping(value = "/bankList",method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Bank> bankList(){
        log.info("request : 获取所有银行列表查询 , parameter : 无");
        return bankBiz.selectBankList();
    }

    /**
     * @Description: (获取商家列表)
     * @param
     * @[param] []
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Merchants>
     * @author:  bin.yang
     * @date:  2018/12/18 5:30 PM
     */
    @RequestMapping(value = "/merList",method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Merchants> merList(){
        try {
            log.info("request : 所属商家列表查询 , parameter : 无");
            return merBiz.selectMerList();
        } catch (Exception e) {
            log.error("商户列表加载异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (获取商户门店列表)
     * @param id
     * @param region
     * @param pageSize
     * @param current
     * @[param] [id, region, pageSize, current]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Store>
     * @author:  bin.yang
     * @date:  2018/12/18 5:30 PM
     */
    @RequestMapping(value = "/storeList",method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Store> storeList(@NotNull(message = "请选择商家") int id,String region ,
                                               @RequestParam(defaultValue = "10") int pageSize,
                                               @RequestParam(defaultValue = "1") int current){
        try {
            log.info("request : 门店列表查询 , parameter : 无");
            SearchSatisfy satisfy = new SearchSatisfy();
            satisfy.setBelonged(id+"");
            if(region != null){
                satisfy.setName(region);
            }
            int count = storeBiz.queryPageCount(satisfy);
            PageHelper.startPage(current,pageSize);
            List<Store> storeList = storeBiz.queryPageList(satisfy);
            return new ObjectRestResponse<Store>().rel(Response.SUCCESS).rows(storeList).total(count);
        } catch (Exception e) {
            log.error("门店信息列表异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (获取门店ID字符集)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2018/12/18 5:30 PM
     */
    @RequestMapping(value = "/storeIdListAll",method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse storeIdListAll(@NotNull(message = "请选择商家") int id,String region){
        try {
            log.info("request : 门店ID字符集 , parameter : 无");
            return storeBiz.storeIdListAll(id,region);
        } catch (Exception e) {
            log.error("门店ID字符集异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (新增卡卷信息)
     * @param coupon
     * @[param] [entity]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Coupon>
     * @author:  bin.yang
     * @date:  2018/12/21 11:51 AM
     */
    @RequestMapping(value = "/addJson" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Coupon> addJson(@Valid @RequestBody Coupon coupon){
        try {
            ObjectRestResponse<Coupon> restResponse = ruleController.validationDateStatus(coupon);
            if(!restResponse.getRel()){
                return restResponse;
            }
            Coupon entity = restResponse.getResult();
            String storeId = entity.getStoreId();
            int userId = systemUserController.getUserId();
            entity.setCreatorId(userId);
            log.info("request : 新增卡卷信息  . parameter : 卡卷信息[{}] , 门店信息[{}]", entity,storeId);
            ObjectRestResponse<Coupon> response = couponBiz.addInfo(entity, storeId);
            if(response.getRel()){
                if(entity.getPurcRuleList() != null || entity.getSellRuleList() != null){
                    return ruleBiz.addRuleList(entity);
                }else {
                    return response;
                }
            }else {
                return response;
            }
        } catch (Exception e) {
            log.error("新增卡卷信息异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (修改卡卷信息)
     * @param coupon
     * @[param] [entity]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Coupon>
     * @author:  bin.yang
     * @date:  2018/12/21 11:51 AM
     */
    @RequestMapping(value = "/updateInfoJson" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Coupon> updateInfoJson(@Valid @RequestBody Coupon coupon){
        try {
            ObjectRestResponse<Coupon> restResponse = ruleController.validationDateStatus(coupon);
            if(!restResponse.getRel()){
                return restResponse;
            }
            Coupon entity = restResponse.getResult();
            String storeId = entity.getStoreId();
            int userId = systemUserController.getUserId();
            entity.setLastEditId(userId);
            log.info("request : 修改卡卷信息  . parameter : 卡卷信息[{}] , 门店信息[{}]", entity,storeId);
            ObjectRestResponse<Coupon> response = couponBiz.updateCouponInfo(entity, storeId);
            if(response.getRel()){
                return ruleBiz.updateRuleList(entity);
            }else {
                return response;
            }
        } catch (Exception e) {
            log.error("修改卡卷信息异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

}
