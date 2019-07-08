package com.shouzan.back.mapper;

import com.shouzan.back.entity.Order;
import com.shouzan.back.vo.SearchSatisfy;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
/**
 * @Author: bin.yang
 * @Date: 2019/1/23 5:19 PM
 *
 * @Description:  订单
 */
public interface OrderMapper extends Mapper<Order> {

    int queryPageCount(SearchSatisfy search);

    List<Order> queryPageList(SearchSatisfy search);

    Order queryOrderById(int id);

    int updateOrderStatus(int id);

    Order selectOrderByNo(@Param("orderNo")String orderNo);

    int refundOrderByOrderNo(@Param("orderNo")String orderNo);


}