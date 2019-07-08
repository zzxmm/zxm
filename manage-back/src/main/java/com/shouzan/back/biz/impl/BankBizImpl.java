package com.shouzan.back.biz.impl;

import com.shouzan.back.biz.BankBiz;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.Bank;
import com.shouzan.back.entity.Coupon;
import com.shouzan.back.entity.Merchants;
import com.shouzan.back.mapper.ActivityMapper;
import com.shouzan.back.mapper.AdvertMapper;
import com.shouzan.back.mapper.BankMapper;
import com.shouzan.back.mapper.CouponMapper;
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
 * @Date: 2018/9/11 12:08
 * @Description:  银行
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class BankBizImpl extends BaseBiz<BankMapper,Bank> implements BankBiz{

    @Autowired
    private BankMapper bankMapper;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private AdvertMapper advertMapper;
    /**
     * @Description: (查询总条数)
     * @param search
     * @[param] [search]
     * @return int
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:41
     */
    @Override
    public int queryBankListCount(SearchSatisfy search) {
        if(StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() + "%");
        }else{
            search.setKeywords(null);
        }
        return bankMapper.queryBankListCount(search);
    }

    /**
     * @Description: (查询信息集合列表)
     * @param search
     * @[param] [search]
     * @return java.util.List<com.shouzan.back.entity.Bank>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:41
     */
    @Override
    public List<Bank> queryBankList(SearchSatisfy search) {
        if(StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() + "%");
        }else{
            search.setKeywords(null);
        }
        return bankMapper.queryBankList(search);
    }

    /**
     * @Description: (通过ID查询详细信息)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Bank>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:42
     */
    @Override
    public ObjectRestResponse<Bank> queryBankInfo(int id) {
        return new ObjectRestResponse<Bank>().rel(Response.SUCCESS).result(bankMapper.queryBankInfo(id));
    }

    /**
     * @Description: (修改状态)
     * @param id
     * @param status
     * @[param] [id, status]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Bank>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:42
     */
    @Override
    public ObjectRestResponse<Bank> updateBank(String id, Integer status) {
        if(status == 0){
            int i = bankMapper.updateBank(id, status);
            if(i > 0){
                String[] split = id.split(",");
                for (int j = 0; j < split.length; j++) {
                    List<Coupon> coupons = couponMapper.selectCouponByBankId(split[j]);
                    if(coupons != null && coupons.size() > 0){
                        for (Coupon coupon : coupons) {
                            couponMapper.updateCouponStatus(coupon.getId()+"",3);
                            activityMapper.updateStatusByCoupon(coupon.getId(),1);
                        }
                    }
                }
                advertMapper.updateAdvertStatusByBank(id);
                return new ObjectRestResponse<Merchants>().rel(Response.SUCCESS).msg("修改银行状态成功");
            }else{
                return new ObjectRestResponse<Merchants>().rel(Response.FAILURE).msg("修改银行状态失败");
            }
        }else{
            return CodeValid.CodeMsg(bankMapper.updateBank(id,status),"修改银行状态");
        }

    }

    /**
     * @Description: (查询所有信息)
     * @param
     * @[param] []
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Bank>
     * @author:  bin.yang
     * @date:  Q 下午3:43
     */
    @Override
    public ObjectRestResponse<Bank> selectBankList() {
        return new ObjectRestResponse<Bank>().rel(Response.SUCCESS).rows(bankMapper.selectBankList());
    }

    /**
     * @Description: (新增银行信息)
     * @param entity
     * @[param] [entity]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Bank>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:43
     */
    @Override
    public ObjectRestResponse<Bank> addInfo(Bank entity) {
        return CodeValid.CodeMsg(bankMapper.addInfo(entity),"添加");
    }

    /**
     * @Description: (修改银行信息)
     * @param entity
     * @[param] [entity]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Bank>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:43
     */
    @Override
    public ObjectRestResponse<Bank> updateBankInfo(Bank entity) {
        return CodeValid.CodeMsg(bankMapper.updateBankInfo(entity),"修改");
    }
}
