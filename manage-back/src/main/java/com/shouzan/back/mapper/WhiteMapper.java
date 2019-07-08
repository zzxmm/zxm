package com.shouzan.back.mapper;

import com.shouzan.back.entity.white.WhiteBase;
import com.shouzan.back.entity.white.WhiteUser;
import com.shouzan.back.vo.SearchSatisfy;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2019/3/28 12:53
 * @Description:
 */
public interface WhiteMapper {

    int queryPageCount(SearchSatisfy search);

    List<WhiteUser> queryPageList(SearchSatisfy search);

    Integer createUserTable(@Param("sign") String sign, @Param("baseName") String baseName);

    int addWhiteBaseInfo(WhiteBase entity);

    int updateWhiteBase(WhiteBase entity);

    WhiteBase findInfoBySign(@Param("sign") String baseSign);

    String findBaseSignById(Integer id);

    void batchInsertList(@Param("list")List<WhiteUser> list, @Param("sign")String sign);

    WhiteBase findInfoById(Integer id);
}
