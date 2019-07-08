package com.shouzan.back.mapper.operate;

import com.shouzan.back.entity.operate.CodeRecord;
import com.shouzan.back.vo.SearchSatisfy;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2019/4/11 12:04 PM
 *
 * @Description:  串码记录
 */
public interface CodeRecordMapper {

    int queryPageCount(SearchSatisfy search);

    List<CodeRecord> queryPageList(SearchSatisfy search);

    CodeRecord findCardRecordById(int id);

    int updateCodeStatus(Integer id);
}