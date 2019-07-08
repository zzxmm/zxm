package com.shouzan.back.entity;


import com.shouzan.back.util.CodeValid;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * 功能描述:  账单表
 *
 * @param:
 * @return: 
 * @auther: bin.yang
 * @date: 2018/9/11 上午9:57
 */
@Data
public class Bills implements Serializable {

    private static final long serialVersionUID = 8759263383858808624L;

    private Integer id;

    //账单交易类型
    private Byte billsType;

    //账单交易对象
    private Byte billsObject;

    //出账方
    private Byte outAccParty;

    //出账方ID
    private Integer outAccPartyId;

    // * 出账方名称
    private String outAccPartyName;

    //出账方账户余额
    private BigDecimal outAccPartyBalance = BigDecimal.valueOf(0.00);

    //入账方
    private Byte putAccParty;

    //入账方ID
    private Integer putAccPartyId;

    // * 入账方名称
    private String putAccPartyName;

    //入账方账户余额
    private BigDecimal putAccPartyBalance = BigDecimal.valueOf(0.00);

    //交易描述
    private String describes;

    //交易金额
    private BigDecimal transactionAmount = BigDecimal.valueOf(0.00);

    //创建时间
    private Date createTime;

    //创建人ID
    private Integer creatorId;

    //创建人名称
    private String creatorName;

    //修改时间
    private Date lastEditTime;

    //修改人ID
    private Integer lastEditId;

    //修改人名称
    private String lastEditName;

    //修改描述
    private String lastEditDescribe;

    //资金来源
    private Byte payAccParty;

    //资金来源ID
    private Integer payAccId;

    // * 资金来源名称
    private String payAccPartyName;

    //资金来源方余额
    private BigDecimal payAccPartyBalance = BigDecimal.valueOf(0.00);

    public void setCreateTime(String createTime) {
        this.createTime = CodeValid.dateFormat(createTime);
    }

    public void setLastEditTime(String lastEditTime) {
        this.lastEditTime = CodeValid.dateFormat(lastEditTime);
    }
    
}