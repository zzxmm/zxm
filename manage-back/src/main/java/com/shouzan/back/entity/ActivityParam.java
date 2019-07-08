package com.shouzan.back.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
/**
 *
 * 功能描述:   活动参数表
 *
 * @param:
 * @return: 
 * @auther: bin.yang
 * @date: 2018/9/11 上午9:49
 */
@Data
public class ActivityParam implements Serializable {

    private static final long serialVersionUID = 386435859366444863L;

    private Integer id;

    /** 开团人数 **/
    @NotNull(message = "请选择开团人数")
    private Integer upLimitPeople;

    /** 每人立减 **/
    private BigDecimal subtractPrice;

    /** 立减上限 **/
    private BigDecimal subtractLimitPrice;

    /** 开团立减 **/
    private BigDecimal groupSubtractPrice;

    /** 优惠额度 **/
    private BigDecimal preferenAmount;

    /** 抢购价格 **/
    private BigDecimal quickBuyPrice;

    /** 单日限制 **/
    private Integer dayLimit;

    /** 活动限制 **/
    private String activityLimit;

    /** 账号限制 **/
    private String accountLimit;

    /** 参与活动数量 **/
    private Integer partActivityNum;

}