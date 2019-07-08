package com.shouzan.back.entity.extend;

import com.shouzan.back.annotation.MinValue;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @Author: bin.yang
 * @Date: 2019/2/18 5:30 PM
 *
 * @Description:  分销-提现交易表
 */
@Data
public class ExtendExchange implements Serializable {

    private static final long serialVersionUID = 4056379503976683125L;

    // 主键
    private Integer id;

    // 用户名称
    private String userName;

    // 用户openID
    private String userOpenid;

    // 用户openID
    private Integer userId;

    //提现订单号
    private String exOrder;

    // 提现金额
    private BigDecimal exchangeMoney;

    // 申请时间
    private Date createTime;

    // 提现状态 :  0 申请提现  1 提现失败  2 提现成功 3 拒绝提现
    private Byte dealState;

    // 处理时间
    private Date dealTime;

    // 处理人
    private Integer dealUser;

    // 备注
    private String notes;

    //用户手机号
    private String phone;

}