package com.shouzan.back.biz.impl;

import com.shouzan.back.biz.RuleBiz;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.Coupon;
import com.shouzan.back.entity.PurchaseRule;
import com.shouzan.back.entity.SellRule;
import com.shouzan.back.entity.operate.Operate;
import com.shouzan.back.mapper.PurchaseRuleMapper;
import com.shouzan.back.mapper.SellRuleMapper;
import com.shouzan.common.biz.BaseBiz;
import com.shouzan.common.msg.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/12/19 16:05
 * @Description:  售卖限购规则
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class RuleBizImpl extends BaseBiz<SellRuleMapper,SellRule> implements RuleBiz {

    @Autowired
    private PurchaseRuleMapper purchaseRuleMapper;

    @Autowired
    private SellRuleMapper sellRuleMapper;

    /**
     * @Description: (添加限购售卖规则)
     * @param entity
     * @[param] [entity]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Coupon>
     * @author:  bin.yang
     * @date:  2018/12/24 5:54 PM
     */
    @Override
    public ObjectRestResponse<Coupon> addRuleList(Coupon entity) {
        List<PurchaseRule> purList;
        if((purList = entity.getPurcRuleList()) != null && purList.size() > 0){
            for (PurchaseRule purchaseRule : purList) {
                purchaseRule.setPurchaseGoods(entity.getId());
                purchaseRule.setRuleType(Byte.parseByte("0") );
                purchaseRuleMapper.addPurchaseRule(purchaseRule);
            }
        }
        List<SellRule> sellList;
        if((sellList = entity.getSellRuleList()) != null && sellList.size() > 0){
            for (SellRule sellRule : sellList) {
                sellRule.setSellGoods(entity.getId());
                sellRule.setRuleType(Byte.parseByte("0") );
                sellRuleMapper.addSellRule(sellRule);
            }
        }
        return new ObjectRestResponse<>().rel(Response.SUCCESS).msg("卡卷: "+entity.getCouponName()+" 添加成功.");
    }

    /**
     * @Description: (修改限购售卖规则)
     * @param entity
     * @[param] [entity]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Coupon>
     * @author:  bin.yang
     * @date:  2018/12/24 5:54 PM
     */
    @Override
    public ObjectRestResponse<Coupon> updateRuleList(Coupon entity) {
        List<PurchaseRule> purList;
        String pid="0";
        if((purList = entity.getPurcRuleList()) != null && purList.size() > 0){
            for (PurchaseRule purchaseRule : purList) {
                if(purchaseRule.getId() != null){
                    purchaseRule.setPurchaseGoods(entity.getId());
                    purchaseRule.setRuleType(Byte.parseByte("0") );
                    purchaseRuleMapper.updatePurchaseRule(purchaseRule);
                    pid += ","+purchaseRule.getId();
                }else{
                    purchaseRule.setPurchaseGoods(entity.getId());
                    purchaseRule.setRuleType(Byte.parseByte("0") );
                    purchaseRuleMapper.addPurchaseRule(purchaseRule);
                    pid += ","+purchaseRule.getId();
                }
            }
            purchaseRuleMapper.deletePurcRull(pid,entity.getId());
        }else{
            purchaseRuleMapper.deletePurcRull(pid,entity.getId());
        }
        List<SellRule> sellList;
        String sid="0";
        if((sellList = entity.getSellRuleList()) != null && sellList.size() > 0){
            for (SellRule sellRule : sellList) {
                if(sellRule.getId() > -1){
                    sellRule.setSellGoods(entity.getId());
                    sellRule.setRuleType(Byte.parseByte("0"));
                    sellRuleMapper.updateSellRule(sellRule);
                    sid += ","+sellRule.getId();
                }else{
                    sellRule.setSellGoods(entity.getId());
                    sellRule.setRuleType(Byte.parseByte("0"));
                    sellRuleMapper.addSellRule(sellRule);
                    sid += ","+sellRule.getId();
                }
            }
            sellRuleMapper.deleteSellRule(sid,entity.getId());
        }else {
            sellRuleMapper.deleteSellRule(sid,entity.getId());
        }
        return new ObjectRestResponse<>().rel(Response.SUCCESS).msg("卡卷: "+entity.getCouponName()+" 修改信息成功.");
    }

    /**
     * @Description: (查询卡卷所有规则)
     * @param coupon
     * @[param] [coupon]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Coupon>
     * @author:  bin.yang
     * @date:  2018/12/24 5:55 PM
     */
    @Override
    public ObjectRestResponse<Coupon> selectCouponRuleList(Coupon coupon) {
        List<PurchaseRule> purList = purchaseRuleMapper.queryPurRuleListByCouId(coupon.getId());
        List<SellRule> sellList = sellRuleMapper.querySelRuleListByCouId(coupon.getId());
        coupon.setPurcRuleList(purList);
        coupon.setSellRuleList(sellList);
        return new ObjectRestResponse<>().rel(Response.SUCCESS).result(coupon);
    }

    /**
     * @Description: (运营H5添加限购规则)
     * @param operate
     * @[param] [operate]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/3/29 6:18 PM
     */
    @Override
    public ObjectRestResponse addOperateRuleList(Operate operate) {
        List<PurchaseRule> purList;
        if((purList = operate.getPurcRuleList()) != null && purList.size() > 0){
            for (PurchaseRule purchaseRule : purList) {
                purchaseRule.setPurchaseGoods(operate.getId());
                purchaseRule.setRuleType(Byte.parseByte("2"));
                purchaseRuleMapper.addPurchaseRule(purchaseRule);
            }
        }
        return new ObjectRestResponse<>().rel(Response.SUCCESS).msg("运营活动: "+operate.getCardName()+" 添加成功.");
    }

    /**
     * @Description: (修改运营H5限购规则)
     * @param operate
     * @[param] [operate]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/3/29 6:25 PM
     */
    @Override
    public ObjectRestResponse updateOperateRuleList(Operate operate) {
        List<PurchaseRule> purList;
        String pid="0";
        if((purList = operate.getPurcRuleList()) != null && purList.size() > 0){
            for (PurchaseRule purchaseRule : purList) {
                if(purchaseRule.getId() != null){
                    purchaseRule.setPurchaseGoods(operate.getId());
                    purchaseRule.setRuleType(Byte.parseByte("2") );
                    purchaseRuleMapper.updatePurchaseRule(purchaseRule);
                    pid += ","+purchaseRule.getId();
                }else{
                    purchaseRule.setPurchaseGoods(operate.getId());
                    purchaseRule.setRuleType(Byte.parseByte("2") );
                    purchaseRuleMapper.addPurchaseRule(purchaseRule);
                    pid += ","+purchaseRule.getId();
                }
            }
            purchaseRuleMapper.deletePurcRull(pid,operate.getId());
        }else{
            purchaseRuleMapper.deletePurcRull(pid,operate.getId());
        }
        return new ObjectRestResponse<>().rel(Response.SUCCESS).msg("运营活动: "+operate.getCardName()+" 修改信息成功.");
    }

    /**
     * @Description: (查询H5规则详细)
     * @param operate
     * @[param] [operate]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/3/29 6:25 PM
     */
    @Override
    public ObjectRestResponse selectOperateRuleList(Operate operate) {
        List<PurchaseRule> purList = purchaseRuleMapper.queryPurRuleListByCouId(operate.getId());
        operate.setPurcRuleList(purList);
        return new ObjectRestResponse<>().rel(Response.SUCCESS).result(operate);
    }
}
