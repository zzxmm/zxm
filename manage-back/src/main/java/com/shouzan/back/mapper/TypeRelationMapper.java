package com.shouzan.back.mapper;

import org.apache.ibatis.annotations.Param;
/**
 * @Author: bin.yang
 * @Date: 2019/1/23 5:20 PM
 *
 * @Description: 类型关系
 */
public interface TypeRelationMapper {

    int insertMerchantsTypeInfo(@Param("merId")Integer merId, @Param("typeId")String typeId);

    int updateMerchantsInFo(@Param("merId")Integer merId, @Param("typeId")String typeId);
}