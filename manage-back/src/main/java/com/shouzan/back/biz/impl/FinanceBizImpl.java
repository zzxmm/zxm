package com.shouzan.back.biz.impl;

import com.shouzan.back.biz.FinanceBiz;
import com.shouzan.back.entity.ConsumeRecord;
import com.shouzan.back.entity.FinalAccount;
import com.shouzan.back.mapper.CardrecordMapper;
import com.shouzan.back.mapper.FinanceMapper;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.biz.BaseBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/12/10 16:39
 * @Description:  对账单
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FinanceBizImpl extends BaseBiz<FinanceMapper,ConsumeRecord> implements FinanceBiz {

    @Autowired
    private FinanceMapper financeMapper;

    @Autowired
    private CardrecordMapper cardrecordMapper;

    /**
     * @Description: (使用记录插入)
     * @param listTemp
     * @param userId
     * @[param] [listTemp]
     * @return int
     * @author:  bin.yang
     * @date:  2018/12/11 4:39 PM
     */
    @Override
    public int addConsumeRecordList(List<ConsumeRecord> listTemp, int userId) {
        int i = financeMapper.addConsumeRecordList(listTemp);
        StringBuffer pay = new StringBuffer();
        StringBuffer refu = new StringBuffer();
        for (ConsumeRecord consumeRecord : listTemp) {
            consumeRecord.setCcreatorId(userId);
            if(consumeRecord.getCtransactionType().indexOf("支付") >= 0){
                pay.append(",") ;
                pay.append(consumeRecord.getCdiscountId()) ;
            }else{
                refu.append(",");
                refu.append(consumeRecord.getCdiscountId());
            }
        }
        if(pay != null && pay.length() > 0){
            cardrecordMapper.paymentStatusUpdate(pay.substring(1));
        }
        if(refu != null && refu.length() > 0){
            cardrecordMapper.refundStatusUpdate(refu.substring(1));
        }
        return i;

    }

    /**
     * @Description: (购买记录插入)
     * @param listTemp
     * @param userId
     * @[param] [listTemp]
     * @return int
     * @author:  bin.yang
     * @date:  2018/12/11 4:39 PM
     */
    @Override
    public int addPurchaseRecordList(List<ConsumeRecord> listTemp, int userId) {
        for (ConsumeRecord consumeRecord : listTemp) {
            consumeRecord.setCcreatorId(userId);
        }
        return financeMapper.addPurchaseRecordList(listTemp);
    }

    /**
     * @Description: (查询总条数)
     * @param search
     * @[param] [search]
     * @return int
     * @author:  bin.yang
     * @date:  2018/12/17 5:30 PM
     */
    @Override
    public int queryPageCount(SearchSatisfy search) {
        return financeMapper.queryPageCount(search);
    }

    /**
     * @Description: (查询分页列表)
     * @param search
     * @[param] [search]
     * @return java.util.List<com.shouzan.back.entity.FinalAccount>
     * @author:  bin.yang
     * @date:  2018/12/17 5:30 PM
     */
    @Override
    public List<FinalAccount> queryPageList(SearchSatisfy search) {
        return financeMapper.queryPageList(search);
    }
}
