package com.shouzan.back.entity.extend;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.math.BigDecimal;
/**
 * @author: bin.yang
 * @Date: 2019/5/9 3:56 PM
 *
 * @Description: description
 */
@Data
public class ExtendRuleParam implements Serializable {

    private static final long serialVersionUID = 8770120702774020181L;
    
    private Integer id;

    /**
     * 级别
     */
    private Integer level;

    /**
     * 最少人数
     */
    private Integer minNum;

    /**
     * 最多人数
     */
    private Integer maxNum;

    /**
     * 比例
     */
    private BigDecimal percentage;

    /**邀请奖励*/
    private BigDecimal inviterReward;

}