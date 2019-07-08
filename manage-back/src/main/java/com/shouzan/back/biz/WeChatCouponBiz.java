package com.shouzan.back.biz;

import com.shouzan.back.entity.card.CardBeanJson;
import com.shouzan.back.entity.card.page.CardPage;
import com.shouzan.back.entity.card.update.CardUp;
import com.shouzan.back.entity.card.update.StockUp;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2019/1/2 14:14
 * @Description:  微信公众号卡卷
 */
public interface WeChatCouponBiz {

    int addWeChetCouponInfo(CardBeanJson card, String UserId);

    CardBeanJson findInfoWeChetCouById(int id);

    int updateWcCardInfo(CardUp beanJson, String currentUserId);

    ObjectRestResponse updateWcCardStock(StockUp stock);

    int queryPageCount(SearchSatisfy search);

    List<CardPage> queryPageList(SearchSatisfy search);

    ObjectRestResponse deleteWeChetCoupon(String id);

    ObjectRestResponse validWcChatCoupon(CardBeanJson beanJson);

    int insertWeChatCardId(String wcCardId, int id, String url);

    int deleteWeChetCouponById(int id);

    ObjectRestResponse<CardUp> updateValidJsonData(CardUp beanJson);
}
