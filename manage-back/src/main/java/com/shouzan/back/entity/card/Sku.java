package com.shouzan.back.entity.card;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: bin.yang
 * @Date: 2018/12/28 11:55
 * @Description:  库存
 */
@Data
public class Sku  implements Serializable {

    private static final long serialVersionUID = 318797080404934451L;

    //卡券库存的数量，上限为100000000。
    @NotNull(message = "卡卷库存不能为空")
    private long quantity = 0;
}
