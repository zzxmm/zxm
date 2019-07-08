package com.shouzan.back.biz.impl;

import com.shouzan.back.biz.SystemUserBiz;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.SystemUser;
import com.shouzan.back.mapper.SystemUserMapper;
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
 * @Date: 2018/10/11 20:59
 * @Description:   系统用户
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class SystemUserBizImpl extends BaseBiz<SystemUserMapper, SystemUser> implements SystemUserBiz {

    @Autowired
    private SystemUserMapper systemUserMapper;

    /**
     * @Description: (查询总条数)
     * @param searchSatisfy
     * @[param] [searchSatisfy]
     * @return int
     * @author:  bin.yang
     * @date:  2018/10/11 下午9:32
     */
    @Override
    public int queryPageCount(SearchSatisfy searchSatisfy) {
        if (StringUtils.isNotBlank(searchSatisfy.getKeywords())){
            searchSatisfy.setKeywords("%" + searchSatisfy.getKeywords().trim() +"%");
        }else{
            searchSatisfy.setKeywords(null);
        }
        return systemUserMapper.queryPageCount(searchSatisfy);
    }

    /**
     * @Description: (查询数据集合)
     * @param searchSatisfy
     * @[param] [searchSatisfy]
     * @return java.util.List<com.shouzan.back.entity.SystemUser>
     * @author:  bin.yang
     * @date:  2018/10/11 下午9:32
     */
    @Override
    public List<SystemUser> queryPageList(SearchSatisfy searchSatisfy) {
        if (StringUtils.isNotBlank(searchSatisfy.getKeywords())){
            searchSatisfy.setKeywords("%" + searchSatisfy.getKeywords().trim() +"%");
        }else{
            searchSatisfy.setKeywords(null);
        }
        return systemUserMapper.queryPageList(searchSatisfy);
    }

    /**
     * @Description: (修改用户数据)
     * @param systemUser
     * @[param] [systemUser]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.SystemUser>
     * @author:  bin.yang
     * @date:  2018/10/11 下午9:33
     */
    @Override
    public ObjectRestResponse<SystemUser> updateSystemUserInfo(SystemUser systemUser) {
        return CodeValid.CodeMsg(systemUserMapper.updateSystemUserInfo(systemUser),"修改");
    }

    /**
     * @Description: (删除用户信息)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.SystemUser>
     * @author:  bin.yang
     * @date:  2018/10/11 下午9:33
     */
    @Override
    public ObjectRestResponse<SystemUser> deleteSystemUserInfo(String id) {
        return CodeValid.CodeMsg(systemUserMapper.deleteSystemUserInfo(id),"删除");
    }

    /**
     * @Description: (新增用户信息)
     * @param systemUser
     * @[param] [systemUser]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.SystemUser>
     * @author:  bin.yang
     * @date:  2018/10/11 下午9:33
     */
    @Override
    public ObjectRestResponse<SystemUser> addSystemUserInfo(SystemUser systemUser) {
        return CodeValid.CodeMsg(systemUserMapper.addSystemUserInfo(systemUser),"新增");
    }

    /**
     * @Description: (修改回显)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.SystemUser>
     * @author:  bin.yang
     * @date:  2018/10/11 下午10:07
     */
    @Override
    public ObjectRestResponse<SystemUser> selectSystemUserById(int id) {
        return new ObjectRestResponse<SystemUser>().rel(Response.SUCCESS).result(systemUserMapper.selectSystemUserById(id));
    }

    @Override
    public int selectUserInfoByCenterId(String id) {
        return systemUserMapper.selectUserInfoByCenterId(id);
    }

}
