package com.shouzan.back.entity.card;

import com.alibaba.fastjson.annotation.JSONField;
import com.shouzan.back.entity.Bank;
import com.shouzan.back.entity.Merchants;
import lombok.Data;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * 功能描述:  卡券表
 *
 * @param:
 * @return:
 * @auther: bin.yang
 * @date: 2018/9/11 上午9:29
 */
@Data
public class CardCoupon implements Serializable{

    private static final long serialVersionUID = 2824744846216096784L;

    @JSONField(serialize=false)
    private Integer id;

    //卡券全称
    @NotNull(message = "卡卷名称不能为空")
    private String couponName;

    //卡券简称
    private String couponShotName;

    //卡券有效期限起始
    @NotNull(message = "请设置卡卷有效期")

    private Date couponValidTimeStart;

    //卡券有效期限截止
    @NotNull(message = "请设置卡卷有效期")

    private Date couponValidTimeEnd;

    //卡券库存
    private Integer couponStocks;

    //商户id
    @NotNull(message = "商户ID不能为空")
    private Integer merId;

    //银行id
    @NotNull(message = "银行ID不能为空")
    private Integer bankId;

    //描述
    private String describes;

    //事项
    private String matter;

    //卡券金额
    @NotNull(message = "请填写卡卷金额")
    private BigDecimal price;

    //卡券优惠后金额
    @NotNull(message = "请填写卡卷优惠后金额")
    private BigDecimal discountPrice;

    //微信代金券批次id
    @NotNull(message = "微信代金券批次ID")
    private String couponStockId;

    //卡券图片地址
    @NotNull(message = "请选择图片")
    private String couponImage;

    //  *** 银行信息
    @Transient
    private Bank bank;

    // *** 商户信息
    @Transient
    private Merchants merchants;

}