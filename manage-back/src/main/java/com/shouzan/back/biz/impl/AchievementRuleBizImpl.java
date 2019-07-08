package com.shouzan.back.biz.impl;

import com.shouzan.back.biz.AchievementRuleBiz;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.Activity;
import com.shouzan.back.entity.achievement.AchievementsRule;
import com.shouzan.back.mapper.achievement.AchievementsRuleMapper;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: com.shouzan.back.biz.impl.AchievementRuleBizImpl
 * @Author: shouzan
 * @Date: 2019-06-26 15:56
 * @Description: TODO
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class AchievementRuleBizImpl implements AchievementRuleBiz {

    @Autowired
    private AchievementsRuleMapper achievementsRuleMapper;

    /**
     * @return com.shouzan.back.entity.achievement.AchievementsRule
     * @Description: 绩效规则查询
     * @[param] []
     * @author: man.z
     * @date: 2019-07-01 16:19
     */
    @Override
    public AchievementsRule selectByRuleAll() {
        AchievementsRule achievementsRule = achievementsRuleMapper.selectRule();
        return achievementsRule;
    }

    /**
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @Description: 绩效规则修改
     * @[param] [achievementsRule]
     * @author: man.z
     * @date: 2019-07-01 16:19
     */
    @Override
    public ObjectRestResponse updateInfo(AchievementsRule achievementsRule) {
        int i = achievementsRuleMapper.updateById(achievementsRule);
        if (i > 0) {
            return new ObjectRestResponse().msg("修改成功").rel(Response.SUCCESS);
        } else {
            return new ObjectRestResponse().msg("修改失败").rel(Response.FAILURE);
        }
    }

    /***
     * @Description: 修改绩效规则全局状态

     * @[param] [status, userId]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author: man.z
     * @date: 2019-07-01 16:20
     */
    @Override
    public ObjectRestResponse updateAchievementEnableStatus(Integer status, Integer userId) {
        int i = achievementsRuleMapper.updateAchievementEnableStatus(status, userId);

        if (i > 0) {
            return new ObjectRestResponse().msg("修改成功").rel(Response.SUCCESS);
        } else {
            return new ObjectRestResponse().msg("修改失败").rel(Response.FAILURE);
        }
    }

    /***
     * @Description: 查询绩效规则状态

     * @[param] []
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author: man.z
     * @date: 2019-07-01 16:20
     */
    @Override
    public ObjectRestResponse findIAchievementEnableStatus() {
        return new ObjectRestResponse().result(achievementsRuleMapper.findAchievementEnableStatus()).rel(Response.SUCCESS);
    }

    /***
     * @Description: 绩效规则详情

     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author: man.z
     * @date: 2019-07-01 16:20
     */
    @Override
    public ObjectRestResponse findById(Integer id) {
        AchievementsRule achievementsRule = achievementsRuleMapper.selectRuleById(id);

        if (achievementsRule != null) {
            return new ObjectRestResponse<Activity>().rel(Response.SUCCESS).result(achievementsRule);
        }else{
            return new ObjectRestResponse<Activity>().rel(Response.FAILURE).msg("未找到相关规则信息");
        }
    }

    /***
     * @Description: 查询总条数

     * @[param] [search]
     * @return int
     * @author: man.z
     * @date: 2019-07-01 16:21
     */
    @Override
    public int queryPageCount(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())) {
            search.setKeywords("%" + search.getKeywords().trim() + "%");
        } else {
            search.setKeywords(null);
        }
        return achievementsRuleMapper.queryPageCount(search);
    }

    /***
     * @Description: 查询数据集合

     * @[param] [search]
     * @return java.util.List<com.shouzan.back.entity.Activity>
     * @author: man.z
     * @date: 2019-07-01 16:21
     */
    @Override
    public List<AchievementsRule> queryPageList(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())) {
            search.setKeywords("%" + search.getKeywords().trim() + "%");
        } else {
            search.setKeywords(null);
        }
        return achievementsRuleMapper.queryPageList(search);
    }
}
