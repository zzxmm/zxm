package com.shouzan.back.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: bin.yang
 * @Date: 2018/9/30 10:56
 * @Description:  账单总览
 */
@Data
public class BillsOverview implements Serializable {

    private static final long serialVersionUID = -5603099437967340661L;

    // 总支出
    private BigDecimal totalExpenditure = BigDecimal.ZERO;

    // 总收入
    private BigDecimal totalIncome = BigDecimal.ZERO;

    // 总余额
    private BigDecimal totalBalance = BigDecimal.ZERO;
}
