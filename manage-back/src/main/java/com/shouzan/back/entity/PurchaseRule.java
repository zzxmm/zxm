package com.shouzan.back.entity;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
/**
 * @Author: bin.yang
 * @Date: 2018/12/19 3:15 PM
 *
 * @Description: 限购规则
 */
@Data
public class PurchaseRule implements Serializable {

    private static final long serialVersionUID = 4054162933786539036L;

    // 主键ID
    private Integer id;

    // 限购类型: 0 单人限购
    @NotNull(message = "限购类型不能为空")
    private Byte purchaseType;

    // 期间限购: 0 单日限购 1 上架期间
    @NotNull(message = "限购期间不能为空")
    private Byte purchasePeriod;

    // 限购数量
    @NotNull(message = "限购数量不能为空")
    private int purchaseNumber;

    // 规则类型: 0卡卷 1 活动 2免支付发卡
    private Byte ruleType;

    // 限购商品ID
    private Integer purchaseGoods;
}