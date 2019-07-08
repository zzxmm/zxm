package com.shouzan.back.biz;

import com.shouzan.back.entity.Merchants;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/9/14 15:37
 * @Description:  商户管理
 */
public interface MerBiz {

    ObjectRestResponse<Merchants> selectMerList();

    int queryPageCount(SearchSatisfy searchSatisfy);

    List<Merchants> queryPageList(SearchSatisfy searchSatisfy);

    ObjectRestResponse<Merchants> selectMerInfoById(int id);

    ObjectRestResponse<Merchants> updateMerchantsStatus(String id, Integer status);

    ObjectRestResponse<Merchants> insertMerchantsInFo(Merchants entity, String typeId);

    ObjectRestResponse<Merchants> updateMerchantsInFo(Merchants entity, String typeId);

    int selectMerInfoByCenterId(String s);
}

