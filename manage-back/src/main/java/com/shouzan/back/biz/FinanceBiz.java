package com.shouzan.back.biz;

import com.shouzan.back.entity.ConsumeRecord;
import com.shouzan.back.entity.FinalAccount;
import com.shouzan.back.vo.SearchSatisfy;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/12/10 16:38
 * @Description:  对账单
 */
public interface FinanceBiz {

    int addConsumeRecordList(List<ConsumeRecord> listTemp, int userId);

    int addPurchaseRecordList(List<ConsumeRecord> listTemp, int userId);

    int queryPageCount(SearchSatisfy search);

    List<FinalAccount> queryPageList(SearchSatisfy search);
}
