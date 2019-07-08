package com.shouzan.back.biz;

import com.shouzan.back.entity.extend.ExtendExchange;
import com.shouzan.back.entity.extend.ExtendRecord;
import com.shouzan.back.entity.extend.ExtendRule;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2019/2/18 18:12
 * @Description:  分销系统
 */
public interface ExtendBiz {

    int queryPageCount(SearchSatisfy search);

    List<ExtendRule> queryPageList(SearchSatisfy search);

    ObjectRestResponse addExtendRuleInfo(ExtendRule rule);

    ObjectRestResponse updateExtendRuleInfo(ExtendRule rule);

    ObjectRestResponse updateExtendRuleStatus(String id, int status);

    ExtendRule findInfoById(int id);

    int queryPageRecordCount(SearchSatisfy search);

    List<ExtendRecord> queryPageRecordList(SearchSatisfy search);

    int queryPageBonusCount(SearchSatisfy search);

    List<ExtendExchange> queryPageBonusList(SearchSatisfy search);

    ObjectRestResponse findInfoBonusById(int id);

    ObjectRestResponse updateBonusStatus(Integer id, Integer status, String notes, Integer userId);

    ObjectRestResponse updateEnableStatus(Integer status, Integer userId);

    ObjectRestResponse findEnableStatus();

}
