package com.shouzan.back.mapper.operate;

import com.shouzan.back.entity.operate.CodeBase;
import com.shouzan.back.entity.operate.Operate;
import com.shouzan.back.vo.SearchSatisfy;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2019/4/10 12:35 PM
 *
 * @Description:  串码库
 */
public interface CodeBaseMapper {

    int queryPageCount(SearchSatisfy search);

    List<CodeBase> queryPageList(SearchSatisfy search);

    int createCodeBase(CodeBase entity);

    int updateCodeBase(CodeBase entity);

    int deleteCodeBase(Integer id);

    CodeBase findInfoById(Integer id);

    String findInIdByOperate(SearchSatisfy search);
}