package com.shouzan.back.mapper;

import com.shouzan.back.entity.Coupon;
import com.shouzan.back.entity.operate.Operate;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName: com.shouzan.back.mapper.ZoneMapper
 * @Author: bin.yang
 * @Date: 2019/4/18 10:24
 * @Description: TODO
 */
public interface ZoneMapper {

    Coupon findCardStockInfoById(Integer id);

    int increaseStockById(@Param("id") Integer id, @Param("inc")Integer increaseValue);

    int reduceStockById(@Param("id")Integer id, @Param("red")Integer reduce);

    Operate findOptCardStockInfoById(Integer id);

    int increaseOptStockById(@Param("id") Integer id, @Param("inc")Integer increaseValue);

    int reduceOptStockById(@Param("id")Integer id, @Param("red")Integer reduce);

    int updateCardStatus(Coupon coupon);

    int updateOperateCardStatus(@Param("id")Integer id, @Param("status")int status);

}
