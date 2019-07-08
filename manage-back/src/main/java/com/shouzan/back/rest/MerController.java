package com.shouzan.back.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shouzan.back.biz.MerBiz;
import com.shouzan.back.biz.TypeBiz;
import com.shouzan.back.biz.impl.MerBizImpl;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.Merchants;
import com.shouzan.back.entity.Types;
import com.shouzan.back.mapper.SystemUserMapper;
import com.shouzan.back.mapper.TypesMapper;
import com.shouzan.back.rpc.MerFeignService;
import com.shouzan.back.rpc.UserFeignService;
import com.shouzan.back.util.CodeValid;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;
import com.shouzan.common.rest.BaseController;
import com.shouzan.uuc.util.Page;
import com.shouzan.uuc.util.ResponseMsg;
import com.shouzan.uuc.vo.MerchantVo;
import com.shouzan.uuc.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/9/14 15:36
 * @Description: 商家管理
 */
@Controller
@RequestMapping("/merchants")
@Validated
@Slf4j
public class MerController extends BaseController<MerBizImpl, Merchants> {

    @Autowired
    private MerBiz merBiz;

    @Autowired
    private TypeBiz typeBiz;

    @Autowired
    private MerFeignService merFeignService;

    @Autowired
    private TypesMapper typesMapper;

    @Autowired
    private SystemUserMapper systemUserMapper;

    /**
     * 功能描述:  分页列表查询
     *
     * @auther: bin.yang
     * @date: 2018/9/21 下午2:41
     */
    @RequestMapping(value = "/page" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<MerchantVo> page (@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "1") int current,
                                           SearchSatisfy searchSatisfy){
        Page<MerchantVo> page = new Page<>();
        page.setEveryPage(pageSize);
        page.setCurrentPage(current);
        MerchantVo merchantVo = new MerchantVo();
        merchantVo.setFmerName(searchSatisfy.getKeywords());
        merchantVo.setStartTime(searchSatisfy.getCreateTimeStart());
        merchantVo.setEndTime(searchSatisfy.getCreateTimeEnd());
        merchantVo.setFmemberState(searchSatisfy.getStatus());
        if(searchSatisfy.getTypes() != null){
            merchantVo.setFmerType(Byte.parseByte(searchSatisfy.getTypes()+""));
        }
        page.setCondition(merchantVo);
        try {
            int mersCount = merFeignService.findMersCount(page);
            ResponseMsg<Object> mersPage = merFeignService.findMersPage(page);
            log.info("request : 商家列表查询 , 每页显示[{}] , 当前显示[{}] . parameter : [{}]",pageSize,current,searchSatisfy);
            if(mersPage.getIsSuccess()){
                Object data = mersPage.getData();
                List<MerchantVo> merchantDateList = JSONObject.parseArray(JSON.toJSONString(data), MerchantVo.class);
                List<Merchants> merchantList = new ArrayList<>();
                Merchants merchants;
                if(merchantDateList != null && merchantDateList.size() > 0){
                    for (MerchantVo merchantVos : merchantDateList) {
                        merchants = new Merchants();
                        merchants.setId(Integer.parseInt(merchantVos.getMerchantId()+""));
                        merchants.setMerName(merchantVos.getFmerName());
                        merchants.setMerType(merchantVos.getFmerType());
                        Byte fmerType = merchantVos.getFmerType();
                        Types types = typesMapper.selectTypeInfoById(fmerType);
                        merchants.setMerTypeName(types.getTypeName());
                        merchants.setServiceTelNumber(merchantVos.getFmobile());
                        merchants.setCreateTime(merchantVos.getExpirationDate());
                        merchants.setEnableState(merchantVos.getFmemberState());
                        merchants.setWechatMchId(merchantVos.getWechatMchId());
                        merchants.setPriority(merchantVos.getFpriority());
                        merchants.setIsSelect(merchantVos.getFselected());
                        merchantList.add(merchants);
                    }
                }
                return new ObjectRestResponse<Merchants>().rel(Response.SUCCESS).rows(merchantList).total(mersCount);
            }else{
                return new ObjectRestResponse<Merchants>().rel(Response.FAILURE).msg("查询列表失败");
            }
        } catch (Exception e) {
            log.error("商户分页查询异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * 功能描述:  商家详细查询
     *
     * @auther: bin.yang
     * @date: 2018/9/21 下午2:41
     */
    @RequestMapping(value = "/findInfo/{id}" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Merchants> findInfo (@PathVariable int id){
        MerchantVo merchantVo = new MerchantVo();
        merchantVo.setMerchantId(Long.parseLong(id+""));
        log.info("request : 商家详细查询  . parameter : 商家ID[{}]",id);
        try {
            ResponseMsg<Object> merByInfo = merFeignService.findMerByInfo(merchantVo);
            if(merByInfo.getIsSuccess()){
                Object data = merByInfo.getData();
                MerchantVo parseObject = JSONObject.parseObject(JSON.toJSONString(data), MerchantVo.class);
                Merchants merchants = new Merchants();
                if(parseObject != null){
                    merchants.setId(Integer.parseInt(parseObject.getMerchantId()+""));
                    merchants.setMerName(parseObject.getFmerName());
                    merchants.setMerShotName(parseObject.getFmerShortName());
                    merchants.setServiceTelNumber(parseObject.getFcustomertel());
                    merchants.setCreateTime(parseObject.getFcreateTime());
                    merchants.setEnableState(parseObject.getFmemberState());
                    merchants.setMerType(parseObject.getFmerType());
                    if (parseObject.getServiceNumber() != null){
                        merchants.setServicenum(Byte.parseByte(parseObject.getServiceNumber()));
                    }
                    merchants.setIsSelect(parseObject.getFselected());
                    merchants.setMerIntroduce(parseObject.getIntroduction());
                    merchants.setMerCover(parseObject.getCoverimage());
                    merchants.setLogoUrl(parseObject.getHeadPortrait());
                    merchants.setLinkmanName(parseObject.getFlinkman());
                    merchants.setLinkmanTel(parseObject.getFmobile());
                    merchants.setLinkmanWechat(parseObject.getFlkwx());
                    merchants.setWechatMchId(parseObject.getWechatMchId());
                    merchants.setPriority(parseObject.getFpriority());
                    merchants.setIsSelect(parseObject.getFselected());
                }
                return new ObjectRestResponse<Merchants>().result(merchants).rel(Response.SUCCESS);
            }else{
                return new ObjectRestResponse<Merchants>().rel(Response.FAILURE).msg(merByInfo.getMessage());
            }
        } catch (Exception e) {
            log.error("商户详细查询异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * 功能描述:  修改商家启用状态
     *
     * @auther: bin.yang
     * @date: 2018/9/21 下午2:41
     */
    @RequestMapping(value = "/update" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Merchants> update (@NotNull(message = "选择商家")String id , @NotNull(message = "选择修改状态")Integer status){
        try {
            log.info("request : 修改商家启用状态  . parameter : 商家ID[{}] , 修改状态[{}]",id,status);
            ResponseMsg<Object> responseMsg = merFeignService.updateMerStatus(id, status + "");
            if(responseMsg.getIsSuccess()){
                return merBiz.updateMerchantsStatus(id,status);
            }else{
                return new ObjectRestResponse<>().rel(Response.FAILURE).msg(responseMsg.getMessage());
            }
        } catch (Exception e) {
            log.error("商户修改状态异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * 功能描述:  新增商户信息
     *
     * @auther: bin.yang
     * @date: 2018/9/21 下午2:41
     */
    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Merchants> add (@Valid Merchants entity , @NotNull(message = "选择商户类型") String merType){
        MerchantVo merchantVo = new MerchantVo();
        if(entity.getId() != null){
            merchantVo.setMerchantId(Long.parseLong(entity.getId()+""));
        }
        merchantVo.setFmerName(entity.getMerName());
        merchantVo.setFmerShortName(entity.getMerShotName());
        merchantVo.setFmerType(Byte.parseByte(merType));
        merchantVo.setServiceNumber(entity.getServicenum()+"");
        merchantVo.setFmemberState(entity.getEnableState());
        merchantVo.setFselected(entity.getIsSelect());
        merchantVo.setIntroduction(entity.getMerIntroduce());
        merchantVo.setCoverimage(entity.getMerCover());
        merchantVo.setHeadPortrait(entity.getLogoUrl());
        merchantVo.setFlinkman(entity.getLinkmanName());
        merchantVo.setFmobile(entity.getLinkmanTel());
        merchantVo.setFcustomertel(entity.getServiceTelNumber());
        merchantVo.setFlkwx(entity.getLinkmanWechat());
        merchantVo.setExpirationDate(new Date());
        merchantVo.setWechatMchId(entity.getWechatMchId());
        merchantVo.setFpriority(entity.getPriority());
        merchantVo.setFselected(entity.getIsSelect());
        String currentUserId = getCurrentUserId();
        int userId = systemUserMapper.findIdByAccount(currentUserId);
        merchantVo.setFcreatorId(userId+"");
        try {
            entity.setCreatorId(userId);
            log.info("request : 新增商户信息  . parameter : 商户信息[{}]",entity);
            ResponseMsg<Object> responseMsg = merFeignService.saveMerInfo(merchantVo);
            if(responseMsg.getIsSuccess()){
                String s = responseMsg.getData().toString();
                entity.setMerCenterId(Integer.parseInt(s));
                return merBiz.insertMerchantsInFo(entity,merType);
            }else{
                return new ObjectRestResponse<>().rel(Response.FAILURE).msg(responseMsg.getMessage());
            }
        } catch (Exception e) {
            log.error("新增商户信息异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * 功能描述:  修改商户信息
     *
     * @auther: bin.yang
     * @date: 2018/9/21 下午2:41
     */
    @RequestMapping(value = "/updateInfo" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Merchants> updateInfo (@Valid Merchants entity , @NotNull(message = "选择商户类型")String merType){
        MerchantVo merchantVo = new MerchantVo();
        merchantVo.setMerchantId(Long.parseLong(entity.getId()+""));
        merchantVo.setFmerName(entity.getMerName());
        merchantVo.setFmerShortName(entity.getMerShotName());
        merchantVo.setFmerType(Byte.parseByte(merType));
        merchantVo.setServiceNumber(entity.getServicenum()+"");
        merchantVo.setFmemberState(entity.getEnableState());
        merchantVo.setFselected(entity.getIsSelect());
        merchantVo.setIntroduction(entity.getMerIntroduce());
        merchantVo.setCoverimage(entity.getMerCover());
        merchantVo.setHeadPortrait(entity.getLogoUrl());
        merchantVo.setFlinkman(entity.getLinkmanName());
        merchantVo.setFmobile(entity.getLinkmanTel());
        merchantVo.setFcustomertel(entity.getServiceTelNumber());
        merchantVo.setFlkwx(entity.getLinkmanWechat());
        merchantVo.setExpirationDate(new Date());
        merchantVo.setWechatMchId(entity.getWechatMchId());
        merchantVo.setFpriority(entity.getPriority());
        String currentUserId = getCurrentUserId();
        merchantVo.setFselected(entity.getIsSelect());
        int userId = systemUserMapper.findIdByAccount(currentUserId);
        merchantVo.setFlastEditorId(userId+"");
        try {
            entity.setLastEditId(userId);
            log.info("request : 修改商户信息  . parameter : 商户信息[{}]",entity);
            ResponseMsg<Object> responseMsg = merFeignService.UpdateMerById(merchantVo);
            if(responseMsg.getIsSuccess()){
                String s = responseMsg.getData().toString();
                entity.setMerCenterId(Integer.parseInt(s));
                return merBiz.updateMerchantsInFo(entity,merType);
            }else{
                return new ObjectRestResponse<>().rel(Response.FAILURE).msg(responseMsg.getMessage());
            }
        } catch (Exception e) {
            log.error("修改商户信息异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * 功能描述:  获取类型列表
     *
     * @auther: bin.yang
     * @date: 2018/9/21 下午2:41
     */
    @RequestMapping(value = "/listType" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Types> listType (){
        try {
            log.info("request : 获取类型列表  . parameter : 无[]");
            return typeBiz.selectTypeListAll();
        } catch (Exception e) {
            log.error("类型列表加载异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }
}
