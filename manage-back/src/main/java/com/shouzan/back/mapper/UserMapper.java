package com.shouzan.back.mapper;

import com.shouzan.back.entity.User;
import com.shouzan.back.rpc.union.UserList;
import com.shouzan.back.vo.SearchSatisfy;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
/**
 * @Author: bin.yang
 * @Date: 2019/1/23 5:20 PM
 *
 * @Description:   用户
 */
public interface UserMapper extends Mapper<User> {

    int updateUserStatus(@Param("id") String id,@Param("status")  Integer status);

    int queryUserCount(SearchSatisfy searchSatisfy);

    List<User> queryUserList(SearchSatisfy searchSatisfy);

    User findInfoById(int id);

    int findUserStatusById(Integer id);

    List<UserList> findUserOpenID();

    List<UserList> findUucUserOpenID();
}