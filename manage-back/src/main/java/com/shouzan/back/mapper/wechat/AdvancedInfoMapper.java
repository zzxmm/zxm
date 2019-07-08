package com.shouzan.back.mapper.wechat;

import com.shouzan.back.entity.card.AbstractTemp;
import com.shouzan.back.entity.card.TextImageList;
import com.shouzan.back.entity.card.TimeLimit;
import com.shouzan.back.entity.card.UseCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * @Author: bin.yang
 * @Date: 2019/1/23 5:16 PM
 *
 * @Description:   公众号卡卷 高级信息
 */
public interface AdvancedInfoMapper {

    int addWeChetCouponUse(@Param("use") UseCondition useCondition, @Param("cardId") Integer cardId);

    int addWeChetCouponAbs(@Param("abs")AbstractTemp abstractTemp, @Param("cardId")Integer cardId);

    int addWeChetCouponAbsIcon(@Param("list")List<String> list, @Param("cardId")Integer cardId);

    int addWeChetCouponTextImg(@Param("list")List<TextImageList> list,@Param("cardId") Integer cardId);

    int addWeChetCouponTime(@Param("list")List<TimeLimit> list, @Param("cardId")Integer cardId);

    int addWeChetCouponBus(@Param("list") List<String> list, @Param("cardId")Integer cardId);

    UseCondition findUseInfo(int id);

    AbstractTemp findAbsInfo(int id);

    List<TextImageList> findTextImgInfo(int id);

    List<TimeLimit> findTimeInfo(int id);

    List<String> findBusInfo(int id);

    List<String> findIconInfo(int id);

    int deleteWeChetCouponByCardId(int id);
}