package com.shouzan.back.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
/**
 *
 * 功能描述:   类型表
 *
 * @auther: bin.yang
 * @date: 2018/9/11 上午10:22
 */
@Data
public class Types implements Serializable {

    private static final long serialVersionUID = -6307492221552071967L;

    private Integer id;

    // 类型名称
    @NotNull(message = "类型名称不能为空")
    private String typeName;

    //类型简称
    private String typeShotName;

    //类型简介
    private String typeIntroduce;

    //注意事项
    private String matter;

    //创建时间
    private Date createTime;

    //创建人ID
    private int creatorId;

    //修改人
    private int lastEditId ;

    //修改时间
    private Date lastEditTime;

    //类型对象
    @NotNull(message = "请选择类型对象")
    private Byte typeBelonged;

    //类型状态
    private Byte enableState;

}