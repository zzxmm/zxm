package com.shouzan.back.mapper;

import com.shouzan.back.entity.ConsumeRecord;
import com.shouzan.back.entity.FinalAccount;
import com.shouzan.back.vo.SearchSatisfy;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
/**
 * @Author: bin.yang
 * @Date: 2019/1/23 5:19 PM
 *
 * @Description:  对账单
 */
public interface FinanceMapper extends Mapper<ConsumeRecord> {

    int addConsumeRecordList(List<ConsumeRecord> listTemp);

    int addPurchaseRecordList(List<ConsumeRecord> listTemp);

    int queryPageCount(SearchSatisfy search);

    List<FinalAccount> queryPageList(SearchSatisfy search);
}