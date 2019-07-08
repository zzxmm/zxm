package com.shouzan.back.mapper.achievement;

import com.shouzan.back.entity.achievement.AchievementsRecord;
import com.shouzan.back.vo.SearchSatisfy;

import java.util.List;

public interface AchievementsRecordMapper {

    int queryPageCount(SearchSatisfy search);

    List queryPageList(SearchSatisfy search);

    List<AchievementsRecord> selectBonusRecordList(int id);
}