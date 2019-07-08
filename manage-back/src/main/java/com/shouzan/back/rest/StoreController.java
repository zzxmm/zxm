package com.shouzan.back.rest;

import com.github.pagehelper.PageHelper;
import com.shouzan.back.biz.MerBiz;
import com.shouzan.back.biz.StoreBiz;
import com.shouzan.back.biz.impl.StoreBizImpl;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.Merchants;
import com.shouzan.back.entity.Store;
import com.shouzan.back.mapper.MerchantsMapper;
import com.shouzan.back.mapper.StoreMapper;
import com.shouzan.back.mapper.SystemUserMapper;
import com.shouzan.back.rpc.MerFeignService;
import com.shouzan.back.rpc.StoreFeignService;
import com.shouzan.back.util.CodeValid;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;
import com.shouzan.common.rest.BaseController;
import com.shouzan.uuc.util.ResponseMsg;
import com.shouzan.uuc.vo.StoreVo;
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
 * @Date: 2018/9/14 16:22
 * @Description:  门店管理
 */
@Controller
@RequestMapping("/store")
@Validated
@Slf4j
public class StoreController extends BaseController<StoreBizImpl, Store> {

    @Autowired
    private StoreFeignService storeFeignService;

    @Autowired
    private MerFeignService merFeignService;

    @Autowired
    private StoreBiz storeBiz;

    @Autowired
    private MerBiz merBiz;

    @Autowired
    private MerchantsMapper merchantsMapper;

    @Autowired
    private SystemUserController systemUserController;

    @Autowired
    private StoreMapper storeMapper;

    /**
     * @Description: (门店信息列表分页查询)
     * @param pageSize
     * @param current
     * @param searchSatisfy
     * @[param] [pageSize, current, searchSatisfy]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.uuc.vo.StoreVo>
     * @author:  bin.yang
     * @date:  2018/10/15 下午12:45
     */
    @RequestMapping(value = "/page" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<StoreVo> page (@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "1") int current,
                                              SearchSatisfy searchSatisfy){
        try {
            log.info("request : 门店列表查询 , 每页显示[{}] , 当前显示[{}] . parameter : [{}]",pageSize,current,searchSatisfy);
            int count = storeBiz.queryPageCount(searchSatisfy);
            PageHelper.startPage(current,pageSize,false);
            List<Store> storeList = storeBiz.queryPageList(searchSatisfy);
            return new ObjectRestResponse<Merchants>().rel(Response.SUCCESS).rows(storeList).total(count);
        } catch (Exception e) {
            log.error("门店分页查询异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * 功能描述:   修改门店营业状态
     *
     * @auther: bin.yang
     * @date: 2018/9/17 下午3:42
     */
    @RequestMapping(value = "/update" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Store> update(@NotNull(message = "选择门店") String id , @NotNull(message = "选择状态") Integer status){
        try {
            String cenId = storeBiz.findCenterIDById(id);
            log.info("request : 修改门店营业状态 , parameter:门店ID[{}] , 修改状态[{}]",id,status);
            ResponseMsg<Object> responseMsg = storeFeignService.updateStrStatus(cenId, status + "");
            if(responseMsg.getIsSuccess()){
                return  storeBiz.updateStatus(cenId,status);
            }else{
                return  new ObjectRestResponse<>().rel(Response.FAILURE).msg(responseMsg.getMessage());
            }
        } catch (Exception e) {
            log.error("门店修改状态异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<Store>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * 功能描述:   通过ID查询门店详细信息
     *
     * @auther: bin.yang
     * @date: 2018/9/17 下午3:42
     */
    @RequestMapping(value = "/findInfo/{id}" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Store> findInfo(@PathVariable int id){
        log.info("request : 门店详细查询  . parameter : 门店ID[{}]",id);
        try {
            Store info = storeBiz.findInfoById(id);
            return new ObjectRestResponse<Store>().result(info).rel(Response.SUCCESS);
        } catch (Exception e) {
            log.error("门店详细查询异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * 功能描述:   新增门店信息
     *
     * @auther: bin.yang
     * @date: 2018/9/17 下午3:42
     */
    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Store> addInfo(@Valid Store entity){
        StoreVo storeVo = new StoreVo();
        storeVo.setFstrName(entity.getStoreName());
        storeVo.setServiceNumber(entity.getServicenum()+"");
        storeVo.setFstrState(entity.getStoreState());
        storeVo.setFadress(entity.getAddress());
        if(entity.getMerCenterId() != null){
            storeVo.setFmerId(Long.parseLong(entity.getMerCenterId()+""));
        }
        storeVo.setBusinessTime(entity.getBusinessHours());
        int userId = systemUserController.getUserId();
        storeVo.setFcreatorId(userId+"");
        log.info("request : 新增门店信息 , parameter : 门店信息[{}]",entity);
        try {
            entity.setCreatorId(userId);
            ObjectRestResponse<Store> response = new CodeValid().validationLatLng(entity);
            if(response.getRel()){
                ResponseMsg<Object> responseMsg = storeFeignService.saveStore(storeVo);
                if(responseMsg.getIsSuccess() && responseMsg.getData() != null){
                    String s = responseMsg.getData().toString();
                    entity.setStoreCenterId(Integer.parseInt(s));
                    return storeBiz.addInfo(entity);
                }else{
                    return new ObjectRestResponse<>().rel(Response.FAILURE).msg(responseMsg.getMessage());
                }
            }else{
                return response;
            }
        } catch (Exception e) {
            log.error("新增门店信息异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * 功能描述:   修改门店信息
     *
     * @auther: bin.yang
     * @date: 2018/9/17 下午3:42
     */
    @RequestMapping(value = "/updateInfo" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Store> updateInfo(@Valid Store entity){
        Store infoById = storeBiz.findInfoById(entity.getId());
        StoreVo storeVo = new StoreVo();
        storeVo.setStoreId(Long.parseLong(infoById.getStoreCenterId()+""));
        storeVo.setFstrName(entity.getStoreName());
        storeVo.setServiceNumber(entity.getServicenum()+"");
        storeVo.setFstrState(entity.getStoreState());
        storeVo.setFadress(entity.getAddress());
        if(entity.getMerCenterId() != null){
            storeVo.setFmerId(Long.parseLong(entity.getMerCenterId()+""));
        }
        storeVo.setBusinessTime(entity.getBusinessHours());
        int userId = systemUserController.getUserId();
        storeVo.setFlastEditorId(userId+"");
        log.info("request : 修改门店信息 , parameter : 门店信息[{}]",entity);
        try {
            entity.setLastEditId(userId);
            ObjectRestResponse<Store> response = new CodeValid().validationLatLng(entity);
            if(response.getRel()){
                ResponseMsg<Object> responseMsg = storeFeignService.UpdateStoreById(storeVo);
                if(responseMsg.getIsSuccess() && responseMsg.getData() != null){
                    return storeBiz.updateInfo(entity);
                }else{
                    return new ObjectRestResponse<>().rel(Response.FAILURE).msg(responseMsg.getMessage());
                }
            }else{
                return response;
            }
        } catch (Exception e) {
            log.error("修改门店信息异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * 功能描述:   通过ID删除门店
     *
     * @auther: bin.yang
     * @date: 2018/9/17 下午3:42
     */
    @RequestMapping(value = "/delete" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Store> delete(@NotNull(message = "选择门店") int id){
        try {
            Store info = storeMapper.findInfoById(id);
            ResponseMsg<Object> responseMsg = storeFeignService.delStrsById(Long.parseLong(info.getStoreCenterId() + ""));
            log.info("request : 删除门店信息 , parameter : 门店ID[{}]",id);
            if(responseMsg.getIsSuccess()){
                return storeBiz.deleteStoreById(id);
            }else{
                return new ObjectRestResponse<>().rel(Response.FAILURE).msg(responseMsg.getMessage());
            }
        } catch (Exception e) {
            log.error("删除门店信息异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * 功能描述:   所属商家列表查询
     *
     * @auther: bin.yang
     * @date: 2018/9/17 下午3:42
     */
    @RequestMapping(value = "/listMer" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Merchants> listMer(){
        try {
            log.info("request : 所属商家列表查询 , parameter : 无");
            return merBiz.selectMerList();
        } catch (Exception e) {
            log.error("商家列表加载异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }
}
