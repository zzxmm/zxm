package com.shouzan.back.biz;

import com.shouzan.back.entity.User;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/9/12 13:01
 * @Description:  用户管理
 */
public interface UserBiz {

    ObjectRestResponse<User> updateUserStatus(String id, Integer status);

    int queryUserCount(SearchSatisfy searchSatisfy);

    List<User> queryUserList(SearchSatisfy searchSatisfy);

    User findInfoById(int id);
}
