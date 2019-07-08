package com.shouzan.back.biz;

import com.shouzan.back.entity.Bank;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/9/11 12:07
 * @Description:  银行管理
 */
public interface BankBiz {

    int queryBankListCount(SearchSatisfy search);

    List<Bank> queryBankList(SearchSatisfy search);

    ObjectRestResponse<Bank> queryBankInfo(int id);

    ObjectRestResponse<Bank> updateBank(String id, Integer status);

    ObjectRestResponse<Bank> selectBankList();

    ObjectRestResponse<Bank> addInfo(Bank entity);

    ObjectRestResponse<Bank> updateBankInfo(Bank entity);
}
