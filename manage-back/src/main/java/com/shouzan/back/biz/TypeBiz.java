package com.shouzan.back.biz;

import com.shouzan.back.entity.Types;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/9/19 17:51
 * @Description:  类型管理
 */
public interface TypeBiz {

    int queryPageCount(SearchSatisfy search);

    List<Types> queryPageList(SearchSatisfy search);

    ObjectRestResponse<Types> updateTypeStatus(String id, Integer status);

    ObjectRestResponse<Types> selectTypeInfoById(int id);

    ObjectRestResponse<Types> insertTypeInfo(Types entity);

    ObjectRestResponse<Types> updateTypeInfo(Types entity);

    ObjectRestResponse<Types> selectTypeListAll();

    int selectIdByName(String name);
}
