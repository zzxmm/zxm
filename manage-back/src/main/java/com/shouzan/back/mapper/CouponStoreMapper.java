package com.shouzan.back.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2019/1/23 5:19 PM
 *
 * @Description:  卡卷门店关系
 */
public interface CouponStoreMapper {

    String selectStoreAllById(Integer id);

    int updateCouponrange(@Param("id") Integer id, @Param("ranges") String range);

    String selectCouponByStore(@Param("id") String id);

    String selectStorerangesById(@Param("id") String id);

    List<String> findStoreIdByCouId(Integer id);

    int deleteByCouId(@Param("id")Integer id, @Param("ids")String ids);
}