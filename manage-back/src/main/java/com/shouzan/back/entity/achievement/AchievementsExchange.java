package com.shouzan.back.entity.achievement;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
/**
 * @Description: description

 * @[param]
 * @return
 * @author:  man.z
 * @date:  2019-06-26 15:02
 */
public class AchievementsExchange implements Serializable {

    private static final long serialVersionUID = 2069649572841735097L;

    //绩效提现id
    private Integer id;

    //用户ID
    private Integer userId;

    //用户openid
    private String userOpenid;

    //提现金额
    private BigDecimal exchangeMoney;

    //提现订单号
    private String exOrder;

    //可提现金额
    private BigDecimal canExchangeMoney;

    //创建时间
    private Date createTime;

    //提现状态: 0申请提现  1 提现失败  2 提现成功 3 拒绝提现 默认0
    private Byte dealState;

    //申请时间
    private Date dealTime;

    //申请人
    private Integer dealUser;

    //备注
    private String notes;

    //用户昵称
    private String nickName;

    //手机号
    private String phone;


}