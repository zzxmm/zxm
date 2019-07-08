package com.shouzan.back.biz.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.shouzan.back.biz.CouponBiz;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.*;
import com.shouzan.back.mapper.*;
import com.shouzan.back.rest.CouponController;
import com.shouzan.back.rest.RuleController;
import com.shouzan.back.util.CodeValid;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.biz.BaseBiz;
import com.shouzan.common.msg.ObjectRestResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.response.IntervalFacet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author: bin.yang
 * @Date: 2018/9/13 09:58
 * @Description:  商户号卡卷
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CouponBizImpl extends BaseBiz<CouponMapper,Coupon> implements CouponBiz {

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private CouponStoreMapper couponStoreMapper;

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private MerchantsMapper merchantsMapper;

    @Autowired
    private BankMapper bankMapper;

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private CouponController couponController;

    @Autowired
    private RuleController ruleController;
    /**
     * @Description: (查询总条数)
     * @param search
     * @[param] [search]
     * @return int
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:41
     */
    @Override
    public int queryPageCount(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() +"%");
        }else{
            search.setKeywords(null);
        }
        return couponMapper.queryPageCount(search);
    }

    /**
     * @Description: (查询数据集合)
     * @param search
     * @[param] [search]
     * @return java.util.List<com.shouzan.back.entity.Coupon>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:37
     */
    @Override
    public List<Coupon> queryPageList(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() +"%");
        }else{
            search.setKeywords(null);
        }
        List<Coupon> coupons = couponMapper.queryPageList(search);
        return coupons;
    }

    /**
     * @Description: (通过ID查询详细信息)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Coupon>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:46
     */
    @Override
    public Coupon couponInfoById(int id) {
        Coupon coupon = couponMapper.couponInfoById(id);
        if(coupon != null){
            Integer creatorId = coupon.getCreatorId();
            SystemUser systemUser = systemUserMapper.selectSystemUserById(coupon.getCreatorId());
            if(systemUser != null){
                coupon.setCreatorName(systemUser.getUserName());
            }
            SystemUser sysUser = systemUserMapper.selectSystemUserById(coupon.getLastEditId());
            if(sysUser != null){
                coupon.setLastEditName(sysUser.getUserName());
            }
            Bank bank = bankMapper.queryBankInfo(coupon.getBankId());
            if(bank != null){
                coupon.setBank(bank);
            }
            Merchants merchants = merchantsMapper.findInfoById(coupon.getMerId());
            if(merchants != null){
                coupon.setMerchants(merchants);

            }
            List<Store> storeList = storeMapper.selectStoreByCoupon(coupon.getId());
            if(storeList != null){
                coupon.setStoreList(storeList);
            }
        }
        return  coupon;
    }

    /**
     * @Description: (修改状态)
     * @param id
     * @param status
     * @[param] [id, status]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Coupon>
     * @author:  bin.yang
     * @date:  2018/9/29 下午4:22
     */
    @Override
    public ObjectRestResponse<Coupon> updateStatus(String id, Integer status) {
        ObjectRestResponse<Coupon> response = new ObjectRestResponse<>();
        String[] split = id.split(",");
        Date date = new Date();
        boolean flag = true;
        if(status == 0){
            for (int i = 0; i < split.length; i++) {
                String s = split[i];
                Coupon coupon = couponMapper.couponInfoById(Integer.parseInt(split[i]));
                if(date.getTime() < coupon.getUpshelfTime().getTime()){
                    response.msg("卡卷 : "+coupon.getCouponName()+" , 卡卷未到上架时间");
                    response.rel(Response.FAILURE);
                    flag=false;
                    break;
                }else if(date.getTime() > coupon.getDownshelfTime().getTime()){
                    response.msg("卡卷 : "+coupon.getCouponName()+" , 卡卷已过期");
                    response.rel(Response.FAILURE);
                    flag=false;
                    break;
                }else if(coupon.getCouponStocks() <= 0){
                    response.msg("卡卷 : "+coupon.getCouponName()+" , 库存数量不足");
                    response.rel(Response.FAILURE);
                    flag=false;
                    break;
                }else if(merchantsMapper.findInfoByCouponCenter(coupon.getMerId()).getEnableState() == 0){
                    response.msg("卡卷 : "+coupon.getCouponName()+" , 所属商家已停用");
                    response.rel(Response.FAILURE);
                    flag=false;
                    break;
                }else if(bankMapper.queryBankInfo(coupon.getBankId()).getEnableState() == 0){
                    response.msg("卡卷 : "+coupon.getCouponName()+" , 所属银行已停用");
                    response.rel(Response.FAILURE);
                    flag=false;
                    break;
                }
            }
            if(flag){
                int count = couponMapper.updateCouponStatus(id, status);
                if(count > 0){
                    for (int i = 0; i < split.length; i++) {
                        Coupon result = couponController.couInfo(Integer.parseInt(split[i])).getResult();
                        Coupon data = ruleController.validationDateStatus(result).getResult();
                        couponMapper.updateNoSellDisp(data.getId(),data.getIsSell());
                    }
                    return response.rel(Response.SUCCESS).msg("卡卷上架更新成功");
                }else{
                    return response.rel(Response.FAILURE).msg("卡卷状态更新失败");
                }
            }else{
                return response;
            }
        }else{
            for (int i = 0; i < split.length; i++) {
                String s = split[i];
                Coupon coupon = couponMapper.couponInfoById(Integer.parseInt(split[i]));
                if(date.getTime() > coupon.getDownshelfTime().getTime()){
                    response.msg("卡卷 : "+coupon.getCouponName()+" , 卡卷已过期");
                    response.rel(Response.FAILURE);
                    flag=false;
                    break;
                }
            }
            if(flag){
                int count = couponMapper.updateCouponStatus(id, status);
                couponMapper.updateIsSellDisp(id);
                if(count > 0){
                    activityMapper.updateStatusByGooods(id,status);
                    return response.rel(Response.SUCCESS).msg("卡卷下架更新成功");
                }else{
                    return response.rel(Response.FAILURE).msg("卡卷下架失败");
                }
            }else{
                return response;
            }
        }
    }

    /**
     * @Description: (新增卡卷)
     * @param entity
     * @param storeId
     * @[param] [entity, storeId]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Coupon>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:49
     */
    @Override
    public ObjectRestResponse<Coupon> addInfo(Coupon entity, String storeId) {
        if(entity.getCouponValidTimeEnd().getTime() < entity.getCouponValidTimeStart().getTime()
                || entity.getDownshelfTime().getTime() < entity.getUpshelfTime().getTime()){
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("截止时间不能早已开始时间");
        }else if(entity.getUpshelfTime().getTime() < entity.getCouponValidTimeStart().getTime()){
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("卡卷上架时间不能早于卡卷有效期时间");
        }else if(entity.getCouponValidTimeEnd().getTime() < entity.getDownshelfTime().getTime()){
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("卡卷下架时间不能晚于卡卷有效期时间");
        }

        ObjectRestResponse<Coupon> response = new ObjectRestResponse<>();

        //效验积分规则
        ObjectRestResponse restResponse = validationToIntegral(entity);
        if (!restResponse.getRel()){
           return  restResponse;
        }

        //效验卡券状态
        Coupon coupon = addValidationToStatus(entity);

        //效验限购售卖
        Coupon data = ruleController.validationDateStatus(coupon).getResult();

        //新增卡卷
        int flag = couponMapper.addInfo(data);
        if(flag>0) {
            CouponStore couponStore;
            String[] split = storeId.split(",");
            List<CouponStore> list = new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                couponStore = new CouponStore();
                couponStore.setStoreId(Integer.parseInt(split[i]));
                couponStore.setMerId(entity.getMerId());
                couponStore.setCouponId(entity.getId());
                list.add(couponStore);
            }
            //新增卡卷门店关系表
            int exam = couponMapper.addCouponStoreInfo(list);
            if (exam > 0) {
                String range = couponStoreMapper.selectStoreAllById(entity.getId());
                return CodeValid.CodeMsg(couponStoreMapper.updateCouponrange(entity.getId(), range), "添加");
            } else {
                response.rel(Response.FAILURE);
                response.msg("添加失败");
            }
        }
        return response;
    }

    /**
     * @Description: (修改卡卷信息)
     * @param entity
     * @param storeId
     * @[param] [entity, storeId]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Coupon>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:49
     */
    @Override
    public ObjectRestResponse<Coupon> updateCouponInfo(Coupon entity, String storeId) {
        if(entity.getCouponValidTimeEnd().getTime() < entity.getCouponValidTimeStart().getTime()
                || entity.getDownshelfTime().getTime() < entity.getUpshelfTime().getTime()){
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("截止时间不能早已开始时间");
        }else if(entity.getUpshelfTime().getTime() < entity.getCouponValidTimeStart().getTime()){
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("卡卷上架时间不能早于卡卷有效期时间");
        }else if(entity.getCouponValidTimeEnd().getTime() < entity.getDownshelfTime().getTime()){
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("卡卷下架时间不能晚于卡卷有效期时间");
        }

        //效验积分规则
        ObjectRestResponse restResponse = validationToIntegral(entity);
        if (!restResponse.getRel()){
            return  restResponse;
        }

        //效验卡券状态
        Coupon coupon = upValidationToStatus(entity);

        //效验限购售卖规则
        Coupon data = ruleController.validationDateStatus(coupon).getResult();

        /** 修改卡券信息 **/
        int flag = couponMapper.updateCouponInfo(data);
        if(flag > 0) {

            // 差集计算
            List<String> list = couponStoreMapper.findStoreIdByCouId(entity.getId());
            String[] split = storeId.split(",");
            List<String> asList = Lists.newArrayList(split);

            // 获取 add 数据
            SetView<String> setAdd = Sets.difference(Sets.newHashSet(asList), Sets.newHashSet(list));
            List<String> integersa = Lists.newArrayList(setAdd);

            // 获取 del 数据
            SetView<String> setDel = Sets.difference(Sets.newHashSet(list), Sets.newHashSet(asList));
            String ids = setDel.toString().replace("[","").replace("]","");
            // 删除差集
            if(ids != null && !"".equals(ids)){
                couponStoreMapper.deleteByCouId(entity.getId(),ids);
            }

            // 新增入库
            if (integersa != null && integersa.size() > 0) {
                CouponStore couponStore;
                List<CouponStore> couponStores = new ArrayList<>();
                for (String  str: integersa) {
                    couponStore = new CouponStore();
                    couponStore.setStoreId(Integer.parseInt(str));
                    couponStore.setMerId(entity.getMerId());
                    couponStore.setCouponId(entity.getId());
                    couponStores.add(couponStore);
                }
                couponMapper.addCouponStoreInfo(couponStores);
            }
            String range = couponStoreMapper.selectStoreAllById(entity.getId());
            return CodeValid.CodeMsg(couponStoreMapper.updateCouponrange(entity.getId(),range),"修改卡卷信息");
        }else{
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("修改失败");
        }
    }

    /**
     * @Description: (查询所有信息)
     * @param
     * @[param] []
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Coupon>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:49
     */
    @Override
    public ObjectRestResponse<Coupon> selectCouponAllInfo() {
        return new ObjectRestResponse<Coupon>().rows(couponMapper.selectCouponAllInfo()).rel(Response.SUCCESS);
    }

    /**
     * @Description: (效验卡卷状态)
     * @param cou
     * @[param] [cou]
     * @return Coupon
     * @author:  bin.yang
     * @date:  2019/4/19 2:31 PM
     */
    public Coupon addValidationToStatus(Coupon cou) {
        Date date = new Date();
        if(cou.getUpshelfTime().getTime() < date.getTime() && cou.getDownshelfTime().getTime() > date.getTime()){
            if(cou.getCouponStocks() != null && cou.getCouponStocks() > 0){
                cou.setCouponState(Byte.parseByte("0"));
            }else {
                cou.setCouponState(Byte.parseByte("3"));
            }
        }else if(cou.getUpshelfTime().getTime() > date.getTime()){
            if(cou.getCouponStocks() != null && cou.getCouponStocks() > 0){
                cou.setCouponState(Byte.parseByte("1"));
            }else {
                cou.setCouponState(Byte.parseByte("3"));
            }
        }else {
            cou.setCouponState(Byte.parseByte("3"));
        }
        return cou;
    }
    public Coupon upValidationToStatus(Coupon cou) {
        Coupon coupon = couponMapper.couponInfoById(cou.getId());
        Date date = new Date();
        if(cou.getUpshelfTime().getTime() < date.getTime() && cou.getDownshelfTime().getTime() > date.getTime()){
            if(cou.getCouponStocks() != null && cou.getCouponStocks() > 0){
                cou.setCouponState(Byte.parseByte("0"));
            }else {
                cou.setCouponState(Byte.parseByte("3"));
            }
        }else if(cou.getUpshelfTime().getTime() > date.getTime()){
            if(cou.getCouponStocks() != null && cou.getCouponStocks() > 0){
                cou.setCouponState(Byte.parseByte("1"));
            }else {
                cou.setCouponState(Byte.parseByte("3"));
            }
        }else {
            cou.setCouponState(Byte.parseByte("3"));
        }
        return cou;
    }

    /**
     * @Description: 积分校验

     * @[param] [cou]
     * @return com.shouzan.back.entity.Coupon
     * @author:  man.z
     * @date:  2019-05-30 17:37
     */
    public ObjectRestResponse validationToIntegral(Coupon cou){
        Byte isPartakeCash = cou.getIsPartakeCash();
        Byte isIntegral = cou.getIsIntegral();
        if(isPartakeCash==0) {
            Integer useIntegral = cou.getUseIntegral();
            Double integralCash = cou.getIntegralCash();
            String integralSign = cou.getIntegralSign();
            if (useIntegral != 0 && integralCash != 0 && integralSign != null) {
                return new ObjectRestResponse().rel(Response.SUCCESS);
            } else {
                return new ObjectRestResponse().rel(Response.FAILURE).msg("可使用积分,积分可抵现,积分标识均不能为空");
            }

        }else if(isIntegral==0){
            Integer makeIntegral = cou.getMakeIntegral();
            if (makeIntegral!=0){
                return new ObjectRestResponse().rel(Response.SUCCESS);
            }else {
                return new ObjectRestResponse().rel(Response.FAILURE).msg("可获得积分数不能为空");
            }
        }else {
            return new ObjectRestResponse().rel(Response.SUCCESS);
        }
    }
}
