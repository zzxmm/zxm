package com.shouzan.back.biz;

import com.shouzan.back.entity.Coupon;
import com.shouzan.back.entity.operate.Operate;
import com.shouzan.common.msg.ObjectRestResponse;

/**
 * @Author: bin.yang
 * @Date: 2018/12/19 16:04
 * @Description:  售卖限购规则
 */
public interface RuleBiz {

    ObjectRestResponse<Coupon> addRuleList(Coupon entity);

    ObjectRestResponse<Coupon> updateRuleList(Coupon entity);

    ObjectRestResponse<Coupon> selectCouponRuleList(Coupon coupon);

    ObjectRestResponse addOperateRuleList(Operate operate);

    ObjectRestResponse updateOperateRuleList(Operate operate);

    ObjectRestResponse selectOperateRuleList(Operate operate);
}