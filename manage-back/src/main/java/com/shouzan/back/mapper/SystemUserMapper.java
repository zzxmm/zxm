package com.shouzan.back.mapper;

import com.shouzan.back.entity.SystemUser;
import com.shouzan.back.vo.SearchSatisfy;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
/**
 * @Author: bin.yang
 * @Date: 2019/1/23 5:20 PM
 *
 * @Description:  系统用户
 */
public interface SystemUserMapper extends Mapper<SystemUser> {

    int queryPageCount(SearchSatisfy searchSatisfy);

    List<SystemUser> queryPageList(SearchSatisfy searchSatisfy);

    int updateSystemUserInfo(SystemUser systemUser);

    int deleteSystemUserInfo(@Param("id") String id);

    int addSystemUserInfo(SystemUser systemUser);

    SystemUser selectSystemUserById(Integer id);

    int selectUserInfoByCenterId(@Param("id") String id);

    SystemUser findUserByCenterId(Integer id);

    int findIdByAccount(@Param("accId") String accId);
}