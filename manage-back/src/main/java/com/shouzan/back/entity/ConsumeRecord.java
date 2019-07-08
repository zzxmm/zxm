package com.shouzan.back.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @Description: (卡卷使用记录)
 * @param null
 * @[param]
 * @return
 * @author:  bin.yang
 * @date:  2018/12/7 11:51 AM
 */
@Data
public class ConsumeRecord implements Serializable {

    private static final long serialVersionUID = -6370359438188049645L;

    private Integer cid;

    // 批次ID
    private Integer cbatchId;

    //优惠ID
    private Long cdiscountId;

    //优惠类型 全场代金券
    private String cdiscountType;

    //优惠金额
    private BigDecimal cdiscountMoney;

    //订单总金额
    private BigDecimal corderMoney;

    //交易类型 支付
    private String ctransactionType;

    //支付单号
    private String cpaymentNumber;

    //消耗时间
    private Date cconsumeTime;

    //消耗商户号
    private Integer cconsumeMer;

    //设备号
    private String cequipmentNumber;

    //银行流水号
    private String cbankSerialnumber;

    //创建时间
    private Date ccreateTime;

    //录入人
    private Integer ccreatorId;

    //单品信息
    private String cinformation;

}