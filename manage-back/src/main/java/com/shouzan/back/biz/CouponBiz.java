package com.shouzan.back.biz;

import com.shouzan.back.entity.Coupon;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/9/13 09:58
 * @Description:  商户号卡卷
 */
public interface CouponBiz {

    int queryPageCount(SearchSatisfy search);

    List<Coupon> queryPageList(SearchSatisfy search);

    Coupon couponInfoById(int id);

    ObjectRestResponse<Coupon> updateStatus(String id, Integer status);

    ObjectRestResponse<Coupon> addInfo(Coupon coupon, String storeId);

    ObjectRestResponse<Coupon> updateCouponInfo(Coupon entity, String storeId);

    ObjectRestResponse<Coupon> selectCouponAllInfo();

}
