package com.shouzan.back.mapper.integral;


import com.shouzan.back.entity.integral.IntegralRecord;
import com.shouzan.back.vo.SearchSatisfy;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IntegralRecordMapper {

    int queryPageCount(SearchSatisfy search);

    List queryPageList(SearchSatisfy search);

    int queryIntegralPageCount(SearchSatisfy search);

    List queruIntegralPageList(SearchSatisfy search);

    IntegralRecord selectIntegralRecord (@Param("orderNo")String orderNo);

}