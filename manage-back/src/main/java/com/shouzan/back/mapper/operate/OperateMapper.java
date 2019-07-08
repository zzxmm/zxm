package com.shouzan.back.mapper.operate;

import com.shouzan.back.entity.operate.Operate;
import com.shouzan.back.vo.SearchSatisfy;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2019/1/21 15:47
 * @Description: 运营页 MAPPER
 */
public interface OperateMapper extends Mapper<Operate> {

    int queryPageCount(SearchSatisfy search);

    List<Operate> queryPageList(SearchSatisfy search);

    int addOperateInfo(Operate operate);

    int updateCardStatus(@Param("id") String id, @Param("status") Integer status);

    int updateOperateCardInfo(Operate operate);

    Operate findInfoById(Integer id);

    int deleteInfoById(Integer id);

    List<Operate> selectInfoByStrId(@Param("id")String id);

    int setOperateLinkUrl(@Param("id")Integer id, @Param("url")String url);

    int resetOperateCardStock(@Param("id")Integer id, @Param("count")int count);
}
