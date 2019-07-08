package com.shouzan.back.entity.operate;

import com.shouzan.back.entity.User;
import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @Author: bin.yang
 * @Date: 2019/3/5 11:35 AM
 *
 * @Description:  充值订单
 */
@Data
public class RechargeOrder implements Serializable {

    private static final long serialVersionUID = 7242740364993554141L;

    //主键
    private Integer id;

    //内部订单号
    private String orderNo;

    //微信订单号
    private String wechatOrderNo;

    //订单金额
    private BigDecimal orderAmount;

    //订单状态: 0已充值、1已退款、2未支付、3未充值 4、已支付  、5已撤销    默认2
    private Byte orderState;

    //订单优惠金额
    private BigDecimal cheapPrice;

    //实际支付金额
    private BigDecimal actualPrice;

    //充值订单类型: 0话费充值 默认0
    private Byte orderType;

    //商品id
    private Integer goodsId;

    //商品名称
    private String goodsName;

    //商品单价
    private BigDecimal goodPrice;

    //用户微信openid
    private String wechatOpenid;

    //订单创建时间
    private Date createTime;

    //订单更新时间
    private Date refreshTime;

    // *** 用户ID
    private int userId;
    // *** 用户名称
    private String userName;
    // ** 用户手机号
    private String userTel;

    //充值手机号
    private String rechargeTel;

    //获利人ID
    private Integer reapUserId;

    //获利人openID
    private String reapUserOpenId;

    // 获利人信息
    @Transient
    private User reapUser;
}