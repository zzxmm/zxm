package com.shouzan.back.mapper.extend;

import com.shouzan.back.entity.extend.ExtendRule;
import com.shouzan.back.vo.SearchSatisfy;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2019/2/18 6:24 PM
 *
 * @Description:  分销规则
 */
public interface ExtendRuleMapper {

    int queryPageCount(SearchSatisfy search);

    List<ExtendRule> queryPageList(SearchSatisfy search);

    int findCountByType(Byte ruleType);

    int addExtendRuleInfo(ExtendRule rule);

    int updateExtendRuleInfo(ExtendRule rule);

    ExtendRule findInfoById(int id);

    int updateExtendRuleStatus(@Param("id") String id,@Param("status") int status);

    int updateEnableStatus(@Param("status")Integer status, @Param("userId")Integer userId);

    int findEnableStatus();
}