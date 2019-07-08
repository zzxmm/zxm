package com.shouzan.back.mapper.operate;

import com.shouzan.back.entity.operate.RechargeRecord;
import com.shouzan.back.vo.SearchSatisfy;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2019/3/5 11:33 AM
 *
 * @Description:  充值记录
 */
public interface RechargeRecordMapper {

    int queryPageCount(SearchSatisfy search);

    List<RechargeRecord> queryPageList(SearchSatisfy search);

    RechargeRecord findRechCardrecordById(int id);
}