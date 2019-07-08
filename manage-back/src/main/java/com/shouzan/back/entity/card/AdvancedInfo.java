package com.shouzan.back.entity.card;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/12/28 11:42
 * @Description:  公众号卡卷 高级信息
 */
@Data
public class AdvancedInfo implements Serializable {

    private static final long serialVersionUID = -5460049627493275467L;

    //使用门槛（条件）字段，若不填写使用条件则在券面拼写 ：无最低消费限制，全场通用，不限品类；并在使用说明显示： 可与其他优惠共享
    private UseCondition use_condition;

    //封面摘要结构体名称
    @JsonProperty("abstract")
    @JSONField(name = "abstract")
    private AbstractTemp abstractTemp;

    //图文列表，显示在详情内页 ，优惠券券开发者须至少传入 一组图文列表
    private List<TextImageList> text_image_list;

    //使用时段限制，包含以下字段
    private List<TimeLimit> time_limit;

    //商家服务类型： BIZ_SERVICE_DELIVER 外卖服务； BIZ_SERVICE_FREE_PARK 停车位； BIZ_SERVICE_WITH_PET 可带宠物； BIZ_SERVICE_FREE_WIFI 免费wifi， 可多选
    private List<String> business_service;

}
