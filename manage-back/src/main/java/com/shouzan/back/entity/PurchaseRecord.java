package com.shouzan.back.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @Description: (卡卷购买记录)
 * @param null
 * @[param]
 * @return
 * @author:  bin.yang
 * @date:  2018/12/7 11:50 AM
 */
@Data
public class PurchaseRecord implements Serializable {

    private static final long serialVersionUID = -2856163952986414345L;

    private Integer pid;

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

    //交易类型  支付
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