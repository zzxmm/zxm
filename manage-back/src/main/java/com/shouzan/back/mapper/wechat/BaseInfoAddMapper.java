package com.shouzan.back.mapper.wechat;

import com.shouzan.back.entity.card.DateInfo;
import com.shouzan.back.entity.card.Sku;
import com.shouzan.back.entity.card.update.DateInfoUp;
import com.shouzan.back.entity.card.update.StockUp;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * @Author: bin.yang
 * @Date: 2019/1/23 5:17 PM
 *
 * @Description:  公众号卡卷 基础附加信息
 */
public interface BaseInfoAddMapper {

    int addWeChetCouponData(@Param("data") DateInfo dateInfo, @Param("cardId") Integer cardId);

    int addWeChetCouponSku(@Param("sku") Sku sku, @Param("cardId") Integer cardId);

    int addWeChetCouponPoiId(@Param("list") List<Integer> list, @Param("cardId")Integer cardId);

    DateInfo findDataInfo(int id);

    Sku findSkuInfo(int id);

    List<Integer> findPoiInfo(int id);

    int updateWcCardInfoBaseType(@Param("beanJson") DateInfoUp beanJson, @Param("id") int id);

    int findDataInfoIdByCardId(int cardId);

    int deleteWcCardPoiIdList(int id);

    int findSkuIdByCardId(int id);

    int updateWcCardInfoBaseSku(@Param("stock")StockUp stock, @Param("skuId")int skuId);

    int deleteWeChetCouponByCardId(int id);
}