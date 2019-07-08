package com.shouzan.back.mapper.achievement;

import com.shouzan.back.entity.achievement.AchievementsRule;
import com.shouzan.back.vo.SearchSatisfy;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface AchievementsRuleMapper {

    AchievementsRule selectRule();

    AchievementsRule selectRuleById(Integer id);

    int updateById(AchievementsRule record);

    int updateAchievementEnableStatus(@Param("status")Integer status, @Param("userId")Integer userId);

    int findAchievementEnableStatus();

    int queryPageCount(SearchSatisfy search);

    List queryPageList(SearchSatisfy search);

}