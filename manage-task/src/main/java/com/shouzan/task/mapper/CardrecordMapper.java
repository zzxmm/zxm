package com.shouzan.task.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @Author: bin.yang
 * @Date: 2018/12/16 21:43
 * @Description:
 */
public interface CardrecordMapper {

    int updateCardrecorStatus(@Param("localeDate") String localeDate);

}
