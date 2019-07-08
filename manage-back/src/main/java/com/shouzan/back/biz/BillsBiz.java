package com.shouzan.back.biz;

import com.shouzan.back.entity.Bank;
import com.shouzan.back.entity.Bills;
import com.shouzan.back.entity.BillsOverview;
import com.shouzan.back.entity.Merchants;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/9/27 10:31
 * @Description:  资金台账
 */
public interface BillsBiz {

    int queryPageCount(SearchSatisfy search);

    List<Bills> queryPageList(SearchSatisfy search);

    ObjectRestResponse<Bank> selectBankList();

    ObjectRestResponse<Merchants> selectMerList();

    ObjectRestResponse<Bills> selectBillsInfoById(int id);

    BillsOverview queryBillsOverview(SearchSatisfy search);

    ObjectRestResponse<Bills> incomeTransactionInfo(byte outAccParty, int outAccPartyId, BigDecimal outAccPartyBalance, byte payAccParty, int payAccId, BigDecimal payAccPartyBalance, BigDecimal transactionAmount, String describes, int userID, String outAccPartyName, String payAccPartyName);

    ObjectRestResponse<Bills> expenditureTransactionInfo(byte putAccParty, int putAccPartyId, BigDecimal putAccPartyBalance, byte payAccParty, int payAccId, BigDecimal payAccPartyBalance, BigDecimal transactionAmount, String describes, int userID, String putAccPartyName, String payAccPartyName);

    List<Bills> exportExcel(SearchSatisfy searchSatisfy);

    Bills selectMerBalanceById(int parseInt);
}

