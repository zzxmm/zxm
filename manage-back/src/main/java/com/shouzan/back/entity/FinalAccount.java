package com.shouzan.back.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: bin.yang
 * @Date: 2018/12/7 12:06 PM
 *
 * @Description:  最终对账单
 */
@Data
public class FinalAccount implements Serializable {

    private static final long serialVersionUID = -7514935197826038378L;

    // 卡卷状态
    private Byte cardState;

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

    //用户流水号
    private String cbankSerialnumber;

    //创建时间
    private Date ccreateTime;

    //录入人
    private Integer ccreatorId;

    //单品信息
    private String cinformation;

    // 批次ID
    private Integer pbatchId;

    //优惠ID
    private Long pdiscountId;

    //优惠类型 全场定额立减
    private String pdiscountType;

    //优惠金额
    private BigDecimal pdiscountMoney;

    //订单总金额
    private BigDecimal porderMoney;

    //交易类型 支付
    private String ptransactionType;

    //支付单号
    private String ppaymentNumber;

    //消耗时间
    private Date pconsumeTime;

    //消耗商户号
    private Integer pconsumeMer;

    //设备号
    private String pequipmentNumber;

    //银行流水号
    private String pbankSerialnumber;

    //创建时间
    private Date pcreateTime;

    //录入人
    private Integer pcreatorId;

}
