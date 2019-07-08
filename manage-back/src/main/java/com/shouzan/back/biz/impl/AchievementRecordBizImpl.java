package com.shouzan.back.biz.impl;

import com.shouzan.back.biz.AchievementRecordBiz;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.achievement.AchievementsExchange;
import com.shouzan.back.entity.achievement.AchievementsRecord;
import com.shouzan.back.mapper.achievement.AchievementsExchangeMapper;
import com.shouzan.back.mapper.achievement.AchievementsRecordMapper;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: com.shouzan.back.biz.impl.AchievementRecordBizImpl
 * @Author: shouzan
 * @Date: 2019-06-28 15:55
 * @Description: TODO
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class AchievementRecordBizImpl implements AchievementRecordBiz {

    @Autowired
    private AchievementsRecordMapper achievementsRecordMapper;

    @Autowired
    private AchievementsExchangeMapper achievementsExchangeMapper;

    /***
     * @Description: 查询数据总条数

     * @[param] [search]
     * @return int
     * @author:  man.z
     * @date:  2019-06-28 16:03
     */
    @Override
    public int queryPageCount(SearchSatisfy search) {

        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() +"%");
        }else{
            search.setKeywords(null);
        }
        return achievementsRecordMapper.queryPageCount(search);
    }

    /***
     * @Description: 查询数据集合

     * @[param] [search]
     * @return java.util.List<com.shouzan.back.entity.achievement.AchievementsRecord>
     * @author:  man.z
     * @date:  2019-06-28 16:04
     */
    @Override
    public List<AchievementsRecord> queryPageList(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() +"%");
        }else{
            search.setKeywords(null);
        }
        return achievementsRecordMapper.queryPageList(search);
    }

    @Override
    public int queryPageBonusCount(SearchSatisfy search) {
        return achievementsExchangeMapper.queryPageBonusCount(search);
    }

    @Override
    public List<AchievementsExchange> queryPageBonusList(SearchSatisfy search) {
        return achievementsExchangeMapper.queryPageBonusList(search);
    }

    @Override
    public ObjectRestResponse findInfoBonusById(int id) {
        AchievementsExchange ac = achievementsExchangeMapper.findInfoBonusById(id);
        return new ObjectRestResponse().rel(Response.SUCCESS).result(ac);
    }

    @Override
    public ObjectRestResponse updateBonusStatus(Integer id, Integer status, String notes, Integer userId) {
        int i = achievementsExchangeMapper.updateBonusStatus(id, status, notes, userId);
        if(i>0){
            return new ObjectRestResponse().rel(Response.SUCCESS).msg("绩效提现操作成功");
        }else {
            return new ObjectRestResponse().rel(Response.FAILURE).msg("绩效提现操作失败");

        }
    }
}
