package com.shouzan.back.biz.impl;

import com.shouzan.back.biz.OperateBiz;
import com.shouzan.back.constant.ConstantInfo;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.operate.Operate;
import com.shouzan.back.mapper.WhiteMapper;
import com.shouzan.back.mapper.operate.CodeBaseMapper;
import com.shouzan.back.mapper.operate.CodeListMapper;
import com.shouzan.back.mapper.operate.OperateMapper;
import com.shouzan.back.util.CodeValid;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.biz.BaseBiz;
import com.shouzan.common.msg.ObjectRestResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2019/1/21 15:44
 * @Description:  运营H5
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class OperateBizImpl extends BaseBiz<OperateMapper, Operate> implements OperateBiz {

    @Autowired
    private OperateMapper operateMapper;

    @Autowired
    private WhiteMapper whiteMapper;

    @Autowired
    private CodeBaseMapper codeBaseMapper;

    @Autowired
    private CodeListMapper codeListMapper;

    @Value("${operate_url}")
    private String OPERATE_URL;

    /**
     * @Description: (查询所有数据条数)
     * @param search
     * @[param] [search]
     * @return int
     * @author:  bin.yang
     * @date:  2019/1/21 5:10 PM
     */
    @Override
    public int queryPageCount(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() +"%");
        }else{
            search.setKeywords(null);
        }
        return operateMapper.queryPageCount(search);
    }

    /**
     * @Description: (查询所有数据集合)
     * @param search
     * @[param] [search]
     * @return java.util.List<com.shouzan.back.entity.Order>
     * @author:  bin.yang
     * @date:  2019/1/21 5:11 PM
     */
    @Override
    public List<Operate> queryPageList(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() +"%");
        }else{
            search.setKeywords(null);
        }
        return operateMapper.queryPageList(search);
    }

    /**
     * @Description: (新增运营页信息)
     * @param operate
     * @[param] [operate]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/1/21 5:11 PM
     */
    @Override
    public ObjectRestResponse addOperateInfo(Operate operate) {
        if(operate.getOperateType() == 0 || operate.getOperateType() == 2){
            ObjectRestResponse res = validationToDate(operate);
            if(!res.getRel()){
                return res;
            }
        }
        if(operate.getOperateType() == 3){
            return addCodeOperateInfo(operate);
        }
        Operate info = validationToStatus(operate);
        int i = operateMapper.addOperateInfo(info);
        if(i > 0){
            return insertOperateLinkUrl(info);
        }else {
            return new ObjectRestResponse().rel(Response.FAILURE).msg("添加活动失败");
        }
    }

    /**
     * @Description: (效验日期)
     * @param operate
     * @[param] [operate]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/4/17 12:53 PM
     */
    private ObjectRestResponse validationToDate(Operate operate) {
        if(operate.getCouponValidTimeEnd().getTime() < operate.getCouponValidTimeStart().getTime()
                || operate.getDownshelfTime().getTime() < operate.getUpshelfTime().getTime()){
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("截止时间不能早已开始时间");
        }else if(operate.getUpshelfTime().getTime() < operate.getCouponValidTimeStart().getTime()){
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("卡卷上架时间不能早于卡卷有效期时间");
        }else if(operate.getCouponValidTimeEnd().getTime() < operate.getDownshelfTime().getTime()){
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("卡卷下架时间不能晚于卡卷有效期时间");
        }else {
            return new ObjectRestResponse().rel(Response.SUCCESS);
        }
    }

    /**
     * @Description: (生成外链)
     * @param info
     * @[param] [info]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/4/17 12:49 PM
     */
    private ObjectRestResponse insertOperateLinkUrl(Operate info) {
        if(info.getOperateType() == 0){
            operateMapper.setOperateLinkUrl(info.getId(),OPERATE_URL+info.getId());
        }else if(info.getOperateType() == 1){
            operateMapper.setOperateLinkUrl(info.getId(),OPERATE_URL+info.getId()+ConstantInfo.OPERATE_URL_RECH);
        }else if(info.getOperateType() == 2){
            operateMapper.setOperateLinkUrl(info.getId(),OPERATE_URL+info.getId()+ConstantInfo.OPERATE_URL_PAY);
        }else if(info.getOperateType() == 3){
            operateMapper.setOperateLinkUrl(info.getId(),OPERATE_URL+info.getId()+ConstantInfo.OPERATE_URL_CODE);
        }
        return new ObjectRestResponse().rel(Response.SUCCESS).msg("添加活动成功");
    }

    /**
     * @Description: (添加串码活动卡卷)
     * @param
     * @[param] []
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/4/17 12:55 PM
     */
    private ObjectRestResponse addCodeOperateInfo(Operate operate) {
        int count = codeListMapper.findCodeCountByBaseId(operate.getCodeBaseId());
        operate.setCardStocks(count);
        Operate info = validationToStatus(operate);
        int i = operateMapper.addOperateInfo(info);
        if(i > 0){
            return insertOperateLinkUrl(info);
        }else {
            return new ObjectRestResponse().rel(Response.FAILURE).msg("添加活动失败");
        }
    }

    /**
     * @Description: (修改卡卷状态)
     * @param id
     * @param status
     * @[param] [id, status]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/1/21 5:11 PM
     */
    @Override
    public ObjectRestResponse updateCardStatus(String id, Integer status) {
        if(status  == 0 || status == 2){
            Date date = new Date();
            List<Operate> operates = operateMapper.selectInfoByStrId(id);
            String str = "";
            int count = 0 ;
            if(status == 0){
                for (Operate operate : operates) {
                    if(operate.getUpshelfTime().getTime() > date.getTime() || operate.getDownshelfTime().getTime() < date.getTime()){
                        count++;
                        str+=operate.getCardName()+",";
                    }
                }
                if(count > 0 ){
                    return new ObjectRestResponse().rel(Response.FAILURE).msg("上架失败 . "+str+" 不在上架有效期");
                }
                for (Operate operate : operates) {
                    if(operate.getCardStocks() <= 0){
                        count++;
                        str+=operate.getCardName()+",";
                    }
                }
                if(count > 0){
                    return new ObjectRestResponse().rel(Response.FAILURE).msg("上架失败 . "+str+" 库存不足");
                }
            }
            int i = operateMapper.updateCardStatus(id,status);
            return CodeValid.CodeMsg(i,"状态修改");
        }else {
            return new ObjectRestResponse().rel(Response.FAILURE).msg("卡卷 状态 信息错误");
        }
    }

    /**
     * @Description: (修改基础信息)
     * @param operate
     * @[param] [operate]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/1/21 5:11 PM
     */
    @Override
    public ObjectRestResponse updateOperateCardInfo(Operate operate) {
        if(operate.getOperateType() == 0 || operate.getOperateType() == 2){
            ObjectRestResponse res = validationToDate(operate);
            if(!res.getRel()){
                return res;
            }
        }
        if(operate.getOperateType() == 3){
            return updateCodeOperateInfo(operate);
        }
        Operate info = validationToStatus(operate);
        int i = operateMapper.updateOperateCardInfo(info);
        return CodeValid.CodeMsg(i , "修改活动基础信息");
    }

    /**
     * @Description: (修改串码卡卷信息)
     * @param operate
     * @[param] [operate]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/4/17 2:09 PM
     */
    private ObjectRestResponse updateCodeOperateInfo(Operate operate) {
        int i = 0;
        Operate ope = operateMapper.findInfoById(operate.getId());
        if(operate.getCodeBaseId() != ope.getCodeBaseId()){
            //修改串码库
            if(operate.getCardState() == 0){
                return new ObjectRestResponse().rel(Response.FAILURE).msg("上架期间不允许更改串码库 , 请下架处理 !");
            }else {
                int count = codeListMapper.findCodeCountByBaseId(operate.getCodeBaseId());
                if(count > 0){
                    operate.setCardStocks(count);
                    Operate info = validationToStatus(operate);
                    i = operateMapper.updateOperateCardInfo(info);
                }else {
                    i = operateMapper.updateOperateCardInfo(operate);
                }
                operateMapper.resetOperateCardStock(operate.getId(), count);
            }
        }else {
            //未修改串码库
            if(operate.getCardState() == 1){
                i = operateMapper.updateOperateCardInfo(operate);
            }else{
                int count = codeListMapper.findCodeCountByBaseId(operate.getCodeBaseId());
                if (count > 0){
                    operate.setCardStocks(ope.getCardStocks());
                    Operate info = validationToStatus(operate);
                    i = operateMapper.updateOperateCardInfo(info);
                }else {
                    i = operateMapper.updateOperateCardInfo(operate);
                }
            }
        }
        return CodeValid.CodeMsg(i , "修改活动基础信息");
    }

    /**
     * @Description: (通过ID查询详细信息)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/1/21 5:12 PM
     */
    @Override
    public Operate findInfoById(Integer id) {
        Operate operate = operateMapper.findInfoById(id);
        if(operate.getOperateType() == 2){
            operate.setWhiteBaseName(whiteMapper.findInfoById(operate.getWhiteBaseId()).getBaseName());
        }else if(operate.getOperateType() == 3){
            operate.setCodeBaseName(codeBaseMapper.findInfoById(operate.getCodeBaseId()).getBaseName());
        }
        return operate;
    }

    /**
     * @Description: (删除指定ID信息)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/1/21 5:12 PM
     */
    @Override
    public ObjectRestResponse deleteInfoById(Integer id) {
        int i = operateMapper.deleteInfoById(id);
        return CodeValid.CodeMsg(i , "删除");
    }

    /**
     * @Auther: bin.yang
     * @Date: 2019/1/22 12:57 PM
     *
     * @Description:   根据时间设置状态
     */
    public Operate validationToStatus(Operate operate) {
        Date date = new Date();
        if(operate.getUpshelfTime().getTime() < date.getTime() && operate.getDownshelfTime().getTime() > date.getTime()){
            if(operate.getCardStocks() != null && operate.getCardStocks() > 0){
                operate.setCardState(Byte.parseByte("0"));
            }else {
                operate.setCardState(Byte.parseByte("2"));
            }
        }else {
            operate.setCardState(Byte.parseByte("2"));
        }
        return operate;
    }

    /**
     * @Description: (新增修改时数据效验)
     * @param operate
     * @[param] [operate]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/2/28 11:05 AM
     */
    @Override
    public ObjectRestResponse validationData(Operate operate) {
        ObjectRestResponse response = new ObjectRestResponse<>();
        if(operate.getOperateType() == 1){
            if(operate.getTelephoneBill() == null || operate.getTelephoneBill() < 0){
                return response.rel(Response.FAILURE).msg("请填写话费金额");
            }
            if(operate.getReduceTag() == null){
                return response.rel(Response.FAILURE).msg("请填写立减标识");
            }
        }else if(operate.getOperateType() == 0){
            if(operate.getWechatBatches() == null){
                return response.rel(Response.FAILURE).msg("请填写批次号");
            }
            if(operate.getReduceTag() == null){
                return response.rel(Response.FAILURE).msg("请填写立减标识");
            }
        }else if(operate.getOperateType() == 0){
            if(operate.getCouponValidTimeStart() == null || operate.getCouponValidTimeEnd() == null){
                return response.rel(Response.FAILURE).msg("请设置活动有效期.");
            }
        }else if(operate.getOperateType() == 2){
            if(operate.getWhiteBaseId() == null){
                return response.rel(Response.FAILURE).msg("请选择白名单库");
            }
            if(operate.getTelLimitCount() == null || operate.getTelLimitCount() < 1){
                return response.rel(Response.FAILURE).msg("请填写单手机号可参与次数");
            }
            if(operate.getWechatBatches() == null){
                return response.rel(Response.FAILURE).msg("请填写批次号");
            }
            if(operate.getIsAchievements() == 0){
                return response.rel(Response.FAILURE).msg("免支付卡卷无法参加绩效返利活动");
            }
        }else if(operate.getOperateType() == 3){
            if(operate.getCodeBaseId() == null){
                return response.rel(Response.FAILURE).msg("请选择串码库");
            }
            if(operate.getReduceTag() == null){
                return response.rel(Response.FAILURE).msg("请填写立减标识");
            }
        }else {
            return response.rel(Response.FAILURE).msg("无效的运营类型");
        }
        if(operate.getIsAchievements() == 0){
           if(operate.getRebateMoney() == null || operate.getRebateMoney().compareTo(BigDecimal.ZERO) < 0){
               return response.rel(Response.FAILURE).msg("请填写绩效返利金额");
           }
        }
        return response.rel(Response.SUCCESS);
    }
}
