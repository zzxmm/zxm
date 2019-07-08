package com.shouzan.back.biz;

import com.shouzan.back.entity.SystemUser;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/10/11 20:59
 * @Description:  系统用户
 */
public interface SystemUserBiz {

    int queryPageCount(SearchSatisfy searchSatisfy);

    List<SystemUser> queryPageList(SearchSatisfy searchSatisfy);

    ObjectRestResponse<SystemUser> updateSystemUserInfo(SystemUser systemUser);

    ObjectRestResponse<SystemUser> deleteSystemUserInfo(String id);

    ObjectRestResponse<SystemUser> addSystemUserInfo(SystemUser systemUser);

    ObjectRestResponse<SystemUser> selectSystemUserById(int id);

    int selectUserInfoByCenterId(String id);
}
