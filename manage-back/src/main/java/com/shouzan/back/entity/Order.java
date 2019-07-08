package com.shouzan.back.entity;

import com.shouzan.back.entity.integral.IntegralRecord;
import com.shouzan.back.util.CodeValid;
import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 *
 * 功能描述:   订单表
 *
 * @param:
 * @return: 
 * @auther: bin.yang
 * @date: 2018/9/11 上午10:07
 */
@Data
public class Order<T> implements Serializable {

    private static final long serialVersionUID = 8713758507786872774L;

    private Integer id;

    //内部订单号
    private String orderNo;

    //微信订单号
    private String wechatOrderNo;

    //订单金额
    private BigDecimal orderAmount;

    //订单状态
    private Byte orderState;

    //订单创建时间
    private Date createTime;

    //用户id
    private Integer userId;

    //用户名称
    private String userName;

    //用户电话
    private String userTel;

    //用户微信openid
    private String wechatOpenid;

    //订单更新时间
    private Date refreshTime;

    //卡券优惠金额
    private BigDecimal cheapPrice;

    //实际支付金额
    private BigDecimal actualPrice;

    //订单类型 : 0 正常订单 , 1 拼团订单  2运营H5订单  3 公众号订单  4串码订单
    private Byte orderType;

    // * 商品名称
    private String goodsName;

    // * 商户名称
    private String merName;

    // * 银行名称
    private String bankName;

    //  主商户openid
    private String mainOpenid;

     // * 订单详细
     private T data;

     //  积分对象
    @Transient
    private IntegralRecord inRecord;

    //获利人ID
    private Integer reapUserId;

    //获利人openID
    private String reapUserOpenId;

    // 获利人信息
    @Transient
    private User reapUser;

    public void setCreateTime(String createTime) {
        this.createTime = CodeValid.dateFormat(createTime);
    }

    public void setRefreshTime(String refreshTime) {
        this.refreshTime = CodeValid.dateFormat(refreshTime);
    }

    public Order data(T data) {
        this.data = data;
        return this;
    }
}