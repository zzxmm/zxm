package com.shouzan.back.entity;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * 功能描述:  卡券门店关系表
 *
 * @param:
 * @return:
 * @auther: bin.yang
 * @date: 2018/9/11 上午10:06
 */
@Data
public class CouponStore implements Serializable {

    private static final long serialVersionUID = -2163334030430706864L;

    private Integer id;

    //卡券id
    private Integer couponId;

    //门店id
    private Integer storeId;

    //商户id
    private Integer merId;

}