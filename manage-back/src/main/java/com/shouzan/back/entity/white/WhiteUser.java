package com.shouzan.back.entity.white;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: bin.yang
 * @Date: 2019/3/28 15:11
 * @Description:
 */
@Data
public class WhiteUser implements Serializable {

    private static final long serialVersionUID = 8571818472161565210L;

    // 主键
    private int id;

    // 手机号
    private String phone;

    // 录入人
    private int creatorId;

    // 录入时间
    private Date createTime;
}
