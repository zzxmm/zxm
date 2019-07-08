package com.shouzan.back.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * @author: bin.yang
 * @Date: 2019/6/12 12:02 PM
 *
 * @Description: 用户 open 关系
 */
@Data
public class UserWechat implements Serializable {

    private static final long serialVersionUID = -7606301612519381618L;

    //主键
    private Integer id;

    // 微信openID
    private String wechatOpenid;

    // 微信open 类型 0公总号  1小程序
    private Byte openidType;

    // unionid
    private String wechatUnionid;

    // 用户ID
    private Integer userId;

    // 创建时间
    private Date createTime;

}