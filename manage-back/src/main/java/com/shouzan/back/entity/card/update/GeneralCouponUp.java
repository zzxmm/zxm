package com.shouzan.back.entity.card.update;

import lombok.Data;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * @Author: bin.yang
 * @Date: 2018/12/28 11:32
 * @Description:  卡卷基础信息   修改用
 */
@Data
public class GeneralCouponUp implements Serializable {

    private static final long serialVersionUID = -6230599713324897042L;

    // 卡券基础信息
    @Valid
    private BaseInfoUp base_info;


}
