package com.shouzan.back.biz;

import com.shouzan.back.entity.integral.IntegralRecord;
import com.shouzan.back.vo.SearchSatisfy;

import java.util.List;

/**
 * @Author: man.z
 * @Date: 2019-05-28 10:18
 *
 * @Description: 积分记录管理
 */
public interface IntegralRecordBiz {

    int queryPageCount(SearchSatisfy search);

    List<IntegralRecord> queryPageList(SearchSatisfy search);

    int queryPageIntegralCount(SearchSatisfy search);

   List<IntegralRecord> queryPageIntegralList(SearchSatisfy search);

}
