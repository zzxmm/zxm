package com.shouzan.back.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Time;

/**
 * @Author: bin.yang
 * @Date: 2018/12/19 3:17 PM
 *
 * @Description:  售卖规则
 */
@Data
public class SellRule implements Serializable {

    private static final long serialVersionUID = 8287737755192997985L;

    // 主键ID
    private Integer id;

    // 星期(周一是1，周二是2以此类推)
    @NotNull(message = "售卖日期不能为空")
    private Byte sellPeriod;

    // 售卖开始时间
    @NotNull(message = "售卖开始时间不能为空")
    private Time sellTimeStart;

    // 售卖结束时间
    @NotNull(message = "售卖结束时间不能为空")
    private Time sellTimeEnd;

    // 售卖数量 : 0 无限制
    private Integer sellNumber;

    // 规则类型: 0卡卷 1活动
    private Byte ruleType;

    // 售卖商品ID
    private Integer sellGoods;

}