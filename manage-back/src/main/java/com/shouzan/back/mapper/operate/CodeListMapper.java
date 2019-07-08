package com.shouzan.back.mapper.operate;

import com.shouzan.back.entity.operate.CodeList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2019/4/10 12:34 PM
 *
 * @Description: 串码
 */
public interface CodeListMapper {

    int findCodeCountByBaseId(Integer id);

    void bathPageInsertList(List<CodeList> list);

    int updateOperateStocks(Integer id);

    int updateCodeStatusByBaseId(@Param("baseId")Integer baseId, @Param("localeDate")String toLoca);

    Integer findMaxIdByBaseId(Integer id);

    int findCountByMaxId(@Param("mid")Integer id, @Param("bid")Integer baseId);

    int lockFindByBaseId(Integer id);

    int increaseOperateStocks(@Param("id")Integer id, @Param("plus")int plus);
}