package com.shouzan.back.entity.achievement;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class AchievementsRule implements Serializable {
    private static final long serialVersionUID = -5414917528378376040L;

    //绩效规则id
    private Integer id;

    //绩效规则名
    @NotNull(message = "绩效规则名不能为空")
    private String ruleName;

    //提现上限
    @NotNull(message = "提现上限不能为空")
    private BigDecimal restrictedUp;

    //提现下限
    @NotNull(message = "提现下限不能为空")
    private BigDecimal restrictedLow;

    //提现间隔限制: 天 默认0
    private Integer restrictedInterval;

    //规则详情
    @NotNull(message = "规则详情不能为空")
    private String ruleDetails;

    //修改人
    private Integer lastEditId;

    //修改时间
    private Date lastEditTime;

}