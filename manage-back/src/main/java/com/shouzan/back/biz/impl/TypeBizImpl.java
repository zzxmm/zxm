package com.shouzan.back.biz.impl;

import com.shouzan.back.biz.TypeBiz;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.Types;
import com.shouzan.back.mapper.TypesMapper;
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
 * @Date: 2018/9/19 17:51
 * @Description:  类型管理
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TypeBizImpl extends BaseBiz<TypesMapper, Types> implements TypeBiz {

    @Autowired
    private TypesMapper typesMapper;

    /**
     * @Description: (查询总条数)
     * @param search
     * @[param] [search]
     * @return int
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:36
     */
    @Override
    public int queryPageCount(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() + "%");
        }else{
            search.setKeywords(null);
        }
        return typesMapper.queryPageCount(search);
    }

    /**
     * @Description: (查询数据集合)
     * @param search
     * @[param] [search]
     * @return java.util.List<com.shouzan.back.entity.Types>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:37
     */
    @Override
    public List<Types> queryPageList(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() + "%");
        }else{
            search.setKeywords(null);
        }
        return typesMapper.queryPageList(search);
    }

    /**
     * @Description: (修改类型状态)
     * @param id
     * @param status
     * @[param] [id, status]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Types>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:52
     */
    @Override
    public ObjectRestResponse<Types> updateTypeStatus(String id, Integer status) {
        return CodeValid.CodeMsg(typesMapper.updateTypeStatus(id,status),"修改状态");
    }

    /**
     * @Description: (通过ID查询详细信息)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Types>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:46
     */
    @Override
    public ObjectRestResponse<Types> selectTypeInfoById(int id) {
        return new ObjectRestResponse<Types>().result(typesMapper.selectTypeInfoById(id)).rel(Response.SUCCESS);
    }

    /**
     * @Description: (新增类型信息)
     * @param entity
     * @[param] [entity]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Types>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:52
     */
    @Override
    public ObjectRestResponse<Types> insertTypeInfo(Types entity) {
        return CodeValid.CodeMsg(typesMapper.insertTypeInfo(entity),"新增");
    }

    /**
     * @Description: (修改类型信息)
     * @param entity
     * @[param] [entity]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Types>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:53
     */
    @Override
    public ObjectRestResponse<Types> updateTypeInfo(Types entity) {
        return CodeValid.CodeMsg(typesMapper.updateTypeInfo(entity),"修改");
    }

    /**
     * @Description: (查询所有信息)
     * @param
     * @[param] []
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Types>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:53
     */
    @Override
    public ObjectRestResponse<Types> selectTypeListAll() {
        return new ObjectRestResponse<Types>().rows(typesMapper.selectTypeListAll()).rel(Response.SUCCESS);
    }

    @Override
    public int selectIdByName(String name) {
        return typesMapper.selectIdByName(name);
    }
}
