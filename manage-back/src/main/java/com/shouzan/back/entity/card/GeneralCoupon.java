package com.shouzan.back.entity.card;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: bin.yang
 * @Date: 2018/12/28 11:32
 * @Description:  卡卷 信息 结构 表
 */
@Data
public class GeneralCoupon implements Serializable {

    private static final long serialVersionUID = -2843548550265401201L;

    // 优惠券专用，填写优惠详情。
    @NotNull(message = "填写优惠详情")
    private String default_detail;

    // 卡券高级信息
    @Valid
    private AdvancedInfo advanced_info;

    // 卡券基础信息
    @Valid
    private BaseInfo base_info;

}
