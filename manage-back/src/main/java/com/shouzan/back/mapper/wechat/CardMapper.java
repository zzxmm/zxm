package com.shouzan.back.mapper.wechat;

import com.shouzan.back.entity.card.Card;
import com.shouzan.back.entity.card.CardCoupon;
import com.shouzan.back.entity.card.GeneralCoupon;
import com.shouzan.back.entity.card.page.CardPage;
import com.shouzan.back.entity.card.update.CardUp;
import com.shouzan.back.vo.SearchSatisfy;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
/**
 * @Author: bin.yang
 * @Date: 2019/1/23 5:18 PM
 *
 * @Description:  公众号卡卷
 */
public interface CardMapper extends Mapper<Card>{

    int addWeChetCouponInfo(Card card);

    Card findCardInfo(int id);

    GeneralCoupon findGeneralInfo(int id);

    Integer findIdByCardId(@Param("cardId")String cardId);

    int updateWcCardInfoCard(@Param("userId") int userId, @Param("cardId") int cardId, @Param("card")CardUp card);

    int addOrUpdateMerCouponInfo(@Param("coupon")CardCoupon coupon, @Param("id")Integer id);

    CardCoupon findCouponInfo(int id);

    int queryPageCount(SearchSatisfy search);

    List<CardPage> queryPageList(SearchSatisfy search);

    int deleteWeChetCoupon(@Param("id")String id);

    int updateCouponStock(@Param("id")int id, @Param("quantity")int quantity);

    int insertWeChatCardId(@Param("cardId") String wcCardId, @Param("id") int id);

    int deleteWeChetCouponById(int id);
}