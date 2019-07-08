package com.shouzan.back.biz;


import com.shouzan.back.entity.achievement.AchievementsRule;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;

import java.util.List;

/**
 * @ClassName: com.shouzan.back.biz.AchievementRuleBiz
 * @Author: shouzan
 * @Date: 2019-06-26 15:55
 * @Description: TODO
 */
public interface AchievementRuleBiz {

    AchievementsRule selectByRuleAll() ;

    ObjectRestResponse updateInfo(AchievementsRule achievementsRule);

    ObjectRestResponse updateAchievementEnableStatus(Integer status, Integer userId);

    ObjectRestResponse findIAchievementEnableStatus();

    ObjectRestResponse findById(Integer id);

    int queryPageCount(SearchSatisfy search);

    List<AchievementsRule> queryPageList(SearchSatisfy search);
}
