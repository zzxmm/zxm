package com.shouzan.back.entity.operate;

import com.shouzan.back.entity.PurchaseRule;
import com.shouzan.back.util.CodeValid;
import lombok.Data;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2019/1/21 3:50 PM
 *
 * @Description:  运营信息
 */
@Data
public class Operate implements Serializable {

    private static final long serialVersionUID = -7306024276896204116L;

    private Integer id;

    // 主题背景图片
    @NotNull(message = "主题背景图片不能为空")
    private String background;

    // 活动图片
    @NotNull(message = "活动图片不能为空")
    private String cardImg;

    // 活动详情
    private String cardDetails;

    //创建人ID
    private Integer creatorId;

    //创建时间
    private Date createTime;

    //修改人ID
    private Integer lastEditId;

    //修改时间
    private Date lastEditTime;

    //活动名称
    @NotNull(message = "活动名称不能为空")
    private String cardName;

    //商品库存
    private Integer cardStocks;

    // 运营状态 :  0上架 1售罄 2 下架
    private Byte cardState;

    //立减标记
    private String reduceTag;

    //微信批次号
    private String wechatBatches;

    //活动金额
    private BigDecimal cardPrice = BigDecimal.ZERO;

    //优惠后金额
    private BigDecimal discountPrice = BigDecimal.ZERO;

    //上架时间
    @NotNull(message = "上架时间不能为空")
    private Date upshelfTime;

    //下架时间
    @NotNull(message = "下架时间不能为空")
    private Date downshelfTime;

    //页面外链
    private String linkUrl;

    //有效期限起始
    private Date couponValidTimeStart;

    //有效期限截止
    private Date couponValidTimeEnd;

    //话费金额
    private Integer telephoneBill;

    // 活动类型 :   0卡卷  1话费  2免支付发卡  3串码   默认0
    @NotNull(message = "请选择运营类型")
    private Byte operateType;

    // 单个手机号可参与次数
    private Integer telLimitCount;

    // 白名单库ID
    private Integer whiteBaseId;

    // 白名单库名称
    private String whiteBaseName;

    // 串码库ID
    private Integer codeBaseId;

    // 串码库名称
    private String codeBaseName;

    // 串码库名称
    private String message;

    // 限购规则
    @Transient
    private List<PurchaseRule> purcRuleList;

    // 是否绩效商品: 0是  1否
    private Byte isAchievements;

    // 返利金额
    private BigDecimal rebateMoney;

    public void setUpshelfTime(String upshelfTime) {
        this.upshelfTime = CodeValid.dateFormat(upshelfTime);
    }

    public void setDownshelfTime(String downshelfTime) {
        this.downshelfTime = CodeValid.dateFormat(downshelfTime);
    }

    public void setCouponValidTimeStart(String couponValidTimeStart) {
        this.couponValidTimeStart = CodeValid.dateFormat(couponValidTimeStart);
    }

    public void setCouponValidTimeEnd(String couponValidTimeEnd) {
        this.couponValidTimeEnd = CodeValid.dateFormat(couponValidTimeEnd);
    }
}