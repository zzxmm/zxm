package com.shouzan.back.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: bin.yang
 * @Date: 2019/4/7 15:44
 * @Description:   库存修改
 */
@Data
public class Stock implements Serializable {

    private static final long serialVersionUID = 6755678883603748513L;

    // 卡卷ID
    @NotNull(message = "卡卷ID不能为空")
    private Integer id;

    //  增加多少库存，支持不填或填0。
    private Integer increaseValue;

    //  减少多少库存，支持不填或填0。
    private Integer reduceValue;

}
