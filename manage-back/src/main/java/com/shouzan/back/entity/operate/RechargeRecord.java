package com.shouzan.back.entity.operate;

import com.shouzan.back.entity.User;
import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @Author: bin.yang
 * @Date: 2019/3/5 11:37 AM
 *
 * @Description: 订单记录
 */
@Data
public class RechargeRecord implements Serializable {

    private static final long serialVersionUID = 6579341754837506836L;

    //主键
    private Integer id;

    //内部订单号
    //内部支付订单号
    private String orderNo;

    //充值状态：0未充值、1未到账、2已到账、3已过期 、4已退款
    //充值状态：0充值中、1充值成功、2充值失败、3已退款
    private Byte state;

    //用户微信openID
    private String wechatOpenid;

    //商品ID
    private Integer goodsId;

    // *** 商品名称
    private String goodsName;

    //充值记录类型  0话费充值  默认0
    private Byte recordType;

    // *** 用户ID
    private Integer userId;

    // *** 用户名称
    private String userName;

    //充值手机号
    private String rechargeTel;

    //创建时间
    //下单时间
    private Date createTime;

    //修改时间
    //订单处理时间
    private Date lastEditTime;

    //充值金额
    private BigDecimal telephoneBill;

    //内部充值订单号
    private String rechargeNo;

    //服务商订单号
    private String thirdOrderNo;

    //服务商订单金额
    private BigDecimal thirdOrderCash;

    //获利人ID
    private Integer reapUserId;

    //获利人openID
    private String reapUserOpenId;

    // 获利人信息
    @Transient
    private User reapUser;

}