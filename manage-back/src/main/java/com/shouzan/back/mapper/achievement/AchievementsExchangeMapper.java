package com.shouzan.back.mapper.achievement;


import com.shouzan.back.entity.achievement.AchievementsExchange;
import com.shouzan.back.entity.achievement.AchievementsRecord;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AchievementsExchangeMapper {

    int queryPageBonusCount(SearchSatisfy search);

    List<AchievementsExchange> queryPageBonusList(SearchSatisfy search);

    AchievementsExchange findInfoBonusById(int id);

    int updateBonusStatus(@Param("id") Integer id, @Param("status") Integer status, @Param("notes") String notes, @Param("userId") Integer userId);
}