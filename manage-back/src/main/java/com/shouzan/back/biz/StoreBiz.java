package com.shouzan.back.biz;

import com.shouzan.back.entity.Store;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/9/14 16:23
 * @Description:  门店管理
 */
public interface StoreBiz {

    ObjectRestResponse<Store> selectStoreList(int id);

    int queryPageCount(SearchSatisfy searchSatisfy);

    List<Store> queryPageList(SearchSatisfy searchSatisfy);

    ObjectRestResponse<Store> updateStatus(String id, Integer status);

    ObjectRestResponse<Store> findInfo(int id);

    ObjectRestResponse<Store> addInfo(Store entity);

    ObjectRestResponse<Store> updateInfo(Store entity);

    ObjectRestResponse<Store> deleteStoreById(int id);

    Store findInfoByCenterId(int id);

    String findCenterIDById(String id);

    Store findInfoById(int id);

    ObjectRestResponse storeIdListAll(int id, String region);
}
