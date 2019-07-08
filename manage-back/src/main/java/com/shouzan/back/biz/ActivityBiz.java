package com.shouzan.back.biz;

import com.shouzan.back.entity.Activity;
import com.shouzan.back.entity.ActivityParam;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/9/14 10:37
 * @Description:  活动管理
 */
public interface ActivityBiz {

    int queryPageCount(SearchSatisfy search);

    List<Activity> queryPageList(SearchSatisfy search);

    ObjectRestResponse<Activity> activityInfoById(int id);

    ObjectRestResponse<Activity> updateStatus(String id, Integer status);

    ObjectRestResponse<Activity> addInfo(Activity entity, ActivityParam activityParam);

    ObjectRestResponse<Activity> updateActivityAndParam(Activity entity, ActivityParam activityParam);
}