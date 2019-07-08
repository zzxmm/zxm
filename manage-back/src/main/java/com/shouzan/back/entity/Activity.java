package com.shouzan.back.entity;

import com.shouzan.back.util.CodeValid;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 功能描述:  活动表
 *
 * @param:
 * @return:
 * @auther: bin.yang
 * @date: 2018/9/11 上午12:12
 */
@Data
public class Activity<T> implements Serializable {

    private static final long serialVersionUID = 5734988218147413596L;

    private Integer id;

    /** 活动名称 **/
    @NotNull(message = "活动名称不能为空")
    private String activityName;

    /** 活动类型 **/
    @NotNull(message = "活动类型不能为空")
    private Byte activityType;

    /** 活动开始时间 **/
    @NotNull(message = "请设置活动开始时间")
    private Date activityStartTime;

    /** 活动结束时间 **/
    @NotNull(message = "请设置活动结束时间")
    private Date activityEndTime;

    /** 活动封面图 **/
    @NotNull(message = "请上传活动图片")
    private String coverPic;

    /** 活动详情 **/
    private String activityDetails;

    /** 注意事项 **/
    private String matter;

    /** 活动状态 **/
    private Byte activityState;

    /** 商品id **/
    @NotNull(message = "请选择活动商品")
    private Integer goodsId;

    /** 活动参数id **/
    private Integer activityParamId;

    /** 创建时间 **/
    private Date createTime;

    /** 创建人 **/
    private Integer creatorId;

    /** 修改时间 **/
    private Date lastEditTime;

    /** 修改人 **/
    private Integer lastEditId;

    /** 微信商品标记 **/
    private String goodsTag;

    /** 卡券优惠后金额 **/
    @NotNull(message = "卡卷优惠后金额")
    private BigDecimal discountPrice;

    /** /* 活动参数 **/
    private T data;

    /** /* 活动参数 **/
    private T coupon;

    public void setActivityStartTime(String activityStartTime) {
        this.activityStartTime = CodeValid.dateFormat(activityStartTime);
    }

    public void setActivityEndTime(String activityEndTime) {
        this.activityEndTime = CodeValid.dateFormat(activityEndTime);
    }

    public void setCreateTime(String createTime) {
        this.createTime = CodeValid.dateFormat(createTime);
    }

    public void setLastEditTime(String lastEditTime) {
        this.lastEditTime = CodeValid.dateFormat(lastEditTime);
    }

    public Activity data(T data) {
        this.data = data;
        return this;
    }
}