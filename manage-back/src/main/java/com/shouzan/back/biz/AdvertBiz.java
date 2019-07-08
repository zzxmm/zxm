package com.shouzan.back.biz;

import com.shouzan.back.entity.Advert;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/9/18 17:53
 * @Description:  广告管理
 */
public interface AdvertBiz {

    int queryPageCount(SearchSatisfy search);

    List<Advert> queryPageList(SearchSatisfy search);

    ObjectRestResponse<Advert> queryAdvertById(int id);

    ObjectRestResponse<Advert> updateAdvertStatus(String id, Integer status);

    ObjectRestResponse<Advert> updateAdvertInfoById(Advert entity);

    ObjectRestResponse<Advert> insertAdvertInfo(Advert entity);

    ObjectRestResponse importExcelAdvertInfo(List<Advert> rows, String userId);
}
