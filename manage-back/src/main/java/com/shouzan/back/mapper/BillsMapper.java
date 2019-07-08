package com.shouzan.back.mapper;

import com.shouzan.back.entity.Bank;
import com.shouzan.back.entity.Bills;
import com.shouzan.back.entity.BillsOverview;
import com.shouzan.back.entity.Merchants;
import com.shouzan.back.vo.SearchSatisfy;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.List;
/**
 * @Author: bin.yang
 * @Date: 2019/1/23 5:18 PM
 *
 * @Description:   字体台账
 */
public interface BillsMapper extends Mapper<Bills> {

    int queryPageCount(SearchSatisfy search);

    List<Bills> queryPageList(SearchSatisfy search);

    List<Bank> selectBankList();

    List<Merchants> selectMerList();

    Bills selectBillsInfoById(int id);

    BillsOverview queryBillsOverview(SearchSatisfy search);

    BigDecimal queryBillsBalance();

    int addTransactionRecord(Bills bills);

    List<Bills> selectInfoAll(SearchSatisfy searchSatisfy);

    Bills selectMerBalanceById(int id);
}