package com.shouzan.back.mapper;

import com.shouzan.back.entity.OrderDetail;
/**
 * @Author: bin.yang
 * @Date: 2019/1/23 5:19 PM
 *
 * @Description:  订单详情
 */
public interface OrderDetailMapper {

    OrderDetail queryOrderDetailById(int id);
}