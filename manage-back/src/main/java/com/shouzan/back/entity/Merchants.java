package com.shouzan.back.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 功能描述:  商户表
 *
 * @param:
 * @return:
 * @auther: bin.yang
 * @date: 2018/9/14 下午3:33
 */
@Data
public class Merchants implements Serializable {

    private static final long serialVersionUID = -8821482290647646328L;

    private Integer id;

    //商户名称
    @NotNull(message = "商户名称不能为空")
    private String merName;

    //商户简称
    private String merShotName;

    //  *  商户类型名称
    private String merTypeName;

    //  *  商户类型
    private int merType;

    //客服电话
    private String serviceTelNumber;

    //创建时间
    private Date createTime;

    //创建人
    private Integer creatorId;

    //修改人
    private int lastEditId ;

    //修改时间
    private Date lastEditTime;

    //商家logo
    private String logoUrl;

    //联系人姓名
    private String linkmanName;

    //商家封面
    private String merCover;

    //联系人手机号
    private String linkmanTel;

    //联系人微信
    private String linkmanWechat;

    //商户状态(：0停用、1启用)
    private Byte enableState;

    //商家简介
    private String merIntroduce;

    //门店等级(: 1-5 )
    private Byte merGrade;

    //评价星级(: 1-5 )
    private Byte evaluateGrade;

    //营业时间
    private String businessHours;

    //营业执照
    private String businessLicense;

    //统一编号
    private String unifiedNumber;

    //服务号关联( : 0关联 , 1解绑)
    private Byte servicenum;

    //是否精选(0非精选、1精选)
    private Byte isSelect;

    // * _  余额
    private BigDecimal Balance = BigDecimal.valueOf(0.00);

    // 商户中心商户ID
    private int merCenterId;

    // 微信分配商户号
    @NotNull(message = "填写微信分配商户号")
    private String wechatMchId;

    // 商户优先级
    private int priority;
}