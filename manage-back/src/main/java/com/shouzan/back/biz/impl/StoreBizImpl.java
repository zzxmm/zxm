package com.shouzan.back.biz.impl;

import com.shouzan.back.biz.StoreBiz;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.Store;
import com.shouzan.back.mapper.CouponStoreMapper;
import com.shouzan.back.mapper.StoreMapper;
import com.shouzan.back.util.CodeValid;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.biz.BaseBiz;
import com.shouzan.common.msg.ObjectRestResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/9/14 16:24
 * @Description:   门店管理
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class StoreBizImpl extends BaseBiz<StoreMapper, Store> implements StoreBiz {

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private CouponStoreMapper couponStoreMapper;

    /**
     * @Description: (查询所属商户的所有门店信息)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Store>
     * @author:  bin.yang
     * @date:  2018/12/8 6:33 PM
     */
    @Override
    public ObjectRestResponse<Store> selectStoreList(int id) {
        return new ObjectRestResponse<Store>().rel(Response.SUCCESS).rows(storeMapper.selectStoreAll(id));
    }

    /**
     * @Description: (查询总条数)
     * @param searchSatisfy
     * @[param] [searchSatisfy]
     * @return int
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:36
     */
    @Override
    public int queryPageCount(SearchSatisfy searchSatisfy) {
        if (StringUtils.isNotBlank(searchSatisfy.getKeywords())){
            searchSatisfy.setKeywords("%" + searchSatisfy.getKeywords().trim() +"%");
        }else{
            searchSatisfy.setKeywords(null);
        }
        if (!StringUtils.isNotBlank(searchSatisfy.getName())){
            searchSatisfy.setName(null);
        }
        return storeMapper.queryPageCount(searchSatisfy);
    }

    /**
     * @Description: (查询数据集合)
     * @param searchSatisfy
     * @[param] [searchSatisfy]
     * @return java.util.List<com.shouzan.back.entity.Store>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:37
     */
    @Override
    public List<Store> queryPageList(SearchSatisfy searchSatisfy) {
        if (StringUtils.isNotBlank(searchSatisfy.getKeywords())){
            searchSatisfy.setKeywords("%" + searchSatisfy.getKeywords().trim() +"%");
        }else{
            searchSatisfy.setKeywords(null);
        }
        if (!StringUtils.isNotBlank(searchSatisfy.getName())){
            searchSatisfy.setName(null);
        }
        return storeMapper.queryPageList(searchSatisfy);
    }

    /**
     * @Description: (修改状态)
     * @param id
     * @param status
     * @[param] [id, status]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Store>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:51
     */
    @Override
    public ObjectRestResponse<Store> updateStatus(String id, Integer status) {
        int i = storeMapper.updateStatus(id, status);
        if(i > 0){
            String couAr = couponStoreMapper.selectCouponByStore(id);
            String[] split = couAr.split(",");
            for (int j = 0; j < split.length; j++) {
                String ranges = couponStoreMapper.selectStorerangesById(split[j]);
                couponStoreMapper.updateCouponrange(Integer.parseInt(split[j]), ranges);
            }
            return new ObjectRestResponse<>().rel(Response.SUCCESS).msg("修改门店状态成功");
        }else {
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("修改门店状态失败");
        }
    }

    /**
     * @Description: (通过ID查询详细信息)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Store>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:46
     */
    @Override
    public ObjectRestResponse<Store> findInfo(int id) {
        return new ObjectRestResponse<Store>().rel(Response.SUCCESS).result(storeMapper.findInfo(id));
    }

    /**
     * @Description: (添加门店信息)
     * @param entity
     * @[param] [entity]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Store>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:51
     */
    @Override
    public ObjectRestResponse<Store> addInfo(Store entity) {
        return CodeValid.CodeMsg(storeMapper.addInfo(entity),"添加");
    }

    /**
     * @Description: (修改门店信息)
     * @param entity
     * @[param] [entity]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Store>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:51
     */
    @Override
    public ObjectRestResponse<Store> updateInfo(Store entity) {
        Store store = storeMapper.findInfo(entity.getId());
        if(!store.getCity().equals(entity.getCity())){
            int i = storeMapper.updateInfo(entity);
            if(i > 0){
                String ids = couponStoreMapper.selectCouponByStore(entity.getId() + "");
                if(ids != null && !"".equals(ids)){
                    String[] split = ids.split(",");
                    for (String s : split) {
                        int id = Integer.parseInt(s);
                        String range = couponStoreMapper.selectStoreAllById(id);
                        couponStoreMapper.updateCouponrange(id,range);
                    }
                }
                return new ObjectRestResponse<>().rel(true).msg("门店信息修改成功");
            }else{
                return new ObjectRestResponse<>().rel(false).msg("门店信息修改失败");
            }
        }else {
            int i = storeMapper.updateInfo(entity);
            return CodeValid.CodeMsg(i,"修改");
        }
    }

    /**
     * @Description: (删除门店)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Store>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:52
     */
    @Override
    public ObjectRestResponse<Store> deleteStoreById(int id) {
        int i = storeMapper.deleteStoreById(id);
        if(i > 0){
            String couAr = couponStoreMapper.selectCouponByStore(id+"");
            storeMapper.deleteStoreCoupon(id);
            if(couAr != null){
                String[] split = couAr.split(",");
                for (int j = 0; j < split.length; j++) {
                    String ranges = couponStoreMapper.selectStorerangesById(split[j]);
                    couponStoreMapper.updateCouponrange(Integer.parseInt(split[j]), ranges);
                }
            }
            return new ObjectRestResponse<>().rel(Response.SUCCESS).msg("门店删除成功");
        }else{
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("门店删除失败");
        }
    }

    /**
     * @Description: (通过用户中心门店ID查询门店详细信息)
     * @param id
     * @[param] [id]
     * @return com.shouzan.back.entity.Store
     * @author:  bin.yang
     * @date:  2018/12/19 11:06 AM
     */
    @Override
    public Store findInfoByCenterId(int id) {
        return storeMapper.findInfoByCenterId(id);
    }

    /**
     * @Description: (通过门店ID 查询用户中心商户ID)
     * @param id
     * @[param] [id]
     * @return java.lang.String
     * @author:  bin.yang
     * @date:  2018/12/19 11:06 AM
     */
    @Override
    public String findCenterIDById(String id) {
        return storeMapper.findCenterIDById(id);
    }

    /**
     * @Description: (通过ID查询详细信息)
     * @param id
     * @[param] [id]
     * @return com.shouzan.back.entity.Store
     * @author:  bin.yang
     * @date:  2018/12/19 11:07 AM
     */
    @Override
    public Store findInfoById(int id) {
        return storeMapper.findInfoById(id);
    }

    /**
     * @Description: (查询所属商户门店ID字符集)
     * @param  id,region
     * @[param] [id,region]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2018/12/19 11:07 AM
     */
    @Override
    public ObjectRestResponse storeIdListAll(int id, String region) {
        if (!StringUtils.isNotBlank(region)) {
            region = null;
        }
        String all = storeMapper.storeIdListAll(id,region);
        return new ObjectRestResponse().rel(Response.SUCCESS).result(all);
    }
}
