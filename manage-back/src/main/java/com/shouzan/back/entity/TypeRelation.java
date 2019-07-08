package com.shouzan.back.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class TypeRelation implements Serializable {

    private static final long serialVersionUID = 2544329499695080604L;

    // 主键
    private Integer id;

    //商户ID
    private Integer merchantId;

    // 类型ID
    private Integer typeId;

}
