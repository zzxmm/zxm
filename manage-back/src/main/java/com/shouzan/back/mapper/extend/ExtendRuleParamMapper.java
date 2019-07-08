package com.shouzan.back.mapper.extend;

import com.shouzan.back.entity.extend.ExtendRuleParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * @author: bin.yang
 * @Date: 2019/5/10 10:34 AM
 *
 * @Description: description
 */
public interface ExtendRuleParamMapper  {

    void addExtendRuleParam(List<ExtendRuleParam> list);

    void deleteExtendRuleParam();

    List<ExtendRuleParam> findInfoByRule();
}