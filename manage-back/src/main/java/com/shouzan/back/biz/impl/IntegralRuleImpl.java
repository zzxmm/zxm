package com.shouzan.back.biz.impl;

import com.shouzan.back.biz.IntegralRuleBiz;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.integral.IntegralRule;
import com.shouzan.back.mapper.integral.IntegralRuleMapper;
import com.shouzan.back.util.CodeValid;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: com.shouzan.back.biz.impl.IntegralRuleImpl
 * @Author: shouzan
 * @Date: 2019-05-29 11:59
 * @Description: TODO
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class IntegralRuleImpl implements IntegralRuleBiz {
    @Autowired
    private IntegralRuleMapper integralRuleMapper;

    /**
     * @Description: 查看积分规则详情

     * @[param] [id]
     * @return com.shouzan.back.entity.integral.IntegralRule
     * @author:  man.z
     * @date:  2019-05-29 12:04
     */
    @Override
    public ObjectRestResponse <IntegralRule> selectById(Integer id) {
        return new ObjectRestResponse<IntegralRule>().result(integralRuleMapper.selectById(id)).rel(Response.SUCCESS);
    }

    /**
     * @Description: 修改积分规则

     * @[param] [record]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.integral.IntegralRule>
     * @author:  man.z
     * @date:  2019-05-29 12:04
     */
    @Override
    public ObjectRestResponse<IntegralRule> updateByIdInfo(IntegralRule entity) {
        Byte type = entity.getRuleType();
//     积分类型： 0购买 1分享 3新人初始化
        if(type==0){
            Integer dayLimit = entity.getDayLimit();
            Integer accountLimit = entity.getAccountLimit();
            if(dayLimit!=null && accountLimit!=null){
                    return new ObjectRestResponse().result(integralRuleMapper.updateByIdSelective(entity)).rel(Response.SUCCESS);
            }else {
                //不能为空
                return new ObjectRestResponse().rel(Response.FAILURE).msg("每日限制,账号限制均不能为空");
            }
        }else if(type==1){
            Integer dayLimit = entity.getDayLimit();
            Integer accountLimit = entity.getAccountLimit();
            Integer obtainIntegral = entity.getObtainIntegral();
            if(dayLimit!=null && accountLimit!=null && obtainIntegral!=null){
                return new ObjectRestResponse().result(integralRuleMapper.updateByIdSelective(entity)).rel(Response.SUCCESS);
            }else {
                //不能为空
                return new ObjectRestResponse().rel(Response.FAILURE).msg("获得积分,每日限制,账号限制均不能为空");
            }
        }else if(type==3){
            Integer integral = entity.getObtainIntegral();
            if(integral != null ){
                return new ObjectRestResponse().result(integralRuleMapper.updateByIdSelective(entity)).rel(Response.SUCCESS);
            }else {
                return new ObjectRestResponse<>().rel(Response.FAILURE).msg("获得积分不能为空");
            }
        }else {
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("积分类型不匹配");
        }

    }

    /**
     * @Description: 计算积分规则总条数

     * @[param] [search]
     * @return int
     * @author:  man.z
     * @date:  2019-05-29 12:05
     */
    @Override
    public int queryPageCount(SearchSatisfy search) {
        if(StringUtils.isNoneBlank(search.getKeywords())){
            search.setKeywords("%"+search.getKeywords().trim()+"%");
        }else {
             search.setKeywords(null);
        }
        return integralRuleMapper.queryPageCount(search);
    }

    /**
     * @Description: 查看积分规则列表

     * @[param] [search]
     * @return java.util.List
     * @author:  man.z
     * @date:  2019-05-29 12:05
     */
    @Override
    public List queryPageList(SearchSatisfy search) {
        if (StringUtils.isNoneBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() + "%");
        }else {
            search.setKeywords(null);
        }
        return integralRuleMapper.queryPageList(search);
    }

    @Override
    public ObjectRestResponse updateIntegralEnableStatus(Integer status, Integer userId) {
        int i = integralRuleMapper.updateIntegralEnableStatus(status, userId);
        return CodeValid.CodeMsg(i,"分销系统全局状态操作");
    }

    @Override
    public ObjectRestResponse findIntegralEnableStatus() {
        return new ObjectRestResponse().result(integralRuleMapper.findIntegralEnableStatus()).rel(true);
    }
}
