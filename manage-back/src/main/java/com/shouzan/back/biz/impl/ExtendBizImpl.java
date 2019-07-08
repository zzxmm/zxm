package com.shouzan.back.biz.impl;

import com.shouzan.back.biz.ExtendBiz;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.extend.ExtendExchange;
import com.shouzan.back.entity.extend.ExtendRecord;
import com.shouzan.back.entity.extend.ExtendRule;
import com.shouzan.back.entity.extend.ExtendRuleParam;
import com.shouzan.back.mapper.extend.ExtendExchangeMapper;
import com.shouzan.back.mapper.extend.ExtendRecordMapper;
import com.shouzan.back.mapper.extend.ExtendRuleMapper;
import com.shouzan.back.mapper.extend.ExtendRuleParamMapper;
import com.shouzan.back.util.CodeValid;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2019/2/18 18:12
 * @Description:  分销系统
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ExtendBizImpl implements ExtendBiz {

    @Autowired
    private ExtendRuleMapper extendRuleMapper;

    @Autowired
    private ExtendRecordMapper extendRecordMapper;

    @Autowired
    private ExtendExchangeMapper extendExchangeMapper;

    @Autowired
    private ExtendRuleParamMapper extendRuleParamMapper;

    /**
     * @Description: (查询总条数)
     * @param search
     * @[param] [search]
     * @return int
     * @author:  bin.yang
     * @date:  2019/2/19 2:59 PM
     */
    @Override
    public int queryPageCount(SearchSatisfy search) {
        if(StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%"+search.getKeywords().trim()+"%");
        }else {
            search.setKeywords(null);
        }
        return extendRuleMapper.queryPageCount(search);
    }

    /**
     * @Description: (分页查询)
     * @param search
     * @[param] [search]
     * @return java.util.List<com.shouzan.back.entity.extend.ExtendRule>
     * @author:  bin.yang
     * @date:  2019/2/19 2:59 PM
     */
    @Override
    public List<ExtendRule> queryPageList(SearchSatisfy search) {
        if(StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%"+search.getKeywords().trim()+"%");
        }else {
            search.setKeywords(null);
        }
        return extendRuleMapper.queryPageList(search);
    }

    /**
     * @Description: (新增分销规则)
     * @param rule
     * @[param] [rule]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/2/19 3:00 PM
     */
    @Override
    public ObjectRestResponse addExtendRuleInfo(ExtendRule rule) {
//        int count = extendRuleMapper.findCountByType(rule.getRuleType());
//        if(count > 0){
            return new ObjectRestResponse().rel(Response.FAILURE).msg("创建失败 : 每一类型的规则只允许创建一条");
//        }
//        ObjectRestResponse response = ValidationExtendRule(rule);
//        if(!response.getRel()){
//            return response;
//        }
//        int i = extendRuleMapper.addExtendRuleInfo(rule);
//        if(i > 0){
//            if(rule.getRuleType() == 0){
//                extendRuleParamMapper.addExtendRuleParam(rule.getParam());
//            }
//        }
//        return CodeValid.CodeMsg(i,"添加规则");
    }

    /**
     * @Description: (修改规则信息)
     * @param rule
     * @[param] [rule]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/2/19 3:00 PM
     */
    @Override
    public ObjectRestResponse updateExtendRuleInfo(ExtendRule rule) {
        ObjectRestResponse response = ValidationExtendRule(rule);
        if(!response.getRel()){
            return response;
        }
        int i = extendRuleMapper.updateExtendRuleInfo(rule);
        if(i > 0){
            extendRuleParamMapper.deleteExtendRuleParam();
            extendRuleParamMapper.addExtendRuleParam(rule.getParam());
        }
        return CodeValid.CodeMsg(i,"修改规则");
    }

    /**
     * @Description: (批量修改状态)
     * @param id
     * @param status
     * @[param] [id, status]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/2/19 3:00 PM
     */
    @Override
    public ObjectRestResponse updateExtendRuleStatus(String id, int status) {
//        String[] split = id.split(",");
//        int[] intType = new int[split.length];
//        if(status == 1){
//            int flag = 0;
//            for (int i = 0; i < split.length; i++) {
//                ExtendRule rule = extendRuleMapper.findInfoById(Integer.parseInt(split[i]));
//                int count = extendRuleMapper.findCountByType(rule.getRuleType());
//                if(count > 0){
//                    flag = 1 ;
//                    break;
//                }
//                intType[i] = rule.getRuleType();
//            }
//            if(flag > 0){
//                return new ObjectRestResponse().rel(Response.FAILURE).msg("上架失败 : 每一类型的规则只允许启用一条");
//            }else{
//                boolean sim = false;
//                for (int i = 0; i < intType.length; i++) {
//                    for (int j = 0; j < intType.length; j++) {
//                        if(i != j){
//                            if(intType[i] == intType[j]){
//                                sim = true;
//                                break;
//                            }
//                        }
//                    }
//                    if(sim){
//                        break;
//                    }
//                }
//                if(sim){
//                    return new ObjectRestResponse().rel(Response.FAILURE).msg("上架失败 : 每一类型的规则只允许启用一条");
//                }else {
//                    int i = extendRuleMapper.updateExtendRuleStatus(id,status);
//                    return CodeValid.CodeMsg(i,"修改状态");
//                }
//            }
//        }else{
//            int i = extendRuleMapper.updateExtendRuleStatus(id,status);
            return CodeValid.CodeMsg(0,"修改状态");
//        }
    }

    /**
     * @Description: (分销规则详细信息查询)
     * @param id
     * @[param] [id]
     * @return com.shouzan.back.entity.extend.ExtendRule
     * @author:  bin.yang
     * @date:  2019/2/21 2:47 PM
     */
    @Override
    public ExtendRule findInfoById(int id) {
        ExtendRule rule = extendRuleMapper.findInfoById(id);
        List<ExtendRuleParam> list = extendRuleParamMapper.findInfoByRule();
        rule.setParam(list);
        return extendRuleMapper.findInfoById(id);
    }

    /**
     * @Description: (分销记录总条数查询)
     * @param search
     * @[param] [search]
     * @return int
     * @author:  bin.yang
     * @date:  2019/2/21 2:47 PM
     */
    @Override
    public int queryPageRecordCount(SearchSatisfy search) {
        if(StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%"+search.getKeywords().trim()+"%");
        }else {
            search.setKeywords(null);
        }
        return extendRecordMapper.queryPageRecordCount(search);
    }

    /**
     * @Description: (分销记录分页集合查询)
     * @param search
     * @[param] [search]
     * @return java.util.List<com.shouzan.back.entity.extend.ExtendRecord>
     * @author:  bin.yang
     * @date:  2019/2/21 2:48 PM
     */
    @Override
    public List<ExtendRecord> queryPageRecordList(SearchSatisfy search) {
        if(StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%"+search.getKeywords().trim()+"%");
        }else {
            search.setKeywords(null);
        }
        return extendRecordMapper.queryPageRecordList(search);
    }

    /**
     * @Description: 佣金提现分页查询总条数
     * @param search
     * @[param] [search]
     * @return int
     * @author:  bin.yang
     * @date:  2019/5/13 5:52 PM
     */
    @Override
    public int queryPageBonusCount(SearchSatisfy search) {
        if(StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%"+search.getKeywords().trim()+"%");
        }else {
            search.setKeywords(null);
        }
        return extendExchangeMapper.queryPageBonusCount(search);
    }

    /**
     * @Description: 佣金提现分页集合查询
     * @param search
     * @[param] [search]
     * @return java.util.List<com.shouzan.back.entity.extend.ExtendExchange>
     * @author:  bin.yang
     * @date:  2019/5/13 5:52 PM
     */
    @Override
    public List<ExtendExchange> queryPageBonusList(SearchSatisfy search) {
        if(StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%"+search.getKeywords().trim()+"%");
        }else {
            search.setKeywords(null);
        }
        return extendExchangeMapper.queryPageBonusList(search);
    }

    /**
     * @Description: 查询佣金提现详细信息
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/5/13 5:53 PM
     */
    @Override
    public ObjectRestResponse findInfoBonusById(int id) {
        ExtendExchange bonus = extendExchangeMapper.findInfoBonusById(id);
        if(bonus != null){
            List<ExtendRecord> list = extendExchangeMapper.selectBonusRecordList(id);
            return new ObjectRestResponse().rel(Response.SUCCESS).result(bonus).rows(list);
        }else{
            return new ObjectRestResponse().rel(Response.FAILURE).msg("查询信息失败,请刷新重试.");
        }
    }

    /**
     * @Description: 佣金提现操作
     * @param id
     * @param status
     * @param notes
     * @param userId
     * @[param] [id, status, notes, userId]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/5/13 5:53 PM
     */
    @Override
    public ObjectRestResponse updateBonusStatus(Integer id, Integer status, String notes, Integer userId) {
        int i = extendExchangeMapper.updateBonusStatus(id,status,notes,userId);
        return CodeValid.CodeMsg(i,"操作");
    }

    /**
     * @Description: 分销系统状态控制
     * @param status
     * @param userId
     * @[param] [status]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/5/13 5:53 PM
     */
    @Override
    public ObjectRestResponse updateEnableStatus(Integer status, Integer userId) {
        int i = extendRuleMapper.updateEnableStatus(status,userId);
        return CodeValid.CodeMsg(i,"分销系统全局状态操作");
    }

    @Override
    public ObjectRestResponse findEnableStatus() {
        return new ObjectRestResponse().rel(true).result(extendRuleMapper.findEnableStatus());
    }

    /**
     * @Auther: bin.yang
     * @Date: 2019/2/19 2:36 PM
     *
     * @Description:  效验规则信息参数
     */
    public ObjectRestResponse ValidationExtendRule(ExtendRule rule) {

        if(rule.getRestrictedLow() == null || rule.getRestrictedLow().compareTo(BigDecimal.ZERO) < 0){
            return new ObjectRestResponse().rel(Response.FAILURE).msg("提现下限不能为空或小于0");
        }
        if(rule.getRestrictedUp() == null || rule.getRestrictedUp().compareTo(rule.getRestrictedLow()) < 0){
            return new ObjectRestResponse().rel(Response.FAILURE).msg("提现上限不能为空或小于下限");
        }
        List<ExtendRuleParam> param = rule.getParam();

        // 快速排序
        if(param != null && param.size() > 0){
            Collections.sort(param, new Comparator<ExtendRuleParam>() {
                @Override
                public int compare(ExtendRuleParam o1, ExtendRuleParam o2) {
                    if(o1.getLevel() < o2.getLevel()){
                        return -1;
                    }
                    if(o1.getLevel() < o2.getLevel()){
                        return 0;
                    }
                    return 1;
                }
            });

            for (int i = 0; i < param.size(); i++) {
                if(i == 0){
                    if(param.get(i).getMinNum() == null ||  param.get(i).getMinNum() < 0){
                        return new ObjectRestResponse().rel(Response.FAILURE).msg("请合理填写邀请等级规则 : 等级人数不应该少于0人");
                    }
                }else if(i == param.size() -1){
                    if(param.get(i).getMinNum() == null || param.get(i).getMinNum() < param.get(i - 1).getMinNum()){
                        return new ObjectRestResponse().rel(Response.FAILURE).msg("请合理填写邀请等级规则 : 高等级人数应该大于低等级人数");
                    }
                }else {
                    if (param.get(i).getMinNum() == null || param.get(i).getMinNum() < param.get(i - 1).getMinNum()) {
                        return new ObjectRestResponse().rel(Response.FAILURE).msg("请合理填写邀请等级规则 : 高等级人数应该大于低等级人数");
                    }
                    if (param.get(i - 1).getMaxNum() == null || param.get(i).getMinNum() - param.get(i - 1).getMaxNum() != 1) {
                        return new ObjectRestResponse().rel(Response.FAILURE).msg("请合理填写邀请等级规则 : 等级人数直接不应该空隙");
                    }
                }
            }
            for (int i = 0; i < param.size(); i++) {
                if(i == 0){
                    if(param.get(i).getPercentage() == null || param.get(i).getPercentage().compareTo(BigDecimal.ZERO) < 0){
                        return new ObjectRestResponse().rel(Response.FAILURE).msg("请合理填写等级返佣比例 : 返佣比例不应该是空的,或者小于0.0001%");
                    }
                }else {
                    if (param.get(i).getPercentage() != null && param.get(i).getPercentage().compareTo(param.get(i - 1).getPercentage()) < 1) {
                        return new ObjectRestResponse().rel(Response.FAILURE).msg("请合理填写等级返佣比例 : 高等级返佣应该大于低等级返佣比例");
                    }
                }
            }
            for (int i = 0; i < param.size(); i++) {
                if(i == 0){
                    if(param.get(i).getInviterReward() == null || param.get(i).getInviterReward().compareTo(BigDecimal.ZERO) < 0){
                        return new ObjectRestResponse().rel(Response.FAILURE).msg("请合理填写等级邀请奖励 : 邀请奖励不应该是空的,或者小于0.01元");
                    }
                }else {
                    if (param.get(i).getPercentage() != null && param.get(i).getPercentage().compareTo(param.get(i - 1).getPercentage()) < 1) {
                        return new ObjectRestResponse().rel(Response.FAILURE).msg("请合理填写等级邀请奖励: 高等级奖励应该大于低等级邀请奖励");
                    }
                }
            }
        }
        return new ObjectRestResponse().rel(Response.SUCCESS);
    }
}
