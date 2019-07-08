package com.shouzan.back.mapper;

import com.shouzan.back.entity.Bank;
import com.shouzan.back.vo.SearchSatisfy;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
/**
 * @Author: bin.yang
 * @Date: 2019/1/23 5:17 PM
 *
 * @Description:   银行
 */

public interface BankMapper extends Mapper<Bank> {

    int queryBankListCount(SearchSatisfy search);

    List<Bank> queryBankList(SearchSatisfy search);

    Bank queryBankInfo(int id);

    int updateBank(@Param("id") String id, @Param("status") Integer status);

    List<Bank> selectBankList();

    int addInfo(Bank entity);

    int updateBankInfo(Bank entity);

    String findIdStringAll();
}