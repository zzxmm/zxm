package com.shouzan.back.entity.card.update;

import com.alibaba.fastjson.annotation.JSONField;
import com.shouzan.back.entity.card.CardCoupon;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: bin.yang
 * @Date: 2018/12/28 11:31
 * @Description: 卡卷  修改用
 */
@Data
public class CardUp implements Serializable {

    private static final long serialVersionUID = 270015995472879125L;

    // 微信公众号卡卷ID
    @NotNull(message = "微信卡卷ID不能为空")
    private String card_id;

    // 优惠券信息
    @Valid
    private GeneralCouponUp general_coupon;

    @Valid
    @JSONField(serialize=false)
    private CardCoupon coupon;

    // 公众号卡卷 兑换积分
    @NotNull(message = "请输入兑换积分数")
    @JSONField(serialize=false)
    private Integer card_score;

    // 公众号卡卷 兑换金额
    @NotNull(message = "请输入兑换金额")
    @JSONField(serialize=false)
    private BigDecimal card_price;

}
