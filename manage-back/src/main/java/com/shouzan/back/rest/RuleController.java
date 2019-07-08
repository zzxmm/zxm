package com.shouzan.back.rest;

import com.shouzan.back.biz.RuleBiz;
import com.shouzan.back.biz.impl.RuleBizImpl;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.Coupon;
import com.shouzan.back.entity.PurchaseRule;
import com.shouzan.back.entity.SellRule;
import com.shouzan.back.entity.operate.Operate;
import com.shouzan.back.util.DateUtil;
import com.shouzan.common.msg.ObjectRestResponse;
import com.shouzan.common.rest.BaseController;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Time;
import java.util.*;

/**
 * @Author: bin.yang
 * @Date: 2018/12/19 16:00
 * @Description:  售卖规则
 */
@Controller
@RequestMapping("/rule")
public class RuleController extends BaseController<RuleBizImpl, SellRule> {

    @Autowired
    private RuleBiz ruleBiz;

    /**
     * @Description: 效验卡卷规则(商户号卡卷)
     * @param coupon
     * @[param] [coupon]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Coupon>
     * @author:  bin.yang
     * @date:  2018/12/24 5:55 PM
     */
    public ObjectRestResponse<Coupon> validationDateStatus(Coupon coupon) {
        ObjectRestResponse<Coupon> response = new ObjectRestResponse<>();
        List<PurchaseRule> purRuleList = coupon.getPurcRuleList();
        if(purRuleList != null && purRuleList.size() > 0){
            ObjectRestResponse res = validationDatePurcRule(purRuleList);
            if(!res.getRel()){
              return res;
            }
        }

        // 是否售卖
        coupon.setIsSell(Byte.parseByte("1"));
        List<SellRule> sellList = coupon.getSellRuleList();
        if(sellList != null && sellList.size() > 0){
            for (int i = 0; i < sellList.size(); i++) {
                for (int j = 0; j < sellList.size(); j++) {
                    if(i != j){
                        if(sellList.get(i).getSellPeriod() == sellList.get(j).getSellPeriod()){
                            if( ( sellList.get(j).getSellTimeStart().getTime() >= sellList.get(i).getSellTimeStart().getTime()
                                    & sellList.get(j).getSellTimeStart().getTime() <= sellList.get(i).getSellTimeEnd().getTime() )
                                    || ( sellList.get(j).getSellTimeEnd().getTime() >= sellList.get(i).getSellTimeStart().getTime()
                                    & sellList.get(j).getSellTimeEnd().getTime() <= sellList.get(i).getSellTimeEnd().getTime() )){
                                return response.rel(Response.FAILURE).msg("售卖规则 , 错误! 原因: 售卖时间冲突");
                            }
                        }
                    }
                }
                if(sellList.get(i).getSellNumber() < 0){
                    return response.rel(Response.FAILURE).msg("售卖规则 , 错误! 原因: 售卖数量不能小于0");
                }
            }
            if(coupon.getCouponState() == 0){
                Byte week = DateUtil.getWeek();
                Time time = DateUtil.getTime();
                for (SellRule sellRule : sellList) {
                    if(sellRule.getSellPeriod()==week){
                        if(Integer.parseInt(sellRule.getSellTimeStart().toString().replace(":","")) <= Integer.parseInt(time.toString().replace(":",""))
                                && Integer.parseInt(time.toString().replace(":","")) < Integer.parseInt(sellRule.getSellTimeEnd().toString().replace(":",""))){
                            coupon.setIsSell(Byte.parseByte("0"));
                        }
                    }
                }
            }
        }else{
            if(coupon.getCouponState() == 0){
                coupon.setIsSell(Byte.parseByte("0"));
            }
        }
        return response.rel(Response.SUCCESS).result(coupon);
    }

    /**
     * @author: bin.yang
     * @Date: 2019/5/28 11:14 AM
     *
     * @Description: 效验限购规则
     */
    public ObjectRestResponse validationDatePurcRule(List<PurchaseRule> purRuleList) {
        ObjectRestResponse response = new ObjectRestResponse();
        if(purRuleList.size() > 4){
            return response.rel(Response.FAILURE).msg("限购规则 , 错误! 原因: 规则重复 .");
        }else {

            // 快速排序
            Collections.sort(purRuleList, new Comparator<PurchaseRule>() {
                @Override
                public int compare(PurchaseRule o1, PurchaseRule o2) {
                    if(o1.getPurchasePeriod() < o2.getPurchasePeriod()){
                        return -1;
                    }
                    if(o1.getPurchasePeriod() < o2.getPurchasePeriod()){
                        return 0;
                    }
                    return 1;
                }
            });

            // 效验 限购规则是否符合原则
            ArrayList<String> list = new ArrayList<>();
            if(purRuleList.get(0).getPurchasePeriod() == 0){
                for (int i = 0; i < purRuleList.size(); i++) {
                    if(i == 0){
                        if(purRuleList.get(i).getPurchaseNumber() <= 0){
                            return response.rel(Response.FAILURE).msg("限购规则: 错误! 原因: 限购数量不能小于 1 .");
                        }
                    }else {
                        if(purRuleList.get(i).getPurchaseNumber() <= 0){
                            return response.rel(Response.FAILURE).msg("限购规则: 错误! 原因: 限购数量不能小于 1 .");
                        }
                        if(purRuleList.get(i - 1).getPurchasePeriod() == 0){
                            if(purRuleList.get(i).getPurchaseNumber() < purRuleList.get(i-1).getPurchaseNumber()){
                                return response.rel(Response.FAILURE).msg("限购规则: 错误! 原因: 限购原则 : 上架期间 > 单月限购 > 单周限购 > 单日限购 .");
                            }
                        }else {
                            if(purRuleList.get(i).getPurchaseNumber() > purRuleList.get(i-1).getPurchaseNumber()
                                    ||purRuleList.get(i).getPurchaseNumber() < purRuleList.get(0).getPurchaseNumber() ){
                                return response.rel(Response.FAILURE).msg("限购规则: 错误! 原因: 限购原则 : 上架期间 > 单月限购 > 单周限购 > 单日限购 .");
                            }
                        }
                    }
                    list.add(purRuleList.get(i).getPurchasePeriod()+"");
                }
            }else {
                for (int i = 0; i < purRuleList.size(); i++) {
                    if(i == 0){
                        if(purRuleList.get(i).getPurchaseNumber() <= 0){
                            return response.rel(Response.FAILURE).msg("限购规则: 错误! 原因: 限购数量不能小于 1 .");
                        }

                    }else {
                        if(purRuleList.get(i).getPurchaseNumber() <= 0){
                            return response.rel(Response.FAILURE).msg("限购规则: 错误! 原因: 限购数量不能小于 1 .");
                        }
                        if(purRuleList.get(i).getPurchaseNumber() > purRuleList.get(i-1).getPurchaseNumber()){
                            return response.rel(Response.FAILURE).msg("限购规则: 错误! 原因: 限购原则 : 上架期间 > 单月限购 > 单周限购 > 单日限购 .");
                        }
                    }
                    list.add(purRuleList.get(i).getPurchasePeriod()+"");
                }
            }

            // 差集计算 是否有重复数据
            Set<String> set = new HashSet<>(list);
            Collection rs = CollectionUtils.disjunction(list,set);
            List array = new ArrayList<>(rs);
            if(array != null && array.size() > 0){
                return response.rel(Response.FAILURE).msg("限购规则: 错误! 原因: 规则重复 .");
            }
        }
        return response.rel(Response.SUCCESS);
    }

    /**
     * @author: bin.yang
     * @Date: 2019/5/28 11:19 AM
     *
     * @Description: 效验限购规则(H5卡卷)
     */
    public ObjectRestResponse<Operate> validationDateStatus(Operate operate) {
        ObjectRestResponse<Operate> response = new ObjectRestResponse<>();
        if(operate.getPurcRuleList() != null && operate.getPurcRuleList().size() > 0){
            ObjectRestResponse res = validationDatePurcRule(operate.getPurcRuleList());
            if(!res.getRel()){
                return res;
            }
        }
        return response.rel(Response.SUCCESS).result(operate);
    }
}
