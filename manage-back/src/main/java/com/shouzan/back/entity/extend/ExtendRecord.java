package com.shouzan.back.entity.extend;

import com.shouzan.back.annotation.MinValue;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @Author: bin.yang
 * @Date: 2019/2/18 5:31 PM
 *
 * @Description: 分销-记录表
 */
@Data
public class ExtendRecord implements Serializable {

    private static final long serialVersionUID = 4211349245468763626L;

    // 主键
    private Integer id;

    // 邀请者ID
    private Integer inviterId;

    // 邀请者openID
    private String inviterOpenid;

    // 邀请者名称
    private String inviterName;

    // 受邀者ID
    private Integer beInviterId;

    // 受邀者openID
    private String beInviterOpenid;

    // 受邀者名称
    private String beInviterName;

    // 奖励金额
    private BigDecimal rewardMoney;

    // 奖励类型 (0 邀请奖励  1返佣奖励)
    private Byte rewardType;

    // 创建时间
    private Date createTime;

}