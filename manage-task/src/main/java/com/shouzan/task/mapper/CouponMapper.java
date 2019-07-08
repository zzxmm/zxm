package com.shouzan.task.mapper;

import org.apache.ibatis.annotations.Param;

import java.sql.Time;

/**
 * 功能描述:
 * @author: bin.yang
 * @date: 2018/9/13 下午3:12
 */
public interface CouponMapper{

    int couponTimingLower(@Param("localeDate") String localeDate);

    int couponTimingUpper(@Param("localeDate")String localeDate);

    int couponIsSellUpdate(@Param("week")Byte week, @Param("time")Time time);

    int couponNoSellUpdate(@Param("week")Byte week, @Param("time")Time time);

}