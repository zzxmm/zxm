package com.shouzan.back.entity.extend;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2019/2/18 5:32 PM
 *
 * @Description:  分销-规则表
 */
@Data
public class ExtendRule implements Serializable {

    private static final long serialVersionUID = 408258232112987419L;

    //主键
    private Integer id;

    //规则说明
    private String details;

    // 提现金额限制
    private BigDecimal restrictedUp;

    // 下限
    private BigDecimal restrictedLow;

    // 创建时间
    private Date createTime;

    // 创建人
    private Integer creatorId;

    // 修改时间
    private Date lastEditTime;

    // 修改人
    private Integer lastEditId;

    /** 提现间隔**/
    private Integer restrictedInterval;

    //等级条件
    private List<ExtendRuleParam> param;

}