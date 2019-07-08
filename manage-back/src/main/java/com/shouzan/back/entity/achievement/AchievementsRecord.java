package com.shouzan.back.entity.achievement;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class AchievementsRecord implements Serializable {
    private static final long serialVersionUID = -2127107282411077269L;

    //绩效管理记录id
    private Integer id;

    //绩效管理记录用户id
    private Integer reapUserId;

    //绩效管理记录用户openid
    private String reapUserOpenid;

    //获利类型: 0手赞合伙人 默认0
    private Byte reapType;

    //获利金额
    private BigDecimal rebateMoney;

    //创建时间
    private Date createTime;

    //用户昵称
    private String nickName;


}