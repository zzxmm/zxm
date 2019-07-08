package com.shouzan.back.biz.impl;

import com.shouzan.back.biz.WeChatCouponBiz;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.Bank;
import com.shouzan.back.entity.Merchants;
import com.shouzan.back.entity.card.*;
import com.shouzan.back.entity.card.page.CardPage;
import com.shouzan.back.entity.card.update.*;
import com.shouzan.back.mapper.*;
import com.shouzan.back.mapper.wechat.AdvancedInfoMapper;
import com.shouzan.back.mapper.wechat.BaseInfoAddMapper;
import com.shouzan.back.mapper.wechat.BaseInfoMapper;
import com.shouzan.back.mapper.wechat.CardMapper;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.biz.BaseBiz;
import com.shouzan.common.msg.ObjectRestResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2019/1/2 14:14
 * @Description:  微信公众号卡卷
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class WeChatCouponBizImpl extends BaseBiz<CardMapper, Card> implements WeChatCouponBiz {

    @Autowired
    private CardMapper cardMapper;

    @Autowired
    private BaseInfoMapper baseInfoMapper;

    @Autowired
    private BaseInfoAddMapper baseInfoAddMapper;

    @Autowired
    private AdvancedInfoMapper advancedInfoMapper;

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Autowired
    private MerchantsMapper merchantsMapper;

    @Autowired
    private BankMapper bankMapper;

    /**
     * @Description: (新增微信公众号卡卷)
     * @param cardBeanJson
     * @param userId
     * @[param] [cardBeanJson]
     * @return int
     * @author:  bin.yang
     * @date:  2019/1/3 10:58 AM
     */
    @Override
    public int addWeChetCouponInfo(CardBeanJson cardBeanJson, String userId) {
        Card card = cardBeanJson.getCard();
        if(card != null){
            int id = systemUserMapper.findIdByAccount(userId);
            card.setCreatorId(id);
            int i = cardMapper.addWeChetCouponInfo(card);
            if(i > 0){
                Integer cardId = card.getId();
                GeneralCoupon general_coupon = card.getGeneral_coupon();
                if(general_coupon != null){
                    BaseInfo baseInfo = card.getGeneral_coupon().getBase_info();
                    if(baseInfo != null){
                        baseInfo.setCardId(cardId);
                        int base = baseInfoMapper.addWeChetCouponBase(baseInfo);
                        if(baseInfo.getDate_info() != null){
                            int data = baseInfoAddMapper.addWeChetCouponData(baseInfo.getDate_info(),cardId);
                        }
                        if(baseInfo.getSku() != null){
                            int sku = baseInfoAddMapper.addWeChetCouponSku(baseInfo.getSku(),cardId);
                        }
                        if(baseInfo.getLocation_id_list() != null && baseInfo.getLocation_id_list().size() > 0){
                            int poi = baseInfoAddMapper.addWeChetCouponPoiId(baseInfo.getLocation_id_list(),cardId);
                        }
                    }
                    AdvancedInfo advancedInfo = card.getGeneral_coupon().getAdvanced_info();
                    if(advancedInfo != null){
                        if(advancedInfo.getUse_condition() != null){
                            int use = advancedInfoMapper.addWeChetCouponUse(advancedInfo.getUse_condition(),cardId);
                        }
                        if(advancedInfo.getAbstractTemp() != null){
                            int abs = advancedInfoMapper.addWeChetCouponAbs(advancedInfo.getAbstractTemp(),cardId);
                        }
                        if(advancedInfo.getAbstractTemp().getIcon_url_list() != null && advancedInfo.getAbstractTemp().getIcon_url_list().size() > 0){
                            int icon = advancedInfoMapper.addWeChetCouponAbsIcon(advancedInfo.getAbstractTemp().getIcon_url_list(),cardId);
                        }
                        if(advancedInfo.getText_image_list() != null && advancedInfo.getText_image_list().size() > 0){
                            int Timg = advancedInfoMapper.addWeChetCouponTextImg(advancedInfo.getText_image_list(),cardId);
                        }
                        if(advancedInfo.getTime_limit() != null && advancedInfo.getTime_limit().size() > 0){
                            int time = advancedInfoMapper.addWeChetCouponTime(advancedInfo.getTime_limit(),cardId);
                        }
                        if(advancedInfo.getBusiness_service() != null && advancedInfo.getBusiness_service().size() >0){
                            int bus = advancedInfoMapper.addWeChetCouponBus(advancedInfo.getBusiness_service(),cardId);
                        }
                    }
                }
                CardCoupon coupon = cardBeanJson.getCoupon();
                if(coupon != null){
                    coupon.setCouponStocks(Integer.parseInt(general_coupon.getBase_info().getSku().getQuantity()+""));
                    int mco = cardMapper.addOrUpdateMerCouponInfo(coupon,card.getId());
                }
                return card.getId();
            }else{
                return 0;
            }
        }else{
            return 0;
        }
    }

    /**
     * @Description: (微信公众号卡卷详情查询)
     * @param id
     * @[param] [id]
     * @return com.shouzan.back.entity.card.CardBeanJson
     * @author:  bin.yang
     * @date:  2019/1/3 1:05 PM
     */
    @Override
    public CardBeanJson findInfoWeChetCouById(int id) {
        Card card = cardMapper.findCardInfo(id);
        CardCoupon coupon = null;
        if(card != null){
            GeneralCoupon generalCoupon = cardMapper.findGeneralInfo(id);
            if(generalCoupon != null){
                BaseInfo baseInfo = baseInfoMapper.findBaseInfo(id);
                if(baseInfo != null){
                    DateInfo dateInfo = baseInfoAddMapper.findDataInfo(id);
                    Sku sku = baseInfoAddMapper.findSkuInfo(id);
                    List<Integer> list = baseInfoAddMapper.findPoiInfo(id);
                    baseInfo.setDate_info(dateInfo);
                    baseInfo.setSku(sku);
                    baseInfo.setLocation_id_list(list);
                }
                AdvancedInfo advancedInfo = new AdvancedInfo();
                UseCondition useCondition = advancedInfoMapper.findUseInfo(id);
                AbstractTemp abstractTemp = advancedInfoMapper.findAbsInfo(id);
                if(abstractTemp != null){
                    List<String> list = advancedInfoMapper.findIconInfo(id);
                    abstractTemp.setIcon_url_list(list);
                }
                List<TextImageList> textList = advancedInfoMapper.findTextImgInfo(id);
                List<TimeLimit> timeList = advancedInfoMapper.findTimeInfo(id);
                List<String> strList = advancedInfoMapper.findBusInfo(id);
                advancedInfo.setUse_condition(useCondition);
                advancedInfo.setAbstractTemp(abstractTemp);
                advancedInfo.setText_image_list(textList);
                advancedInfo.setTime_limit(timeList);
                advancedInfo.setBusiness_service(strList);
                generalCoupon.setBase_info(baseInfo);
                generalCoupon.setAdvanced_info(advancedInfo);
            }
            card.setGeneral_coupon(generalCoupon);
            coupon = cardMapper.findCouponInfo(id);
            if(coupon != null){
                if(coupon.getMerId() != null){
                    Merchants mer = merchantsMapper.findInfoById(coupon.getMerId());
                    coupon.setMerchants(mer);
                }
                if(coupon.getBankId() != null){
                    Bank bank = bankMapper.queryBankInfo(coupon.getBankId());
                    coupon.setBank(bank);
                }
            }
        }
        CardBeanJson cardBeanJson = new CardBeanJson();
        cardBeanJson.setCard(card);
        cardBeanJson.setCoupon(coupon);
        return cardBeanJson;
    }

    /**
     * @Description: (公众号卡卷信息修改)
     * @param beanJson
     * @param currentUserId
     * @[param] [beanJson]
     * @return int
     * @author:  bin.yang
     * @date:  2019/1/7 6:30 PM
     */
    @Override
    public int updateWcCardInfo(CardUp beanJson, String currentUserId) {
        int userId = systemUserMapper.findIdByAccount(currentUserId);
        String cardId = beanJson.getCard_id();
        int id = cardMapper.findIdByCardId(cardId);
        GeneralCouponUp general_coupon = beanJson.getGeneral_coupon();
        if(general_coupon != null){
            BaseInfoUp baseInfo = general_coupon.getBase_info();
            if(baseInfo != null){
                if(baseInfo.getDate_info() != null){
                    int dateId = baseInfoAddMapper.findDataInfoIdByCardId(id);
                    baseInfoAddMapper.updateWcCardInfoBaseType(baseInfo.getDate_info(),dateId);
                }
                if(baseInfo.getLocation_id_list() != null && baseInfo.getLocation_id_list().size() > 0){
                    baseInfoAddMapper.deleteWcCardPoiIdList(id);
                    baseInfoAddMapper.addWeChetCouponPoiId(baseInfo.getLocation_id_list(),id);
                }
                baseInfoMapper.updateWcCardInfoBase(baseInfo,id);
                cardMapper.updateWcCardInfoCard(userId,id,beanJson);
            }
        }
        CardCoupon coupon = beanJson.getCoupon();
        if(coupon != null){
            int mco = cardMapper.addOrUpdateMerCouponInfo(coupon,id);
        }
        return 1;
    }

    /**
     * @Description: (修改卡卷库存)
     * @param stock
     * @[param] [stock]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/1/8 11:41 AM
     */
    @Override
    public ObjectRestResponse updateWcCardStock(StockUp stock) {
        int id = cardMapper.findIdByCardId(stock.getCard_id());
        int skuId = baseInfoAddMapper.findSkuIdByCardId(id);
        int i = baseInfoAddMapper.updateWcCardInfoBaseSku(stock , skuId);
        if(i > 0 ){
            cardMapper.updateCouponStock(id,Integer.parseInt(baseInfoAddMapper.findSkuInfo(id).getQuantity()+""));
            return new ObjectRestResponse().rel(Response.SUCCESS).msg("库存修改成功");
        }else {
            return new ObjectRestResponse().rel(Response.FAILURE).msg("库存修改失败");
        }
    }

    @Override
    public int queryPageCount(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() +"%");
        }else{
            search.setKeywords(null);
        }
        return cardMapper.queryPageCount(search);
    }

    @Override
    public List<CardPage> queryPageList(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() +"%");
        }else{
            search.setKeywords(null);
        }
        List<CardPage> cardPages = cardMapper.queryPageList(search);
        for (CardPage cardPage : cardPages) {
            DateInfo dataInfo = baseInfoAddMapper.findDataInfo(cardPage.getId());
            cardPage.setDate_info(dataInfo);
        }
        return cardPages;
    }

    @Override
    public ObjectRestResponse deleteWeChetCoupon(String id) {
        int i = cardMapper.deleteWeChetCoupon(id);
        if(i > 0){
            return new ObjectRestResponse().rel(Response.SUCCESS).msg("卡卷删除成功");
        }else{
            return new ObjectRestResponse().rel(Response.FAILURE).msg("删除卡卷失败");
        }
    }

    @Override
    public ObjectRestResponse validWcChatCoupon(CardBeanJson beanJson) {
        DateInfo date_info = beanJson.getCard().getGeneral_coupon().getBase_info().getDate_info();
        if(date_info.getType().equals("DATE_TYPE_FIX_TERM")){
            if(date_info.getFixed_term() == null){
                return new ObjectRestResponse().rel(Response.FAILURE).msg("领取多少天内有效不能为空");
            }
            if(date_info.getFixed_term() <= 0){
                return new ObjectRestResponse().rel(Response.FAILURE).msg("领取X天内生效不支持填写0");
            }
            if(date_info.getFixed_begin_term() == null){
                return new ObjectRestResponse().rel(Response.FAILURE).msg("领取多少天后生效不能为空");
            }
        }else if(date_info.getType().equals("DATE_TYPE_FIX_TIME_RANGE")){
            if(date_info.getBegin_timestamp() == null){
                return new ObjectRestResponse().rel(Response.FAILURE).msg("起用时间不能为空");
            }
            if(date_info.getEnd_timestamp() == null){
                return new ObjectRestResponse().rel(Response.FAILURE).msg("结束时间不能为空");
            }
        }
        return new ObjectRestResponse().rel(Response.SUCCESS);
    }

    @Override
    public int insertWeChatCardId(String wcCardId, int id, String url) {
        baseInfoMapper.insertWeChatCenterUrl(url,id);
        return cardMapper.insertWeChatCardId(wcCardId,id);
    }

    @Override
    public int deleteWeChetCouponById(int id) {
        baseInfoMapper.deleteWeChetCouponByCardId(id);
        baseInfoAddMapper.deleteWeChetCouponByCardId(id);
        advancedInfoMapper.deleteWeChetCouponByCardId(id);
        return cardMapper.deleteWeChetCouponById(id);
    }

    @Override
    public ObjectRestResponse<CardUp> updateValidJsonData(CardUp beanJson) {
        beanJson.getGeneral_coupon().getBase_info().setCenter_url(null);
        DateInfoUp dateInfoUp = beanJson.getGeneral_coupon().getBase_info().getDate_info();
        Integer id = cardMapper.findIdByCardId(beanJson.getCard_id());
        DateInfo dataInfo = baseInfoAddMapper.findDataInfo(id);
        if(!dateInfoUp.getType().equals(dataInfo.getType())){
            return new ObjectRestResponse<CardUp>().rel(Response.FAILURE).msg("不可切换使用有效期时间类型");
        }
        if(dateInfoUp.getType().equals("DATE_TYPE_FIX_TIME_RANGE")){
            if(dateInfoUp.getBegin_timestamp() > dataInfo.getBegin_timestamp()
                    || dateInfoUp.getEnd_timestamp() < dataInfo.getEnd_timestamp()){
                return new ObjectRestResponse<CardUp>().rel(Response.FAILURE).msg("只能延长使用有效期");
            }
        }
        return new ObjectRestResponse<CardUp>().rel(Response.SUCCESS).result(beanJson);
    }
}
