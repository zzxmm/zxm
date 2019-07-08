package com.shouzan.back.mapper;

import com.shouzan.back.entity.Types;
import com.shouzan.back.vo.SearchSatisfy;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
/**
 * @Author: bin.yang
 * @Date: 2019/1/23 5:20 PM
 *
 * @Description:   类型
 */
public interface TypesMapper extends Mapper<Types> {

    int queryPageCount(SearchSatisfy search);

    List<Types> queryPageList(SearchSatisfy search);

    int updateTypeStatus(@Param("id") String id, @Param("status") Integer status);

    Types selectTypeInfoById(int id);

    int insertTypeInfo(Types entity);

    int updateTypeInfo(Types entity);

    List<Types> selectTypeListAll();

    int selectIdByName(@Param("name")String name);
}