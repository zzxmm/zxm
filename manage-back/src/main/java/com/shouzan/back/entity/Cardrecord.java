package com.shouzan.back.entity;

import com.shouzan.back.util.CodeValid;
import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 *
 * 功能描述:  卡券记录表
 *
 * @param:
 * @return: 
 * @auther: bin.yang
 * @date: 2018/9/11 上午10:01
 */
@Data
public class Cardrecord<T> implements Serializable {

    private static final long serialVersionUID = 6384982637282214406L;

    private Integer id;

    //订单号
    private String orderNo;

    //卡卷id
    private String CouponId;

    //微信代金券id
    private String wechatCouponId;

    //商户id
    private Integer merId;

    //商户名称
    private String merName;

    // 卡卷记录类型  0商户号卡卷  1拼团卡券  2运营H5卡卷  3公众号卡卷 4免支付发卡 默认0
    private Byte recordType;

    //银行id
    private Integer bankId;

    //银行名称
    private String bankName;

    //卡券状态
    private Byte cardState;

    //创建时间
    private Date createTime;

    //修改时间
    private Date lastEditTime;

    //卡券有效期限起始
    private Date couponValidTimeStart;

    //卡券有效期限截止
    private Date couponValidTimeEnd;

    //用户id
    private Integer userId;

    //用户名称
    private String userName;

    //用户电话
    private String userTel;

    //用户微信openID
    private String wechatOpenid;

    // * 订单信息
    private T info;

    // * 用户信息
    private T user;

    // * 销售金额
    private BigDecimal goodPrice;

    // * 商品名称
    private String goodsName;

    private String goodsNameo;

    /** 消耗商户号 */
    private Integer cconsumeMer;

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

    public void setLastEditTime(String lastEditTime) {
        this.lastEditTime = CodeValid.dateFormat(lastEditTime);
    }

    public void setCouponValidTimeStart(String couponValidTimeStart) {
        this.couponValidTimeStart = CodeValid.dateFormat(couponValidTimeStart);
    }

    public void setCouponValidTimeEnd(String couponValidTimeEnd) {
        this.couponValidTimeEnd = CodeValid.dateFormat(couponValidTimeEnd);
    }

    public Cardrecord Info(T info) {
        this.info=info;
        return this;
    }
}