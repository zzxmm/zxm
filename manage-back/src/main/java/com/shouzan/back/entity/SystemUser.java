package com.shouzan.back.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
/**
 *
 * 功能描述:  系统用户表
 *
 * @param:
 * @return: 
 * @auther: bin.yang
 * @date: 2018/9/11 上午10:19
 */
@Data
public class SystemUser implements Serializable {

    private static final long serialVersionUID = 853219267540538510L;

    private Integer id;

    //用户账号
    @NotNull(message = "用户账号不能为空")
    @Length(max = 8 , message = "用户账号不能大于8位")
    private String userAccount;

    //账号密码
    @NotNull(message = "用户密码不能为空")
    private String userPassword;

    //姓名
    @NotNull(message = "用户姓名不能为空")
    private String userName;

    //手机号
    @NotNull(message = "用户手机号不能为空")
    private String phone;

    //性别
    private Byte sex;

    //邮箱
    private String email;

    //用户状态
    private Byte userState;

    //创建时间
    private Date createTime;

    //创建人
    private Integer creatorId;

    //修改时间
    private Date lastEditTime;

    //修改人
    private Integer lastEditId;

    //删除标识
    private Byte delted;

    //用户头像
    private String userHeadimg;

    //微信openID
    private String openid;

    //通讯地址
    private String address;

    //用户中心用户ID
    private int userCenterId;

}