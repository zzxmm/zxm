package com.shouzan.back.mapper;

import com.shouzan.back.entity.ActivityParam;
/**
 * @Author: bin.yang
 * @Date: 2019/1/23 5:16 PM
 *
 * @Description:  活动详情
 */
public interface ActivityParamMapper {

    int updateActivityParam(ActivityParam activityParam);

    ActivityParam selectParamInfoById(Integer activityParamId);
}