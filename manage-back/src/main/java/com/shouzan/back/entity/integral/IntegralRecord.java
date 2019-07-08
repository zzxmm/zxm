package com.shouzan.back.entity.integral;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: man.z
 * @Date: 2019-05-27 14:31
 *
 * @Description: 积分记录
 */
@Data
public class IntegralRecord implements Serializable {

    private static final long serialVersionUID = 5328370697710907636L;
    private Integer id;

    /** 微信id **/
    private String openId;

    /** 用户id */
    private Integer userId;

    /** 用户昵称 */
    private String nickName;

    /** 手机号 **/
    private String phone;

    /** 积分类型 **/
    private Byte integralType;

    /** 记录类型 **/
    private Byte recordType;

    /** 积分记录数量 **/
    private Integer integralNumber;

    /** 卡券ID **/
    private Integer couponId;

    /** 创建时间 **/
    private Date createTime;

    /** 内部订单 **/
    private String orderNo;

    /** 积分节省钱数 **/
    private Double integralSaveMoney;

    /** 抵扣后卡券金额 */
    private  Double finalCheapPrice;

    /** 当前积分 */
    private Integer currentIntegral;

}