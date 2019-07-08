package com.shouzan.back.biz.impl;

import com.shouzan.back.biz.BillsBiz;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.*;
import com.shouzan.back.mapper.BankMapper;
import com.shouzan.back.mapper.BillsMapper;
import com.shouzan.back.mapper.MerchantsMapper;
import com.shouzan.back.mapper.SystemUserMapper;
import com.shouzan.back.util.CodeValid;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.biz.BaseBiz;
import com.shouzan.common.msg.ObjectRestResponse;
import com.shouzan.uuc.vo.MerchantVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/9/27 10:40
 * @Description:  资金台账
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class BillsBizImpl extends BaseBiz<BillsMapper, Bills> implements BillsBiz {

    @Autowired
    private BillsMapper billsMapper;

    @Autowired
    private MerchantsMapper merchantsMapper;

    @Autowired
    private BankMapper bankMapper;

    @Autowired
    private SystemUserMapper systemUserMapper;

    /**
     * @Description: (获取总条数)
     * @param search
     * @[param] [search]
     * @return int
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:58
     */
    @Override
    public int queryPageCount(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() +"%");
        }else{
            search.setKeywords(null);
        }
        return billsMapper.queryPageCount(search);
    }

    /**
     * @Description: (获取集合列表)
     * @param search
     * @[param] [search]
     * @return java.util.List<com.shouzan.back.entity.Bills>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:58
     */
    @Override
    public List<Bills> queryPageList(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() +"%");
        }else{
            search.setKeywords(null);
        }
        List<Bills> list = billsMapper.queryPageList(search);
        if(list != null && list.size() > 0){
            for (Bills bills : list) {
                if(bills.getPutAccPartyId() != 0){
                    Merchants merchants = merchantsMapper.findInfoById(bills.getPutAccPartyId());
                    if (merchants != null) {
                        String merName = merchants.getMerName();
                        if(!bills.getPutAccPartyName().equals(merName)){
                            bills.setPutAccPartyName(bills.getPutAccPartyName()+"(已变更:"+merName+")");
                        }
                    }
                }
                Bank bank = bankMapper.queryBankInfo(bills.getOutAccPartyId());
                if(bank != null){
                    String bankName = bank.getBankName();
                        if(!bills.getOutAccPartyName().equals(bankName)){
                            bills.setOutAccPartyName(bills.getOutAccPartyName()+"(已变更:"+bankName+")");
                        }
                }
                SystemUser userById = systemUserMapper.selectSystemUserById(bills.getCreatorId());
                bills.setCreatorName(userById.getUserName());
            }
        }
        return list;
    }

    /**
     * @Description: (获取交易主体银行列表)
     * @param
     * @[param] []
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Bank>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:59
     */
    @Override
    public ObjectRestResponse<Bank> selectBankList() {
        return new ObjectRestResponse<Bank>().rows(billsMapper.selectBankList()).rel(Response.SUCCESS);
    }

    /**
     * @Description: (获取交易主体商户列表)
     * @param
     * @[param] []
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Merchants>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:59
     */
    @Override
    public ObjectRestResponse<Merchants> selectMerList() {
        return new ObjectRestResponse<Merchants>().rows(billsMapper.selectMerList()).rel(Response.SUCCESS);
    }

    /**
     * @Description: (订单轨迹查看)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Bills>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:59
     */
    @Override
    public ObjectRestResponse<Bills> selectBillsInfoById(int id) {
        Bills bills = billsMapper.selectBillsInfoById(id);
        MerchantVo merchantVo = new MerchantVo();
        if(bills.getPutAccPartyId() != 0){
            Merchants merchants = merchantsMapper.findInfoById(bills.getPutAccPartyId());
            if (merchants != null) {
                String merName = merchants.getMerName();
                if(!bills.getPutAccPartyName().equals(merName)){
                    bills.setPutAccPartyName(bills.getPutAccPartyName()+"(已变更:"+merName+")");
                }
            }
        }
        Bank bank = bankMapper.queryBankInfo(bills.getPayAccId());
        if(bank != null){
            String bankName = bank.getBankName();
            if(!bills.getPayAccPartyName().equals(bankName)){
                bills.setPayAccPartyName(bills.getPayAccPartyName()+"(已变更:"+bankName+")");
                if(bills.getOutAccPartyId()==bank.getId()){
                    bills.setOutAccPartyName(bills.getOutAccPartyName()+"(已变更:"+bankName+")");
                }
            }
        }
        return new ObjectRestResponse<>().rel(Response.SUCCESS).result(bills);
    }

    /**
     * @Description: (总览查询[总支出.总收入.总余额])
     * @param search
     * @[param] [search]
     * @return com.shouzan.back.entity.BillsOverview
     * @author:  bin.yang
     * @date:  2018/9/30 下午4:00
     */
    @Override
    public BillsOverview queryBillsOverview(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() +"%");
        }
        return billsMapper.queryBillsOverview(search);
    }

    /**
     * @Description: (收入交易记录入账)
     * @param outAccParty
     * @param outAccPartyId
     * @param outAccPartyBalance
     * @param payAccParty
     * @param payAccId
     * @param payAccPartyBalance
     * @param transactionAmount
     * @param describes
     * @param outAccPartyName
     * @param payAccPartyName
     * @[param] [outAccParty, outAccPartyId, outAccPartyName, outAccPartyBalance, payAccParty, payAccId, payAccPartyName, payAccPartyBalance, transactionAmount, describes]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Bills>
     * @author:  bin.yang
     * @date:  2018/9/30 下午4:00
     */
    @Override
    public ObjectRestResponse<Bills> incomeTransactionInfo(byte outAccParty, int outAccPartyId,
                                                           BigDecimal outAccPartyBalance, byte payAccParty, int payAccId,
                                                           BigDecimal payAccPartyBalance, BigDecimal transactionAmount, String describes, int userID, String outAccPartyName, String payAccPartyName) {
        Bills bills = new Bills();
        bills.setBillsType((byte)1);
        bills.setBillsObject((byte)1);
        bills.setOutAccParty(outAccParty);
        bills.setOutAccPartyId(outAccPartyId);
        bills.setOutAccPartyName(outAccPartyName);
        bills.setOutAccPartyBalance(outAccPartyBalance.add(transactionAmount));
        bills.setPayAccParty(payAccParty);
        bills.setPayAccId(payAccId);
        bills.setPayAccPartyName(payAccPartyName);
        bills.setPayAccPartyBalance(payAccPartyBalance.add(transactionAmount));
        bills.setPutAccParty((byte)0);
        bills.setPutAccPartyId(0);
        bills.setPutAccPartyName("信用社");
        BigDecimal bigDecimal = billsMapper.queryBillsBalance();
        if(bigDecimal == null){
            bigDecimal = new BigDecimal("0.00");
        }
        bills.setPutAccPartyBalance(bigDecimal.add(transactionAmount));
        bills.setTransactionAmount(transactionAmount);
        bills.setDescribes(describes);
        bills.setCreatorId(userID);
        return CodeValid.CodeMsg(billsMapper.addTransactionRecord(bills),"收入交易");
    }

    /**
     * @Description: (支出交易记录入账)
     * @param putAccParty
     * @param putAccPartyId
     * @param putAccPartyBalance
     * @param payAccParty
     * @param payAccId
     * @param payAccPartyBalance
     * @param transactionAmount
     * @param describes
     * @param putAccPartyName
     * @param payAccPartyName
     * @[param] [putAccParty, putAccPartyId, putAccPartyName, putAccPartyBalance, payAccParty, payAccId, payAccPartyName, payAccPartyBalance, transactionAmount, describes]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Bills>
     * @author:  bin.yang
     * @date:  2018/9/30 下午4:01
     */
    @Override
    public ObjectRestResponse<Bills> expenditureTransactionInfo(byte putAccParty, int putAccPartyId,
                                                                BigDecimal putAccPartyBalance, byte payAccParty, int payAccId,
                                                                BigDecimal payAccPartyBalance, BigDecimal transactionAmount, String describes, int userID, String putAccPartyName, String payAccPartyName) {
        if(payAccPartyBalance.subtract(transactionAmount).compareTo(BigDecimal.ZERO) < 0){
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("资金来源账户余额不足");
        }else{
            Bills bills = new Bills();
            bills.setBillsType((byte)0);
            bills.setBillsObject((byte)2);
            bills.setPutAccParty(putAccParty);
            bills.setPutAccPartyId(putAccPartyId);
            bills.setPutAccPartyName(putAccPartyName);
            bills.setPutAccPartyBalance(putAccPartyBalance.add(transactionAmount));
            bills.setPayAccParty(payAccParty);
            bills.setPayAccId(payAccId);
            bills.setPayAccPartyName(payAccPartyName);
            bills.setPayAccPartyBalance(payAccPartyBalance.subtract(transactionAmount));
            bills.setOutAccParty((byte)0);
            bills.setOutAccPartyId(0);
            bills.setOutAccPartyName("信用社");
            BigDecimal bigDecimal = billsMapper.queryBillsBalance();
            if(bigDecimal == null){
                bigDecimal = new BigDecimal("0.00");
            }
            bills.setOutAccPartyBalance(bigDecimal.subtract(transactionAmount));
            bills.setTransactionAmount(new BigDecimal("-"+transactionAmount.toString()));
            bills.setDescribes(describes);
            bills.setCreatorId(userID);
            return CodeValid.CodeMsg(billsMapper.addTransactionRecord(bills),"支出交易");
        }
    }

    @Override
    public Bills selectMerBalanceById(int parseInt) {
        return billsMapper.selectMerBalanceById(parseInt);
    }

    /**
     * @Description: (导出数据查询)
     * @param searchSatisfy
     * @[param] [searchSatisfy]
     * @return java.util.List<com.shouzan.back.entity.Bills>
     * @author:  bin.yang
     * @date:  2018/10/8 下午4:19
     */
    @Override
    public List<Bills> exportExcel(SearchSatisfy searchSatisfy) {
        List<Bills> list = billsMapper.selectInfoAll(searchSatisfy);
        if(list != null && list.size() > 0){
            for (Bills bills : list) {
                MerchantVo merchantVo = new MerchantVo();
                if(bills.getPutAccPartyId() != 0){
                    Merchants merchants = merchantsMapper.findInfoById(bills.getPutAccPartyId());
                    if (merchants != null) {
                        String merName = merchants.getMerName();
                        if(!bills.getPutAccPartyName().equals(merName)){
                            bills.setPutAccPartyName(bills.getPutAccPartyName()+"(已变更:"+merName+")");
                        }
                    }
                }
                Bank bank = bankMapper.queryBankInfo(bills.getPayAccId());
                if(bank != null){
                    String bankName = bank.getBankName();
                    if(!bills.getPayAccPartyName().equals(bankName)){
                        bills.setPayAccPartyName(bills.getPayAccPartyName()+"(已变更:"+bankName+")");
                        if(bills.getOutAccPartyId()==bank.getId()){
                            bills.setOutAccPartyName(bills.getOutAccPartyName()+"(已变更:"+bankName+")");
                        }
                    }
                }
            }
        }
        return list;
    }
}
