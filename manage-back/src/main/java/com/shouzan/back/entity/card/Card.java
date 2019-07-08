package com.shouzan.back.entity.card;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: bin.yang
 * @Date: 2018/12/28 11:31
 * @Description: 卡卷
 */
@Data
public class Card implements Serializable {

    private static final long serialVersionUID = -6870358399162722456L;

    @JSONField(serialize=false)
    private Integer id;

    //卡卷类型   优惠卷 : GENERAL_COUPON
    @NotNull(message = "选择卡卷类型")
    private String card_type;

    // 优惠券信息
    @Valid
    @Transient
    private GeneralCoupon general_coupon;

    // 公众号卡卷 兑换积分
    @NotNull(message = "请输入兑换积分数")
    @JSONField(serialize=false)
    private Integer card_score;

    // 公众号卡卷 兑换金额
    @NotNull(message = "请输入兑换金额")
    @JSONField(serialize=false)
    private BigDecimal card_price;

    // 创建人
    @JSONField(serialize=false)
    private Integer creatorId;

    // 创建时间
    @JSONField(serialize=false)
    private Date createTime;

    // 修改人
    @JSONField(serialize=false)
    private Integer lastEditId;

    // 修改时间
    @JSONField(serialize=false)
    private Date lastEditTime;

    // 微信公众号卡卷ID
    @JSONField(serialize=false)
    private String card_id;
}
