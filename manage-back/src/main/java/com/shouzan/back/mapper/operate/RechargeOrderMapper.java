package com.shouzan.back.mapper.operate;

import com.shouzan.back.entity.operate.RechargeOrder;
import com.shouzan.back.vo.SearchSatisfy;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2019/3/5 11:33 AM
 *
 * @Description: 充值订单
 */
public interface RechargeOrderMapper {

    int queryPageCount(SearchSatisfy search);

    List<RechargeOrder> queryPageList(SearchSatisfy search);

    RechargeOrder queryOrderById(int id);
}