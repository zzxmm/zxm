package com.shouzan.back.mapper.wechat;

import com.shouzan.back.entity.card.BaseInfo;
import com.shouzan.back.entity.card.update.BaseInfoUp;
import org.apache.ibatis.annotations.Param;
/**
 * @Author: bin.yang
 * @Date: 2019/1/23 5:17 PM
 *
 * @Description:  公众号卡卷 基础信息
 */
public interface BaseInfoMapper {

    int addWeChetCouponBase(BaseInfo baseInfo);

    BaseInfo findBaseInfo(int id);

    int updateWcCardInfoBase(@Param("beanJson") BaseInfoUp beanJson, @Param("cardId") int cardId);

    int deleteWeChetCouponByCardId(int id);

    int insertWeChatCenterUrl(@Param("url")String url,@Param("id") int id);
}