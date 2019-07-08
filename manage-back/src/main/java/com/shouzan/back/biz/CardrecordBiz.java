package com.shouzan.back.biz;

import com.shouzan.back.entity.Cardrecord;
import com.shouzan.back.entity.operate.RechargeRecord;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/9/26 10:34
 * @Description:  卡卷记录
 */
public interface CardrecordBiz {

    int queryPageCount(SearchSatisfy search);

    List<Cardrecord> queryPageList(SearchSatisfy search);

    ObjectRestResponse<Cardrecord> selectCardrecordById(int id,int type);

    ObjectRestResponse<Cardrecord> updateCardrecordById(String id);

    ObjectRestResponse<Cardrecord> refundCardrecordByOrderNo(String orderNo);

    int queryPageRechCount(SearchSatisfy search);

    List<RechargeRecord> queryPageRechList(SearchSatisfy search);

    ObjectRestResponse findRechCardrecordById(int id);
}
