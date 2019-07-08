package com.shouzan.back.entity.integral;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
/**
 * @Author: man.z
 * @Date: 2019-05-27 15:41
 *
 * @Description: 积分规则
 */
@Data
public class IntegralRule implements Serializable {
    private static final long serialVersionUID = 5251120307626544270L;

    private Integer id;

    /** 规则名称 **/
    @NotNull(message = "规则名称不能为空")
    private String ruleName;

    /** 规则类型 **/
    @NotNull(message = "规则类型不能为空")
    private Byte ruleType;

    /** 每日限制 **/
    private Integer dayLimit;

    /** 获得积分 **/
    private Integer obtainIntegral;

    /** 账户限制 **/
    private Integer accountLimit;

    /** 规则详情 **/
    @NotNull(message = "规则详情不能为空")
    private String ruleDetails;

    /** 创建人 **/
    private Integer creatorId;

    /** 创建时间 **/
    private Date createTime;

    /** 修改人 **/
    private Integer lastEditId;

    /** 修改时间 **/
    private Date lastEditTime;

}