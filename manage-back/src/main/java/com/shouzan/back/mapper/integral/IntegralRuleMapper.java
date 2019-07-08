package com.shouzan.back.mapper.integral;


import com.shouzan.back.entity.integral.IntegralRule;
import com.shouzan.back.vo.SearchSatisfy;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IntegralRuleMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(IntegralRule record);

    IntegralRule selectById(Integer id);

    int updateByIdSelective(IntegralRule record);

    int queryPageCount(SearchSatisfy search);

    List queryPageList(SearchSatisfy search);

    int updateIntegralEnableStatus(@Param("status")Integer status, @Param("userId")Integer userId);

    int findIntegralEnableStatus();
}