package com.shouzan.back.mapper;

import com.shouzan.back.entity.Coupon;
import com.shouzan.back.entity.CouponStore;
import com.shouzan.back.vo.SearchSatisfy;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
/**
 * @Author: bin.yang
 * @Date: 2019/1/23 5:18 PM
 *
 * @Description:   商户号卡卷
 */
public interface CouponMapper extends Mapper<Coupon> {

    int queryPageCount(SearchSatisfy search);

    List<Coupon> queryPageList(SearchSatisfy search);

    Coupon couponInfoById(int id);

    int addInfo(Coupon entity);

    int addCouponStoreInfo(List<CouponStore> list);

    int updateCouponInfo(Coupon entity);

    int deleteStoreById(Integer id);

    List<Coupon> selectCouponAllInfo();

    int updateCouponStatus(@Param("id") String id,@Param("status") Integer status);

    List<Coupon> selectCouponByMerId(@Param("id") String id);

    List<Coupon> selectCouponByBankId(@Param("id") String id);

    int updateIsSellDisp(@Param("id")String id);

    int updateNoSellDisp(@Param("id")Integer id,@Param("sell") Byte sell);
}