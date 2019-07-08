package com.shouzan.back.entity;

import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * 功能描述:  用户表
 *
 * @param:
 * @return:
 * @auther: bin.yang
 * @date: 2018/9/11 上午10:24
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 2724100815705089083L;

    private Integer id;

    //用户账号
    private String userAccount;

    //账号密码
    private String userPassword;

    //姓名
    private String userName;

    //手机号
    private String phone;

    //性别
    private Byte sex;

    //邮箱
    private String eMail;

    //用户状态
    private Byte userState;

    //创建时间
    private Date createTime;

    //用户头像
    private String userHeadimg;

    //微信openID
    private String openid;

    //通讯地址
    private String address;

    // 用户昵称
    private String nickName;

    // 用户中心ID
    private Integer userCenterId;

    //微信openID
    @Transient
    private List<UserWechat> open;

}