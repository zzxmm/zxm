package com.shouzan.back.mapper.extend;

import com.shouzan.back.entity.extend.ExtendRecord;
import com.shouzan.back.vo.SearchSatisfy;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2019/2/18 6:24 PM
 *
 * @Description:  分销 记录
 */
public interface ExtendRecordMapper {

    int queryPageRecordCount(SearchSatisfy search);

    List<ExtendRecord> queryPageRecordList(SearchSatisfy search);
}