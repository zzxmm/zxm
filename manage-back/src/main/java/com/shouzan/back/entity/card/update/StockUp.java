package com.shouzan.back.entity.card.update;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: bin.yang
 * @Date: 2019/1/7 15:44
 * @Description:  修改用 库存
 */
@Data
public class StockUp implements Serializable {

    private static final long serialVersionUID = -7661214421785668169L;

    // 卡卷ID
    @NotNull(message = "微信卡卷ID不能为空")
    private String card_id;

    //  增加多少库存，支持不填或填0。
    private Integer increase_stock_value;

    //  减少多少库存，可以不填或填0。
    private Integer reduce_stock_value;

}
