package com.shouzan.back.biz.impl;

import com.shouzan.back.biz.MerBiz;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.Coupon;
import com.shouzan.back.entity.Merchants;
import com.shouzan.back.mapper.*;
import com.shouzan.back.rpc.StoreFeignService;
import com.shouzan.back.util.CodeValid;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.biz.BaseBiz;
import com.shouzan.common.msg.ObjectRestResponse;
import com.shouzan.uuc.util.ResponseMsg;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.cms.bc.BcKeyTransRecipientInfoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/9/14 15:37
 * @Description:  商户管理
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class MerBizImpl extends BaseBiz<MerchantsMapper,Merchants> implements MerBiz {

    @Autowired
    private MerchantsMapper merchantsMapper;

    @Autowired
    private TypeRelationMapper typeRelationMapper;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private StoreFeignService storeFeignService;

    @Autowired
    private StoreMapper storeMapper;

    /**
     * @Description: (查询所有商户信息)
     * @param
     * @[param] []
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Merchants>
     * @author:  bin.yang
     * @date:  2018/12/8 6:31 PM
     */
    @Override
    public ObjectRestResponse<Merchants> selectMerList() {
        return new ObjectRestResponse<Merchants>().rel(Response.SUCCESS).rows(merchantsMapper.selectMerAll());
    }

    /**
     * @Description: (查询总条数)
     * @param searchSatisfy
     * @[param] [searchSatisfy]
     * @return int
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:41
     */
    @Override
    public int queryPageCount(SearchSatisfy searchSatisfy) {
        if (StringUtils.isNotBlank(searchSatisfy.getKeywords())){
            searchSatisfy.setKeywords("%" + searchSatisfy.getKeywords().trim() +"%");
        }else{
            searchSatisfy.setKeywords(null);
        }
        return merchantsMapper.queryPageCount(searchSatisfy);
    }

    /**
     * @Description: (查询数据集合)
     * @param searchSatisfy
     * @[param] [searchSatisfy]
     * @return java.util.List<com.shouzan.back.entity.Merchants>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:37
     */
    @Override
    public List<Merchants> queryPageList(SearchSatisfy searchSatisfy) {
        if (StringUtils.isNotBlank(searchSatisfy.getKeywords())){
            searchSatisfy.setKeywords("%" + searchSatisfy.getKeywords().trim() +"%");
        }else{
            searchSatisfy.setKeywords(null);
        }
        return merchantsMapper.queryPageList(searchSatisfy);
    }

    /**
     * @Description: (通过ID查询详细信息)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Merchants>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:46
     */
    @Override
    public ObjectRestResponse<Merchants> selectMerInfoById(int id) {
        return new ObjectRestResponse<Merchants>().result(merchantsMapper.selectMerInfoById(id)).rel(Response.SUCCESS);
    }

    /**
     * @Description: (修改状态)
     * @param id
     * @param status
     * @[param] [id, status]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Merchants>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:50
     */
    @Override
    public ObjectRestResponse<Merchants> updateMerchantsStatus(String id, Integer status) {
        if(status == 0){
            int i = merchantsMapper.updateMerchantsStatus(id, status);
            if(i > 0){
                String idStr = merchantsMapper.queryIdByCenterId(id);
                String[] split = idStr.split(",");
                for (int j = 0; j < split.length; j++) {
                    List<Coupon> coupons = couponMapper.selectCouponByMerId(split[j]);
                    if(coupons != null && coupons.size() > 0){
                        for (Coupon coupon : coupons) {
                            couponMapper.updateCouponStatus(coupon.getId()+"",3);
                            activityMapper.updateStatusByCoupon(coupon.getId(),1);
                        }
                    }
                    String stoId = storeMapper.selectCenterIdById(split[j]);
                    if(stoId != null){
                        storeFeignService.updateStrStatus(stoId, "1");
                    }
                    int s = storeMapper.updateStatusByMerId(split[j]);
                }
                return new ObjectRestResponse<Merchants>().rel(Response.SUCCESS).msg("修改商家状态成功");
            }else{
                return new ObjectRestResponse<Merchants>().rel(Response.FAILURE).msg("修改商家状态失败");
            }
        }else{
            return CodeValid.CodeMsg(merchantsMapper.updateMerchantsStatus(id,status),"修改商家状态");
        }
    }

    /**
     * @Description: (新增商户信息)
     * @param entity
     * @param typeId
     * @[param] [entity, typeId]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Merchants>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:50
     */
    @Override
    public ObjectRestResponse<Merchants> insertMerchantsInFo(Merchants entity, String typeId) {
        int flag = merchantsMapper.insertMerchantsInFo(entity);
        if(flag > 0){
            return CodeValid.CodeMsg(typeRelationMapper.insertMerchantsTypeInfo(entity.getId(),typeId),"添加");
        }
        return new ObjectRestResponse<>().rel(Response.FAILURE).msg("添加失败");
    }

    /**
     * @Description: (修改商户信息)
     * @param entity
     * @param typeId
     * @[param] [entity]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Merchants>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:50
     */
    @Override
    public ObjectRestResponse<Merchants> updateMerchantsInFo(Merchants entity, String typeId) {
        int flag = merchantsMapper.updateMerchantsInFo(entity);
        if(flag > 0){
            int merId = merchantsMapper.findIdByCenterId(entity.getId());
            return CodeValid.CodeMsg(typeRelationMapper.updateMerchantsInFo(merId,typeId),"修改");
        }else{
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("修改失败");
        }
    }

    /**
     * @Description: (通过用户中心商户ID查询商户信息)
     * @param s
     * @[param] [s]
     * @return int
     * @author:  bin.yang
     * @date:  2018/12/19 11:09 AM
     */
    @Override
    public int selectMerInfoByCenterId(String s) {
        return merchantsMapper.selectMerInfoByCenterId(s);
    }
}
