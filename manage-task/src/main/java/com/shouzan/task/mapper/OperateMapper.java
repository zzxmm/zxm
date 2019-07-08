package com.shouzan.task.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @Author: bin.yang
 * @Date: 2019/1/22 11:28
 * @Description:
 */
public interface OperateMapper {

    int LowerShelfToTime(@Param("localeDate")String localeDate);

    int UpperShelfToTime(@Param("localeDate")String localeDate);
}
