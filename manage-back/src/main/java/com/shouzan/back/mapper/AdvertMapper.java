package com.shouzan.back.mapper;

import com.shouzan.back.entity.Advert;
import com.shouzan.back.vo.SearchSatisfy;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
/**
 * @Author: bin.yang
 * @Date: 2019/1/23 5:17 PM
 *
 * @Description:  广告
 */
public interface AdvertMapper extends Mapper<Advert> {

    int queryPageCount(SearchSatisfy search);

    List<Advert> queryPageList(SearchSatisfy search);

    Advert queryAdvertById(int id);

    int updateAdvertStatus(@Param("id") String id, @Param("status") Integer status);

    int updateAdvertInfoById(Advert entity);

    int insertAdvertInfo(Advert entity);

    int batchInsertAdvertInfo(@Param("list")List<Advert> list, @Param("userId")int id);

    int updateAdvertStatusByBank(@Param("id")String id);
}