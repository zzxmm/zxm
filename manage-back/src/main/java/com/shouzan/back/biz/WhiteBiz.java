package com.shouzan.back.biz;

import com.shouzan.back.entity.white.WhiteBase;
import com.shouzan.back.entity.white.WhiteUser;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2019/3/28 12:52
 * @Description:
 */
public interface WhiteBiz {

    int queryPageCount(SearchSatisfy search);

    List<WhiteUser> queryPageList(SearchSatisfy search);

    ObjectRestResponse createWhiteBase(WhiteBase entity);

    ObjectRestResponse updateWhiteBase(WhiteBase entity);
}
