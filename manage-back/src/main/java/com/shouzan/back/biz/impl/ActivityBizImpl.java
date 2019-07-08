package com.shouzan.back.biz.impl;

import com.shouzan.back.biz.ActivityBiz;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.Activity;
import com.shouzan.back.entity.ActivityParam;
import com.shouzan.back.entity.Advert;
import com.shouzan.back.entity.Coupon;
import com.shouzan.back.mapper.ActivityMapper;
import com.shouzan.back.mapper.ActivityParamMapper;
import com.shouzan.back.mapper.CouponMapper;
import com.shouzan.back.util.CodeValid;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.biz.BaseBiz;
import com.shouzan.common.msg.ObjectRestResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/9/14 10:37
 * @Description:  活动
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ActivityBizImpl extends BaseBiz<ActivityMapper, Activity> implements ActivityBiz {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private ActivityParamMapper activityParamMapper;

    /**
     * @Description: (查询总条数)
     * @param search
     * @[param] [search]
     * @return int
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:36
     */
    @Override
    public int queryPageCount(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() +"%");
        }else{
            search.setKeywords(null);
        }
        return activityMapper.queryPageCount(search);
    }

    /**
     * @Description: (查询数据集合)
     * @param search
     * @[param] [search]
     * @return java.util.List<com.shouzan.back.entity.Activity>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:37
     */
    @Override
    public List<Activity> queryPageList(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() +"%");
        }else{
            search.setKeywords(null);
        }
        return activityMapper.queryPageList(search);
    }

    /**
     * @Description: (通过ID查询详细信息)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Activity>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:37
     */
    @Override
    public ObjectRestResponse<Activity> activityInfoById(int id) {
        Activity activity = activityMapper.activityInfoById(id);
        ActivityParam activityParam = null;
        if(activity != null){
            Coupon coupon = couponMapper.couponInfoById(activity.getGoodsId());
            activity.setCoupon(coupon);
            activityParam = activityParamMapper.selectParamInfoById(activity.getActivityParamId());
            return new ObjectRestResponse<Activity>().rel(Response.SUCCESS).result(activity.data(activityParam));
        }else{
            return new ObjectRestResponse<Activity>().rel(Response.FAILURE).result(null).msg("未找到相关活动信息");
        }
    }

    /**
     * @Description: (修改状态)
     * @param id
     * @param status
     * @[param] [id, status]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Activity>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:38
     */
    @Override
    public ObjectRestResponse<Activity> updateStatus(String id, Integer status) {
        ObjectRestResponse<Activity> response = new ObjectRestResponse<>();
        String[] split = id.split(",");
        Date date = new Date();
        boolean flag = true;
        if (status == 0){
            for (int i = 0; i < split.length; i++) {
                String s = split[i];
                Activity activity = activityMapper.activityInfoById(Integer.parseInt(split[i]));
                if(date.getTime() < activity.getActivityStartTime().getTime()){
                    response.msg(activity.getActivityName()+" , 活动未到上架时间");
                    response.rel(Response.FAILURE);
                    flag=false;
                    break;
                }else if(date.getTime() > activity.getActivityEndTime().getTime()){
                    response.msg(activity.getActivityName()+" , 活动已过期");
                    response.rel(Response.FAILURE);
                    flag=false;
                    break;
                }else if(couponMapper.couponInfoById(activity.getGoodsId()).getCouponState() == 3){
                    response.msg(activity.getActivityName()+" , 所属卡卷已下架");
                    response.rel(Response.FAILURE);
                    flag=false;
                    break;
                }
            }
            if(flag){
                return CodeValid.CodeMsg(activityMapper.updateActivityStatus(id,status),"活动上架");
            }else{
                return response;
            }
        }else{
            for (int i = 0; i < split.length; i++) {
                String s = split[i];
                Activity activity = activityMapper.activityInfoById(Integer.parseInt(split[i]));
                if(date.getTime() > activity.getActivityEndTime().getTime()){
                    response.msg(activity.getActivityName()+" , 活动已过期");
                    response.rel(Response.FAILURE);
                    flag=false;
                    break;
                }
            }
            if(flag){
                return CodeValid.CodeMsg(activityMapper.updateActivityStatus(id,status),"活动下架");
            }else{
                return response;
            }
        }
    }

    /**
     * @Description: (添加活动)
     * @param entity
     * @param activityParam
     * @[param] [entity, activityParam]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Activity>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:38
     */
    @Override
    public ObjectRestResponse<Activity> addInfo(Activity entity, ActivityParam activityParam) {
        Coupon coupon = couponMapper.couponInfoById(entity.getGoodsId());
        if(entity.getActivityStartTime().getTime() < coupon.getUpshelfTime().getTime()){
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("活动开始时间不能早于卡卷上架时间");
        }else if(entity.getActivityEndTime().getTime() > coupon.getDownshelfTime().getTime() ){
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("活动结束时间不能晚于于卡卷下架时间");
        }else if(entity.getActivityState() == 0){
            Date date = new Date();
            if(date.getTime() < entity.getActivityStartTime().getTime()){
                return new ObjectRestResponse<>().rel(Response.FAILURE).msg("活动未到上架时间 , 更换状态");
            }
        }
        ObjectRestResponse<Activity> response = new ObjectRestResponse<>();
        int paramFlag = activityMapper.addParamInfo(activityParam);
        if(paramFlag>0){
            entity.setActivityParamId(activityParam.getId());
            return CodeValid.CodeMsg(activityMapper.addInfo(entity),"添加");
        }else {
            response.rel(Response.FAILURE);
            response.msg("添加失败");
            return response;
        }
    }

    /**
     * @Description: (修改活动信息)
     * @param entity
     * @param activityParam
     * @[param] [entity, activityParam]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Activity>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:38
     */
    @Override
    public ObjectRestResponse<Activity> updateActivityAndParam(Activity entity, ActivityParam activityParam) {
        Coupon coupon = couponMapper.couponInfoById(entity.getGoodsId());
        if(entity.getActivityStartTime().getTime() < coupon.getUpshelfTime().getTime()){
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("活动开始时间不能早于卡卷上架时间");
        }else if(entity.getActivityEndTime().getTime() > coupon.getDownshelfTime().getTime() ){
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("活动结束时间不能晚于于卡卷下架时间");
        }else if(entity.getActivityState() == 0){
            Date date = new Date();
            if(date.getTime() < entity.getActivityStartTime().getTime()){
                return new ObjectRestResponse<>().rel(Response.FAILURE).msg("活动未到上架时间 , 更换状态");
            }
        }
        int flag = activityMapper.updateActivityInfo(entity);
        if(flag > 0){
            Activity activity = activityMapper.activityInfoById(entity.getId());
            activityParam.setId(activity.getActivityParamId());
            return CodeValid.CodeMsg(activityParamMapper.updateActivityParam(activityParam),"修改");
        }else {
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("修改信息失败");
        }
    }

}
