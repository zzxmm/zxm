package com.shouzan.back.biz.impl;


import com.shouzan.back.biz.IntegralRecordBiz;
import com.shouzan.back.entity.integral.IntegralRecord;
import com.shouzan.back.mapper.integral.IntegralRecordMapper;
import com.shouzan.back.vo.SearchSatisfy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: man.z
 * @Date: 2019-05-28 10:14
 *
 * @Description: 积分记录
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class IntegralBizImpl implements IntegralRecordBiz {
    @Autowired
    private IntegralRecordMapper integralRecordMapper;

    /**
     * @Description: 会员积分总条数

     * @[param] [search]
     * @return int
     * @author:  man.z
     * @date:  2019-05-28 16:12
     */
    @Override
    public int queryPageCount(SearchSatisfy search) {
       if(StringUtils.isNotBlank(search.getKeywords())){
           search.setKeywords("%" + search.getKeywords().trim() + "%");
       }else {
           search.setKeywords(null);
       }
       return integralRecordMapper.queryPageCount(search);
    }

    /**
     * @Description: 会员积分列表

     * @[param] [search]
     * @return java.util.List<com.shouzan.back.entity.integral.IntegralRecord>
     * @author:  man.z
     * @date:  2019-05-28 16:12
     */
    @Override
    public List<IntegralRecord> queryPageList(SearchSatisfy search) {
        if(StringUtils.isNoneBlank(search.getKeywords())){
            search.setKeywords("%"+ search.getKeywords()+ "%");
        }else {
            search.setKeywords(null);
        }
        return integralRecordMapper.queryPageList(search);
    }

    /**
     * @Description: 用户积分总条数

     * @[param] [search]
     * @return int
     * @author:  man.z
     * @date:  2019-05-28 16:13
     */
    @Override
    public int queryPageIntegralCount(SearchSatisfy search) {
        return integralRecordMapper.queryIntegralPageCount(search);
    }

    /**
     * @Description: 用户积分列表

     * @[param] [search]
     * @return java.util.List<com.shouzan.back.entity.integral.IntegralRecord>
     * @author:  man.z
     * @date:  2019-05-28 16:13
     */
    @Override
    public List<IntegralRecord> queryPageIntegralList(SearchSatisfy search) {
        return integralRecordMapper.queruIntegralPageList(search);
    }


}
