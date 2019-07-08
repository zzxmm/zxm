package com.shouzan.back.biz;

import com.shouzan.back.entity.Order;
import com.shouzan.back.entity.operate.RechargeOrder;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/9/19 17:46
 * @Description:  订单管理
 */
public interface OrderBiz {

    int queryPageCount(SearchSatisfy search);

    List<Order> queryPageList(SearchSatisfy search);

    ObjectRestResponse<Order> queryOrderById(int id);

    ObjectRestResponse<Order> updateOrderStatus(int id);

    List<RechargeOrder> queryPageRechList(SearchSatisfy search);

    ObjectRestResponse queryRechOrderById(int id);

    int queryRechPageCount(SearchSatisfy search);
}
