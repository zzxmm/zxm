package com.shouzan.back.mapper;

import com.shouzan.back.entity.PurchaseRule;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
/**
 * @Author: bin.yang
 * @Date: 2019/1/23 5:19 PM
 *
 * @Description:  限购规则
 */
public interface PurchaseRuleMapper extends Mapper<PurchaseRule> {

    int addPurchaseRule(PurchaseRule purchaseRule);

    int updatePurchaseRule(PurchaseRule purchaseRule);

    List<PurchaseRule> queryPurRuleListByCouId(Integer id);

    int deletePurcRull(@Param("pid") String pid, @Param("cid")Integer cid);
}