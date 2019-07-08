package com.shouzan.back.biz;

import com.shouzan.back.entity.integral.IntegralRule;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName: com.shouzan.back.biz.IntegralRuleBiz
 * @Author: shouzan
 * @Date: 2019-05-29 10:46
 * @Description: TODO
 */
public interface IntegralRuleBiz {

    ObjectRestResponse<IntegralRule> selectById(Integer id);

    ObjectRestResponse<IntegralRule> updateByIdInfo(IntegralRule entity);

    int queryPageCount(SearchSatisfy search);

    List queryPageList(SearchSatisfy search);

    ObjectRestResponse updateIntegralEnableStatus(@Param("status")Integer status, @Param("userId")Integer userId);

    ObjectRestResponse findIntegralEnableStatus();



}
