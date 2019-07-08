package com.shouzan.back.entity.operate;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * @Author: bin.yang
 * @Date: 2019/4/10 12:33 PM
 *
 * @Description:  串码表
 */
@Data
public class CodeList implements Serializable {

    private static final long serialVersionUID = -588388489367483551L;

    // 主键
    private Integer id;

    //串码库ID
    private Integer baseId;

    // 串码
    private String code;

    //录入人
    private Integer creatorId;

    //录入时间
    private Date createTime;
}