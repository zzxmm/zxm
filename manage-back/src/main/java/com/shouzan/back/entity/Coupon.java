package com.shouzan.back.entity;

import com.shouzan.back.annotation.MinValue;
import com.shouzan.back.util.CodeValid;
import lombok.Data;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * 功能描述:  卡券表
 *
 * @param:
 * @return:
 * @auther: bin.yang
 * @date: 2018/9/11 上午9:29
 */
@Data
public class Coupon<T> implements Serializable{

    private static final long serialVersionUID = -7446710706229582488L;

    private Integer id;

    //卡券全称
    @NotNull(message = "卡卷名称不能为空")
    private String couponName;

    //卡券简称
    private String couponShotName;

    //卡券类型
    @NotNull(message = "卡卷类型不能为空")
    private Byte couponType;

    //卡券有效期限起始
    @NotNull(message = "请设置卡卷有效期")
    private Date couponValidTimeStart;

    //卡券有效期限截止
    @NotNull(message = "请设置卡卷有效期")
    private Date couponValidTimeEnd;

    //卡券库存
    @NotNull(message = "卡卷库存不能为空")
    private Integer couponStocks;

    //卡券状态
    private Byte couponState = 1;

    //商户id
    @NotNull(message = "商户ID不能为空")
    private Integer merId;

    //银行id
    @NotNull(message = "银行ID不能为空")
    private Integer bankId;

    //卡券上架时间
    @NotNull(message = "请选择卡卷上架时间")
    private Date upshelfTime;

    //卡券下架时间
    @NotNull(message = "请选择卡卷下架时间")
    private Date downshelfTime;

    //限购类型
    private Byte limitBuyType;

    //限购人数
    private Integer limitPersonNum;

    //限购数量
    private Integer limitBuyNum;

    //描述
    private String describes;

    //事项
    private String matter;

    //创建时间
    private Date createTime;

    //创建人
    private Integer creatorId;

    // *** 创建人名称
    private String creatorName;

    //修改时间
    private Date lastEditTime;

    //修改人
    private Integer lastEditId;

    // *** 修改人名称
    private String lastEditName;

    //卡券是否特惠
    @NotNull(message = "请选择是否特惠")
    private Byte isPreferen;

    //卡券是否精选
    private Byte isSelect;

    //卡券优先级
    private Integer priority;

    //微信商品标记
    private String goodsTag;

    //卡券金额
    @NotNull(message = "请填写卡卷金额")
    private BigDecimal price;

    //卡券优惠后金额
    @NotNull(message = "请填写卡卷优惠后金额")
    private BigDecimal discountPrice;

    //微信代金券批次id
    @NotNull(message = "微信代金券批次ID")
    private String couponStockId;

    //卡券图片地址
    @NotNull(message = "请选择图片")
    private String couponImage;

    //卡卷使用范围
    private String couponUseRange;

    // * 门店ID
    @NotNull(message = "请选择适用门店")
    private String storeId;

    //  *** 银行信息
    private T bank;

    // *** 商户信息
    private T merchants;

    // *** 门店信息
    private T storeList;

    // 是否售卖 0是 1否 默认1否
    private Byte isSell;

    // 是否显示 0是 1否 默认1否
    private Byte isDisplay;

    // 返佣金额
    @NotNull(message = "请填写返佣金额")
    private BigDecimal returnMoney;

    // 限购规则
    @Transient
    private List<PurchaseRule> purcRuleList;

    // 售卖规则
    @Transient
    private List<SellRule> sellRuleList;

    /** 每个卡券可获得积分 **/
    private Integer makeIntegral;

    /** 每个卡券可用积分数 **/
    private Integer useIntegral;

    /** 积分购买标记 */
    private String integralSign;

    /** 积分可低现 **/
    private Double integralCash;

    /** 是否参与积分抵现 */
    @NotNull(message = "请选择是否参与积分抵现")
    private Byte isPartakeCash;

    /** 是否参加购买获得积分 **/
    @NotNull(message = "请选择是否参加购买获得积分")
    private Byte isIntegral;

   /** 是否参加购买获得积分 **/
    @NotNull(message = "请填写卡包ID")
    private String cardPackageId;



    public void setUpshelfTime(String upshelfTime) {
        this.upshelfTime = CodeValid.dateFormat(upshelfTime);
    }

    public void setCouponValidTimeStart(String couponValidTimeStart) {
        this.couponValidTimeStart = CodeValid.dateFormat(couponValidTimeStart);
    }

    public void setCouponValidTimeEnd(String couponValidTimeEnd) {
        this.couponValidTimeEnd = CodeValid.dateFormat(couponValidTimeEnd);
    }

    public void setDownshelfTime(String downshelfTime) {
        this.downshelfTime = CodeValid.dateFormat(downshelfTime);
    }

    public void setCreateTime(String createTime) {
        this.createTime = CodeValid.dateFormat(createTime);
    }

    public void setLastEditTime(String lastEditTime) {
        this.lastEditTime = CodeValid.dateFormat(lastEditTime);
    }

}