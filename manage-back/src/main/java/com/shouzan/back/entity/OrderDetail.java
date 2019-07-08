package com.shouzan.back.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 *
 * 功能描述:   订单详情表
 *
 * @param:
 * @return:
 * @auther: bin.yang
 * @date: 2018/9/11 上午10:14
 */
@Data
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 9081320542461160846L;

    private Integer id;

    //订单id
    private Integer orderId;

    //商品id
    private String goodsId;

    //商品名称
    private String goodsName;

    //商品数量
    private Integer goodsNum;

    //商品单价
    private BigDecimal goodPrice;

    //商户id
    private Integer merId;

    //商户名称
    private String merName;

    //银行id
    private Integer bankId;

    //银行名称
    private String bankName;

}