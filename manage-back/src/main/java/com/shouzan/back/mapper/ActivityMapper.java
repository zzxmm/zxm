package com.shouzan.back.mapper;

import com.shouzan.back.entity.Activity;
import com.shouzan.back.entity.ActivityParam;
import com.shouzan.back.vo.SearchSatisfy;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
/**
 * @Author: bin.yang
 * @Date: 2019/1/23 5:16 PM
 *
 * @Description:   活动
 */
public interface ActivityMapper extends Mapper<Activity> {

    int queryPageCount(SearchSatisfy search);

    List<Activity> queryPageList(SearchSatisfy search);

    Activity activityInfoById(int id);

    int addInfo(Activity entity);

    int addParamInfo(ActivityParam activityParam);

    int updateActivityInfo(Activity entity);

    int updateStatusUp(@Param("id") String id,@Param("date")String date);

    int updateStatusDown(@Param("id") String id, @Param("date")String date);

    int updateStatusByCouUp(@Param("id") String id, @Param("status") Integer status,@Param("date")String date);

    int updateStatusByCouDown(@Param("id") String id, @Param("status") Integer status,@Param("date")String date);

    int updateActivityStatus(@Param("id") String id, @Param("status") Integer status);

    int updateStatusByCoupon(@Param("couId") Integer couId, @Param("status") Integer status);

    int updateStatusByGooods(@Param("id") String id, @Param("status") Integer status);
}