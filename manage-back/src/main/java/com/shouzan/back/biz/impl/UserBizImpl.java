package com.shouzan.back.biz.impl;

import com.shouzan.back.biz.UserBiz;
import com.shouzan.back.entity.User;
import com.shouzan.back.mapper.UserMapper;
import com.shouzan.back.rpc.union.UserList;
import com.shouzan.back.util.CodeValid;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.biz.BaseBiz;
import com.shouzan.common.msg.ObjectRestResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/9/12 13:01
 * @Description:  用户管理
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserBizImpl extends BaseBiz<UserMapper, User> implements UserBiz {

    @Autowired
    private UserMapper userMapper;

    /**
     * @Description: (修改用户状态)
     * @param id
     * @param status
     * @[param] [id, status]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.User>
     * @author:  bin.yang
     * @date:  2018/11/21 11:19 AM
     */
    @Override
    public ObjectRestResponse<User> updateUserStatus(String id, Integer status) {
        return CodeValid.CodeMsg(userMapper.updateUserStatus(id,status),"修改用户状态");
    }

    /**
     * @Description: (查询总条数)
     * @param searchSatisfy
     * @[param] [searchSatisfy]
     * @return int
     * @author:  bin.yang
     * @date:  2018/11/21 11:19 AM
     */
    @Override
    public int queryUserCount(SearchSatisfy searchSatisfy) {
        if(StringUtils.isNotBlank(searchSatisfy.getKeywords())){
            searchSatisfy.setKeywords("%" + searchSatisfy.getKeywords().trim() + "%");
        }else{
            searchSatisfy.setKeywords(null);
        }
        return userMapper.queryUserCount(searchSatisfy);
    }

    /**
     * @Description: (分页查询用户信息)
     * @param searchSatisfy
     * @[param] [searchSatisfy]
     * @return java.util.List<com.shouzan.back.entity.User>
     * @author:  bin.yang
     * @date:  2018/11/21 11:19 AM
     */
    @Override
    public List<User> queryUserList(SearchSatisfy searchSatisfy) {
        if(StringUtils.isNotBlank(searchSatisfy.getKeywords())){
            searchSatisfy.setKeywords("%" + searchSatisfy.getKeywords().trim() + "%");
        }else{
            searchSatisfy.setKeywords(null);
        }
        return userMapper.queryUserList(searchSatisfy);
    }

    /**
     * @Description: (通过ID查询详细信息)
     * @param id
     * @[param] [id]
     * @return com.shouzan.back.entity.User
     * @author:  bin.yang
     * @date:  2018/11/21 11:20 AM
     */
    @Override
    public User findInfoById(int id) {
        return userMapper.findInfoById(id);
    }

    public List<UserList> findUserOpenID() {
        return userMapper.findUserOpenID();
    }
}
