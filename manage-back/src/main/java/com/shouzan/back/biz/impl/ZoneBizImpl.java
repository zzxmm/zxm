package com.shouzan.back.biz.impl;

import com.shouzan.back.biz.RuleBiz;
import com.shouzan.back.biz.ZoneBiz;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.Coupon;
import com.shouzan.back.entity.Stock;
import com.shouzan.back.entity.operate.Operate;
import com.shouzan.back.mapper.ZoneMapper;
import com.shouzan.back.rest.RuleController;
import com.shouzan.back.util.CodeValid;
import com.shouzan.common.msg.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @ClassName: com.shouzan.back.biz.impl.ZoneBizImpl
 * @Author: bin.yang
 * @Date: 2019/4/18 10:21
 * @Description: TODO
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ZoneBizImpl implements ZoneBiz {

    @Autowired
    private ZoneMapper zoneMapper;

    @Autowired
    private RuleController ruleController;

    @Autowired
    private RuleBiz ruleBiz;

    @Override
    public ObjectRestResponse updateMerchantsCardStock(Stock stock) {
        ObjectRestResponse res = validationStock(stock);
        if(!res.getRel()){
            return res;
        }
        Coupon cou = zoneMapper.findCardStockInfoById(stock.getId());
        // 增加库存
        if(stock.getIncreaseValue() != null && stock.getIncreaseValue() > 0){
            int i = zoneMapper.increaseStockById(stock.getId(), stock.getIncreaseValue());
            if(cou.getCouponStocks() == 0 && cou.getCouponState() != 0){
                cou.setCouponStocks(stock.getIncreaseValue());
                Coupon status = validationToStatus(cou);
                Coupon result = ruleBiz.selectCouponRuleList(status).getResult();
                Coupon coupon = ruleController.validationDateStatus(result).getResult();
                zoneMapper.updateCardStatus(coupon);
            }
            return CodeValid.CodeMsg(i,"增加库存 "+stock.getIncreaseValue());
        }else{
            //减少库存
            if(cou.getCouponStocks() - stock.getReduceValue() < 0){
                return new ObjectRestResponse().rel(Response.FAILURE).msg("修改失败 : 库存修改数 大于 库存余量 !");
            }
            int i = zoneMapper.reduceStockById(stock.getId(), stock.getReduceValue());
            if(cou.getCouponStocks() - stock.getReduceValue() == 0){
                cou.setIsDisplay(Byte.parseByte("1"));
                cou.setCouponState(Byte.parseByte("3"));
                cou.setIsSell(Byte.parseByte("1"));
                zoneMapper.updateCardStatus(cou);
            }
            return CodeValid.CodeMsg(i,"删减库存 "+stock.getReduceValue());
        }
    }

    @Override
    public ObjectRestResponse updateOperateCardStock(Stock stock) {
        ObjectRestResponse res = validationStock(stock);
        if(!res.getRel()){
            return res;
        }
        Operate operate = zoneMapper.findOptCardStockInfoById(stock.getId());
        //增加库存
        if(stock.getIncreaseValue() != null && stock.getIncreaseValue() > 0){
            int i = zoneMapper.increaseOptStockById(stock.getId(), stock.getIncreaseValue());
            if(operate.getCardStocks() == 0 && operate.getCardState() != 0){
                operate.setCardStocks(stock.getIncreaseValue());
                int status = validationToStatus(operate);
                zoneMapper.updateOperateCardStatus(stock.getId(), status);
            }
            return CodeValid.CodeMsg(i,"增加库存 "+stock.getIncreaseValue());
        }else{
            //减少库存
            if(operate.getCardStocks() - stock.getReduceValue() < 0){
                return new ObjectRestResponse().rel(Response.FAILURE).msg("修改失败 : 库存修改数 大于 库存余量 !");
            }
            int i = zoneMapper.reduceOptStockById(stock.getId(), stock.getReduceValue());
            if(operate.getCardStocks() - stock.getReduceValue() == 0){
                zoneMapper.updateOperateCardStatus(stock.getId(), 2);
            }
            return CodeValid.CodeMsg(i,"删减库存 "+stock.getReduceValue());
        }
    }

    /**
     * @Description: (效验库存数据)
     * @param stock
     * @[param] [stock]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/4/18 12:04 PM
     */
    private ObjectRestResponse validationStock(Stock stock) {
        Integer inc = stock.getIncreaseValue();
        Integer red = stock.getReduceValue();
        if( (inc != null && inc > 0) && (red != null && red > 0) ){
            return new ObjectRestResponse().rel(Response.FAILURE).msg("数据错误 : 单次只能进行一次增减操作");
        }
        if( (inc == null || inc < 1) && (red == null || red < 1) ){
            return new ObjectRestResponse().rel(Response.FAILURE).msg("数据错误 : 增减数量不能小于0");
        }
        return new ObjectRestResponse().rel(Response.SUCCESS);
    }

    /**
     * @Description: (判断状态)
     * @[param] [operate, Coupon]
     * @return com.shouzan.back.entity.operate.Operate
     * @author:  bin.yang
     * @date:  2019/4/19 2:07 PM
     */
    public Coupon validationToStatus(Coupon cou) {
        Date date = new Date();
        if(cou.getUpshelfTime().getTime() < date.getTime() && cou.getDownshelfTime().getTime() > date.getTime()){
            if(cou.getCouponStocks() != null && cou.getCouponStocks() > 0){
                cou.setCouponState(Byte.parseByte("0"));
            }else {
                cou.setCouponState(Byte.parseByte("3"));
            }
        }else if(cou.getUpshelfTime().getTime() > date.getTime()){
            if(cou.getCouponStocks() != null && cou.getCouponStocks() > 0){
                cou.setCouponState(Byte.parseByte("1"));
            }else {
                cou.setCouponState(Byte.parseByte("3"));
            }
        }else {
            cou.setCouponState(Byte.parseByte("3"));
        }
        return cou;
    }
    public int validationToStatus(Operate operate) {
        Date date = new Date();
        if(operate.getUpshelfTime().getTime() < date.getTime() && operate.getDownshelfTime().getTime() > date.getTime()){
            if(operate.getCardStocks() != null && operate.getCardStocks() > 0){
                return 0;
            }else {
                return 2;
            }
        }else {
            return 2;
        }
    }
}
