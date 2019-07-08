package com.shouzan.task.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @Author: bin.yang
 * @Date: 2018/10/24 11:39
 * @Description:
 */
public interface AdvertMapper {

    int advertTimingLower(@Param("localeDate") String localeDate);

    int advertTimingUpper(@Param("localeDate") String localeDate);
}
