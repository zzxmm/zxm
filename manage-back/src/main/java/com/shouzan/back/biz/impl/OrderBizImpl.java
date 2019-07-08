package com.shouzan.back.biz.impl;


import com.shouzan.back.biz.OrderBiz;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.Order;
import com.shouzan.back.entity.User;
import com.shouzan.back.entity.integral.IntegralRecord;
import com.shouzan.back.entity.operate.RechargeOrder;
import com.shouzan.back.mapper.OrderDetailMapper;
import com.shouzan.back.mapper.OrderMapper;
import com.shouzan.back.mapper.UserMapper;
import com.shouzan.back.mapper.integral.IntegralRecordMapper;
import com.shouzan.back.mapper.operate.RechargeOrderMapper;
import com.shouzan.back.util.CodeValid;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.biz.BaseBiz;
import com.shouzan.common.msg.ObjectRestResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/9/19 17:47
 * @Description:  订单管理
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class OrderBizImpl extends BaseBiz<OrderMapper, Order> implements OrderBiz {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RechargeOrderMapper rechargeOrderMapper;

    @Autowired
    private IntegralRecordMapper integralRecordMapper;

    /**
     * @Description: (查询总条数)
     * @param search
     * @[param] [search]
     * @return int
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:36
     */
    @Override
    public int queryPageCount(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords(search.getKeywords().trim());
        }else{
            search.setKeywords(null);
        }
            return orderMapper.queryPageCount(search);
    }

    /**
     * @Description: (查询数据集合)
     * @param search
     * @[param] [search]
     * @return java.util.List<com.shouzan.back.entity.Order>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:37
     */
    @Override
    public List<Order> queryPageList(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords(search.getKeywords().trim());
        }else{
            search.setKeywords(null);
        }
        List<Order> orderList = orderMapper.queryPageList(search);
        if(search.getTypes() == 0){
            // 商户号订单
            return merchantsOrderInfo(orderList);
        }else if(search.getTypes() == 1){
            // 拼团订单 暂时没有逻辑 先写null
            orderList = null;
        }else if(search.getTypes() == 2){
            // 运营H5订单
            return operateOrderInfo(orderList);
        }else if(search.getTypes() == 3){
            // 公众号订单
            return weChatOrderInfo(orderList);
        }else if(search.getTypes() == 4){
            // 串码订单
            return codeOrderInfo(orderList);
        }
        return orderList;
    }

    /**
     * @Description: (通过ID查询详细信息)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Order>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:46
     */
    @Override
    public ObjectRestResponse queryOrderById(int id) {
        Order order = orderMapper.queryOrderById(id).data(orderDetailMapper.queryOrderDetailById(id));
        User user = userMapper.findInfoById(order.getUserId());
        if(user != null){
            order.setUserId(user.getId());
            order.setUserName(user.getNickName());
            order.setUserTel(user.getPhone());
        }
        String orderNo = order.getOrderNo();
        IntegralRecord integralRecord = integralRecordMapper.selectIntegralRecord(orderNo);
        order.setInRecord(integralRecord);
        return new ObjectRestResponse().result(order).rel(Response.SUCCESS);
    }

    /**
     * @Description: (撤销订单)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Order>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:50
     */
    @Override
    public ObjectRestResponse<Order> updateOrderStatus(int id) {
        return CodeValid.CodeMsg(orderMapper.updateOrderStatus(id),"撤销");
    }

    /**
     * @Description: (充值订单分页查询)
     * @param search
     * @[param] [search]
     * @return java.util.List<com.shouzan.back.entity.operate.RechargeOrder>
     * @author:  bin.yang
     * @date:  2019/4/11 6:01 PM
     */
    @Override
    public List<RechargeOrder> queryPageRechList(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords(search.getKeywords().trim());
        }else{
            search.setKeywords(null);
        }
        List<RechargeOrder> orders = rechargeOrderMapper.queryPageList(search);
        return orders;
    }

    /**
     * @Description: (充值订单查询详细)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/4/11 6:01 PM
     */
    @Override
    public ObjectRestResponse queryRechOrderById(int id) {
        RechargeOrder order = rechargeOrderMapper.queryOrderById(id);
        return new ObjectRestResponse().rel(Response.SUCCESS).result(order);
    }

    /**
     * @Description: (充值订单查询总条数)
     * @param search
     * @[param] [search]
     * @return int
     * @author:  bin.yang
     * @date:  2019/4/11 6:02 PM
     */
    @Override
    public int queryRechPageCount(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords(search.getKeywords().trim());
        }else{
            search.setKeywords(null);
        }
        return rechargeOrderMapper.queryPageCount(search);
    }

    /**
     * @Author: bin.yang
     * @Date: 2019/4/11 6:02 PM
     *
     * @Description:  商户号卡卷查询
     */
    public List<Order> merchantsOrderInfo(List<Order> orderList) {

        return orderList;
    }

    /**
     * @Author: bin.yang
     * @Date: 2019/4/11 6:02 PM
     *
     * @Description: 运营H5订单查询
     */
    public List<Order> operateOrderInfo(List<Order> orderList) {

        return orderList;
    }

    /**
     * @Author: bin.yang
     * @Date: 6:03 PM
     *
     * @Description: 公众号订单查询
     */
    public List<Order> weChatOrderInfo(List<Order> orderList) {

        return orderList;
    }

    /**
     * @Author: bin.yang
     * @Date: 2019/4/11 6:03 PM
     *
     * @Description:  串码订单查询
     */
    public List<Order> codeOrderInfo(List<Order> orderList) {

        return orderList;
    }
}
