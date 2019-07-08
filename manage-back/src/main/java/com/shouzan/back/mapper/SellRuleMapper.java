package com.shouzan.back.mapper;

import com.shouzan.back.entity.SellRule;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
/**
 * @Author: bin.yang
 * @Date: 2019/1/23 5:20 PM
 *
 * @Description:  售卖规则
 */
public interface SellRuleMapper extends Mapper<SellRule> {

    int addSellRule(SellRule sellRule);

    int updateSellRule(SellRule sellRule);

    List<SellRule> querySelRuleListByCouId(Integer id);

    int deleteSellRule(@Param("sid")String sid, @Param("cid")Integer cid);
}