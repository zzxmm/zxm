package com.shouzan.back.entity.card;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * @Author: bin.yang
 * @Date: 2018/12/28 12:18
 * @Description:   卡卷 ROOT json
 */
@Data
public class CardBeanJson implements Serializable {

    private static final long serialVersionUID = 4716755360266845788L;

    // 卡卷信息
    @Valid
    private Card card;

    @Valid
    @JSONField(serialize=false)
    private CardCoupon coupon;
}
