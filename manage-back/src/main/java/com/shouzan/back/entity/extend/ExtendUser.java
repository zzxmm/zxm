package com.shouzan.back.entity.extend;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @Author: bin.yang
 * @Date: 2019/2/18 5:33 PM
 *
 * @Description:  分销-用户关系表
 */
@Data
public class ExtendUser implements Serializable {

    private static final long serialVersionUID = 418153563337080574L;

    private Integer id;

    // 邀请者
    private Integer inviterUserId;

    // 受邀者
    private Integer beInvitedUserId;

    // 邀请者
    private String inviterUserOpenid;

    // 受邀者
    private String beInvitedUserOpenid;

    //创建时间
    private Date createTime;

}