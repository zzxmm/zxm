package com.shouzan.back.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class WithdrawalRequest implements Serializable {

    private static final long serialVersionUID = 4846704485419376302L;

    /** 分销提现记录id  */
    private Integer extendExchangeId;

    /** 手赞合伙人提现记录id  */
    private Integer achievementsExchangeId;

    /** 提现类型 1分销提现 2手赞合伙人提现  */
    private String withdrawalType;
}
