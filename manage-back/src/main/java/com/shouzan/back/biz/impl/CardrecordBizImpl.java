package com.shouzan.back.biz.impl;

import com.shouzan.back.biz.CardrecordBiz;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.*;
import com.shouzan.back.entity.operate.Operate;
import com.shouzan.back.entity.operate.RechargeRecord;
import com.shouzan.back.mapper.*;
import com.shouzan.back.mapper.operate.OperateMapper;
import com.shouzan.back.mapper.operate.RechargeRecordMapper;
import com.shouzan.back.util.CodeValid;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.biz.BaseBiz;
import com.shouzan.common.msg.ObjectRestResponse;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/9/26 10:35
 * @Description:  卡卷记录
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CardrecordBizImpl extends BaseBiz<CardrecordMapper, Cardrecord> implements CardrecordBiz {

    @Autowired
    private CardrecordMapper cardrecordMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MerchantsMapper merchantsMapper;

    @Autowired
    private RechargeRecordMapper rechargeRecordMapper;

    @Autowired
    private OperateMapper operateMapper;

    /**
     * @Description: (查询总条数)
     * @param search
     * @[param] [search]
     * @return int
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:41
     */
    @Override
    public int queryPageCount(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() +"%");
        }else{
            search.setKeywords(null);
        }
        return cardrecordMapper.queryPageCount(search);
    }

    /**
     * @Description: (查询数据集合)
     * @param search
     * @[param] [search]
     * @return java.util.List<com.shouzan.back.entity.Activity>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:37
     */
    @Override
    public List<Cardrecord> queryPageList(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() +"%");
        }else{
            search.setKeywords(null);
        }
        List<Cardrecord> list = cardrecordMapper.queryPageList(search);
        if(search.getTypes() == 0){
            //商户号卡卷
            return merchantsCardList(list);
        }else if(search.getTypes() == 1){
            // 拼团卡卷 暂时没有逻辑 先写null
                list = null;
        }else if(search.getTypes() == 2){
            // 运营H5卡卷
            return operateCardList(list);
        }else if(search.getTypes() == 3){
            // 公众号卡卷
            return weChatCardList(list);
        }else if(search.getTypes() == 4){
            // 免支付卡卷
            return noPaymentCardList(list);
        }
        return list;
    }

    /**
     * @Description: (通过ID查询详细信息)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Cardrecord>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:46
     */
    @Override
    public ObjectRestResponse<Cardrecord> selectCardrecordById(int id , int type) {
        ObjectRestResponse<Cardrecord> response = new ObjectRestResponse<>();
        Cardrecord cardrecord = cardrecordMapper.selectCardrecordById(id);
        if(type == 0){
            return merchantsCardInfo(cardrecord);
        }else if(type == 1){
            // 暂时没有逻辑 先写null
            response.rel(Response.SUCCESS).result(null);
        }else if(type == 2){
            return operateCardInfo(cardrecord);
        }else if(type == 3){
            return weChatCardInfo(cardrecord);
        }else if(type == 4){
            return noPaymentCardInfo(cardrecord);
        }
        return response;
    }

    /**
     * @Description: (卡卷发放)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Cardrecord>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:47
     */
    @Override
    public ObjectRestResponse<Cardrecord> updateCardrecordById(String id) {
        return CodeValid.CodeMsg(cardrecordMapper.updateCardrecordById(id),"发放");
    }

    /**
     * @Description: (卡卷退款)
     * @param orderNo
     * @[param] [orderNo]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Cardrecord>
     * @author:  bin.yang
     * @date:  2018/10/10 上午9:33
     */
    @Override
    public ObjectRestResponse<Cardrecord> refundCardrecordByOrderNo(String orderNo) {
        int flag = cardrecordMapper.refundCardrecordByOrderNo(orderNo);
        if(flag>0){
            return CodeValid.CodeMsg(orderMapper.refundOrderByOrderNo(orderNo),"退款");
        }else {
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("退款失败 , 卡卷已发放");
        }
    }

    /**
     * @Description: (话费充值记录总条数)
     * @param search
     * @[param] [search]
     * @return int
     * @author:  bin.yang
     * @date:  2019/3/21 10:55 AM
     */
    @Override
    public int queryPageRechCount(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() +"%");
        }else{
            search.setKeywords(null);
        }
        return rechargeRecordMapper.queryPageCount(search);
    }

    /**
     * @Description: (话费充值记录分页)
     * @param search
     * @[param] [search]
     * @return java.util.List<com.shouzan.back.entity.operate.RechargeRecord>
     * @author:  bin.yang
     * @date:  2019/3/21 10:56 AM
     */
    @Override
    public List<RechargeRecord> queryPageRechList(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() +"%");
        }else{
            search.setKeywords(null);
        }
        List<RechargeRecord> rechargeRecords = rechargeRecordMapper.queryPageList(search);
        return rechargeRecords;
    }

    /**
     * @Description: (话费充值记录详细)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/3/21 11:47 AM
     */
    @Override
    public ObjectRestResponse findRechCardrecordById(int id) {
        RechargeRecord cardrecord = rechargeRecordMapper.findRechCardrecordById(id);
        return new ObjectRestResponse().result(cardrecord).rel(Response.SUCCESS);
    }

    /**
     * @Author: bin.yang
     * @Date: 2019/4/2 11:17 AM
     *
     * @Description: (page) 商户号卡卷记录信息
     */
    public List<Cardrecord> merchantsCardList(List<Cardrecord> list){

        return list;
    }

    /**
     * @Author: bin.yang
     * @Date: 2019/4/2 11:20 AM
     *
     * @Description: (page) 运营H5卡卷
     */
    public List<Cardrecord> operateCardList(List<Cardrecord> list){

        return list;
    }

    /**
     * @Author: bin.yang
     * @Date: 2019/4/2 11:19 AM
     *
     * @Description: (page) 公总号卡卷
     */
    public List<Cardrecord> weChatCardList(List<Cardrecord> list){

        return list;
    }

    /**
     * @Author: bin.yang
     * @Date: 2019/4/2 11:22 AM
     *
     * @Description: (page) 免支付发卡
     */
    public List<Cardrecord> noPaymentCardList(List<Cardrecord> list){

        return list;
    }

    /**
     * @Author: bin.yang
     * @Date: 2019/4/2 11:46 AM
     *
     * @Description: (info) 商户号卡卷
     */
    public ObjectRestResponse<Cardrecord> merchantsCardInfo(Cardrecord cardrecord){
        ObjectRestResponse<Cardrecord> response = new ObjectRestResponse<>();
        Order order = null;
        OrderDetail orderDetail = null;
        if(cardrecord != null){
            order = orderMapper.selectOrderByNo(cardrecord.getOrderNo());
            if(order != null){
                order.setOrderAmount(order.getActualPrice());
                orderDetail = orderDetailMapper.queryOrderDetailById(order.getId());
                cardrecord.setUser(userMapper.findInfoById(order.getUserId()));
                response.result(cardrecord.Info(order.data(orderDetail))).rel(Response.SUCCESS);
            }
            Merchants merchants = merchantsMapper.findInfoById(cardrecord.getMerId());
            if(merchants != null){
                cardrecord.setMerName(merchants.getMerName());
            }
            response.result(cardrecord.Info(order)).rel(Response.SUCCESS);
        }else{
            response.rel(Response.FAILURE).result(null).msg("未找到相关数据");
        }
        return response;
    }

    /**
     * @Author: bin.yang
     * @Date: 2019/4/2 11:46 AM
     *
     * @Description: (info) 运营H5卡卷
     */
    public ObjectRestResponse<Cardrecord> operateCardInfo(Cardrecord cardrecord){
        ObjectRestResponse<Cardrecord> response = new ObjectRestResponse<>();
        Order order = null;
        OrderDetail orderDetail = null;
        if(cardrecord != null){
            order = orderMapper.selectOrderByNo(cardrecord.getOrderNo());
            if(order != null){
                order.setOrderAmount(order.getActualPrice());
                orderDetail = orderDetailMapper.queryOrderDetailById(order.getId());
                cardrecord.setUser(userMapper.findInfoById(order.getUserId()));
                response.result(cardrecord.Info(order.data(orderDetail))).rel(Response.SUCCESS);
            }
            response.result(cardrecord.Info(order)).rel(Response.SUCCESS);
        }else{
            response.rel(Response.FAILURE).result(null).msg("未找到相关数据");
        }
        return response;
    }

    /**
     * @Author: bin.yang
     * @Date: 2019/4/2 11:46 AM
     *
     * @Description: (info) 公总号卡卷
     */
    public ObjectRestResponse<Cardrecord> weChatCardInfo(Cardrecord cardrecord){
        ObjectRestResponse<Cardrecord> response = new ObjectRestResponse<>();
        Order order = null;
        OrderDetail orderDetail = null;
        if(cardrecord != null){
            order = orderMapper.selectOrderByNo(cardrecord.getOrderNo());
            if(order != null){
                order.setOrderAmount(order.getActualPrice());
                orderDetail = orderDetailMapper.queryOrderDetailById(order.getId());
                cardrecord.setUser(userMapper.findInfoById(order.getUserId()));
                response.result(cardrecord.Info(order.data(orderDetail))).rel(Response.SUCCESS);
            }
            Merchants merchants = merchantsMapper.findInfoById(cardrecord.getMerId());
            if(merchants != null){
                cardrecord.setMerName(merchants.getMerName());
            }
            response.result(cardrecord.Info(order)).rel(Response.SUCCESS);
        }else{
            response.rel(Response.FAILURE).result(null).msg("未找到相关数据");
        }
        return response;
    }

    /**
     * @Author: bin.yang
     * @Date: 2019/4/2 11:52 AM
     *
     * @Description: (info) 免支付卡卷
     */
    public ObjectRestResponse<Cardrecord> noPaymentCardInfo(Cardrecord cardrecord){
        ObjectRestResponse<Cardrecord> response = new ObjectRestResponse<>();
        Order order = new Order();
        OrderDetail detail = new OrderDetail();
        if(cardrecord != null){
            Operate operate = operateMapper.findInfoById(Integer.parseInt(cardrecord.getCouponId()));
            cardrecord.setGoodsName(operate.getCardName());
            detail.setGoodsId(operate.getId()+"");
            detail.setGoodsName(operate.getCardName());
            cardrecord.Info(order.data(detail));
            cardrecord.setUser(userMapper.findInfoById(cardrecord.getUserId()));
            response.result(cardrecord).rel(Response.SUCCESS);
        }else{
            response.rel(Response.FAILURE).result(null).msg("未找到相关数据");
        }
        return response;
    }

}
