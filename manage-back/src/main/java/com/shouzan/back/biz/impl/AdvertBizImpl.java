package com.shouzan.back.biz.impl;

import com.shouzan.back.biz.AdvertBiz;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.Advert;
import com.shouzan.back.entity.Bank;
import com.shouzan.back.mapper.AdvertMapper;
import com.shouzan.back.mapper.BankMapper;
import com.shouzan.back.mapper.SystemUserMapper;
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
 * @Date: 2018/9/18 17:54
 * @Description:  广告
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class AdvertBizImpl extends BaseBiz<AdvertMapper, Advert> implements AdvertBiz {

    @Autowired
    private AdvertMapper advertMapper;

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Autowired
    private BankMapper bankMapper;

    /**
     * @Description: (查询总条数)
     * @param search
     * @[param] [search]
     * @return int
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:38
     */
    @Override
    public int queryPageCount(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() +"%");
        }else{
            search.setKeywords(null);
        }
        return advertMapper.queryPageCount(search);
    }

    /**
     * @Description: (查询列表集合)
     * @param search
     * @[param] [search]
     * @return java.util.List<com.shouzan.back.entity.Advert>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:39
     */
    @Override
    public List<Advert> queryPageList(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() +"%");
        }else{
            search.setKeywords(null);
        }
        return advertMapper.queryPageList(search);
    }

    /**
     * @Description: (通过ID查询详细信息)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Advert>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:40
     */
    @Override
    public ObjectRestResponse<Advert> queryAdvertById(int id) {
        Advert advert = advertMapper.queryAdvertById(id);
        if(advert != null){
            if(advert.getBankId() != null){
                Bank bank = bankMapper.queryBankInfo(advert.getBankId());
                advert.setBank(bank);
            }
        }
        return new ObjectRestResponse<Advert>().result(advert).rel(Response.SUCCESS);
    }

    /**
     * @Description: (修改状态)
     * @param id
     * @param status
     * @[param] [id, status]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Advert>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:40
     */
    @Override
    public ObjectRestResponse<Advert> updateAdvertStatus(String id, Integer status) {
        ObjectRestResponse<Advert> response = new ObjectRestResponse<>();
        String[] split = id.split(",");
        Date date = new Date();
        boolean flag = true;
        if (status == 0){
            for (int i = 0; i < split.length; i++) {
                Advert advert = advertMapper.queryAdvertById(Integer.parseInt(split[i]));
                if(date.getTime() < advert.getUpshelfTime().getTime()){
                    response.msg(advert.getAdvertName()+" , 广告未到上架时间");
                    response.rel(Response.FAILURE);
                    flag=false;
                    break;
                }
                if(date.getTime() > advert.getDownshelfTime().getTime()){
                    response.msg(advert.getAdvertName()+" , 广告已过期");
                    response.rel(Response.FAILURE);
                    flag=false;
                    break;
                }
                if(advert.getBankId() != null){
                    Bank bank = bankMapper.queryBankInfo(advert.getBankId());
                    if(bank.getEnableState() == 0){
                        response.msg(advert.getAdvertName()+" , 所属银行已被停用");
                        response.rel(Response.FAILURE);
                        flag=false;
                        break;
                    }
                }
            }
            if(flag){
                return CodeValid.CodeMsg(advertMapper.updateAdvertStatus(id,status),"广告上架");
            }else{
                return response;
            }
        }else{
            for (int i = 0; i < split.length; i++) {
                Advert advert = advertMapper.queryAdvertById(Integer.parseInt(split[i]));
                if(date.getTime() > advert.getDownshelfTime().getTime()){
                    response.msg(advert.getAdvertName()+" , 广告已过期");
                    response.rel(Response.FAILURE);
                    flag=false;
                    break;
                }
            }
            if(flag){
                return CodeValid.CodeMsg(advertMapper.updateAdvertStatus(id,status),"广告下架");
            }else{
                return response;
            }
        }
    }

    /**
     * @Description: (修改广告信息)
     * @param entity
     * @[param] [entity]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Advert>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:40
     */
    @Override
    public ObjectRestResponse<Advert> updateAdvertInfoById(Advert entity) {
        if(entity.getDownshelfTime().getTime() < entity.getUpshelfTime().getTime()){
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("广告开始时间不能晚于广告结束时间");
        }
        if(entity.getAdvertState() == 0){
            Date date = new Date();
            if(date.getTime() < entity.getUpshelfTime().getTime()){
                return new ObjectRestResponse<>().rel(Response.FAILURE).msg("广告未到上架时间 , 更换状态");
            }
        }
        if(entity.getAdvertPosition().indexOf("1") >= 0 || entity.getAdvertPosition().indexOf("2") >= 0){
            if(entity.getBankId() == null){
                return new ObjectRestResponse<>().rel(Response.FAILURE).msg("请选择银行");
            }
        }else {
            entity.setBankId(0);
        }
        return CodeValid.CodeMsg(advertMapper.updateAdvertInfoById(entity),"修改广告信息");
    }

    /**
     * @Description: (新增广告信息)
     * @param entity
     * @[param] [entity]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Advert>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:40
     */
    @Override
    public ObjectRestResponse<Advert> insertAdvertInfo(Advert entity) {
        if(entity.getDownshelfTime().getTime() < entity.getUpshelfTime().getTime()){
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("广告开始时间不能晚于广告结束时间");
        }
        if(entity.getAdvertState() == 0){
            Date date = new Date();
            if(date.getTime() < entity.getUpshelfTime().getTime()){
                return new ObjectRestResponse<>().rel(Response.FAILURE).msg("广告未到上架时间 , 更换状态");
            }
        }
        if(entity.getAdvertPosition().indexOf("1") >= 0 || entity.getAdvertPosition().indexOf("2") >= 0){
            if(entity.getBankId() == null){
                return new ObjectRestResponse<>().rel(Response.FAILURE).msg("请选择银行");
            }
        }else {
            entity.setBankId(null);
        }
        return CodeValid.CodeMsg(advertMapper.insertAdvertInfo(entity),"添加广告");
    }

    @Override
    public ObjectRestResponse importExcelAdvertInfo(List<Advert> list, String userId) {
        int id = systemUserMapper.findIdByAccount(userId);
        int i = advertMapper.batchInsertAdvertInfo(list,id);
        if(i > 0){
            return new ObjectRestResponse().rel(Response.SUCCESS).msg(" 导入成功 , 成功导入 "+i+" 条数据 . ");
        }else{
            return new ObjectRestResponse().rel(Response.FAILURE).msg(" 导入失败 , 请刷新重新导入 ! ");
        }
    }
}
