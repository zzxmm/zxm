package com.shouzan.back.biz;


import com.shouzan.back.entity.achievement.AchievementsExchange;
import com.shouzan.back.entity.achievement.AchievementsRecord;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;

import java.util.List;

/**
 * @ClassName: com.shouzan.back.biz.AchievementRecordBiz
 * @Author: shouzan
 * @Date: 2019-06-28 14:42
 * @Description: TODO
 */
public interface AchievementRecordBiz {

    int queryPageCount(SearchSatisfy search);

    List<AchievementsRecord> queryPageList(SearchSatisfy search);

    int queryPageBonusCount(SearchSatisfy search);

    List<AchievementsExchange> queryPageBonusList(SearchSatisfy search);

    ObjectRestResponse findInfoBonusById(int id);

    ObjectRestResponse updateBonusStatus(Integer id, Integer status, String notes, Integer userId);

}
