package com.shouzan.back.entity.card.page;

import com.shouzan.back.entity.card.DateInfo;
import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @Author: bin.yang
 * @Date: 2019/1/10 12:08
 * @Description:  卡卷信息 分页查询用
 */
@Data
public class CardPage implements Serializable {

    private static final long serialVersionUID = -6546980587817901563L;

    private Integer id;

    //卡卷类型   优惠卷 : GENERAL_COUPON
    private String card_type;

    // 卡卷名   字数上限为9个汉字
    private String title;

    @Transient
    private DateInfo date_info;

    // 卡卷互通状态  0未互通   1已互通
    private String mutual_state;

    //卡券库存的数量，上限为100000000。
    private long quantity;

    // 卡卷ID
    private String card_id;
}
