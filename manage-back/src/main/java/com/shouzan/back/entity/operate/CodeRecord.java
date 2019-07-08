package com.shouzan.back.entity.operate;

import com.shouzan.back.entity.User;
import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
/**
 * @Author: bin.yang
 * @Date: 2019/4/11 12:02 PM
 *
 * @Description: 串码记录
 */
@Data
public class CodeRecord implements Serializable {

    private static final long serialVersionUID = -8486759640651610028L;

    //主键
    private Integer id;

    //内部订单号
    private String orderNo;

    //串码CODE
    private String code;

    //卡券状态：0未发放、1已发放
    private Byte cardState;

    //创建时间
    private Date createTime;

    //修改时间
    private Date lastEditTime;

    //用户手机号
    private String userTel;

    // * 用户id
    private Integer userId;

    // * 用户名称
    private String userName;

    //微信openID
    private String wechatOpenid;

    //活动ID
    private Integer activityId;

    //记录类型  0普通串码 默认0
    private Byte recordType;

    // * 商品名称
    private String goodsNames;

    // * 商品ID
    private String goodsId;

    //获利人ID
    private Integer reapUserId;

    //获利人openID
    private String reapUserOpenId;

    // 获利人信息
    @Transient
    private User reapUser;
}