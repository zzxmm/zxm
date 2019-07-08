package com.shouzan.back.entity;

import com.shouzan.back.util.CodeValid;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * 功能描述:  银行表
 *
 * @param:
 * @return: 
 * @auther: bin.yang
 * @date: 2018/9/11 上午9:54
 */
@Data
public class Bank implements Serializable {

    private static final long serialVersionUID = -83446467820305481L;

    private Integer id;

    //银行全称
    @NotNull(message = "银行名称不能为空")
    private String bankName;

    //银行简称
    private String bankShotName;

    //信用卡申领页面url
    private String creditcardApplyUrl;

    //客服电话
    private String serviceTelNumber;

    //银行启停状态
    private Byte enableState;

    //银行logo的url
    private String logoUrl;

    //银行等级
    private Integer level;

    //银行联系人姓名
    private String bankLinkmanName;

    //银行联系人手机号
    private String bankLinkmanTel;

    //银行联系人微信号
    private String bankLinkmanWechat;

    //银行联系人职位
    private String bankLinkmanPosition;

    //银行是否精选
    private Byte isSelect;

    //银行优先级
    private Integer priority;

    //创建时间
    private Date createTime;

    //创建人
    private Integer creatorId;

    //修改时间
    private Date lastEditTime;

    //修改人
    private Integer lastEditId;

    //反白logo
    private String floorLogo;

    // * _  余额
    private BigDecimal balance = BigDecimal.valueOf(0.00);

    public void setCreateTime(String createTime) {
        this.createTime = CodeValid.dateFormat(createTime);
    }

    public void setLastEditTime(String lastEditTime) {
        this.lastEditTime = CodeValid.dateFormat(lastEditTime);
    }
}