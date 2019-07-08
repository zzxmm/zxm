package com.shouzan.back.mapper.extend;

import com.shouzan.back.entity.extend.ExtendExchange;
import com.shouzan.back.entity.extend.ExtendRecord;
import com.shouzan.back.vo.SearchSatisfy;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2019/2/18 6:24 PM
 *
 * @Description:  分销-佣金提现
 */
public interface ExtendExchangeMapper {

    int queryPageBonusCount(SearchSatisfy search);

    List<ExtendExchange> queryPageBonusList(SearchSatisfy search);

    ExtendExchange findInfoBonusById(int id);

    List<ExtendRecord> selectBonusRecordList(int id);

    int updateBonusStatus(@Param("id") Integer id, @Param("status") Integer status, @Param("notes") String notes,@Param("userId") Integer userId);
}