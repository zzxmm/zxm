package com.shouzan.back.mapper;

import com.shouzan.back.entity.Merchants;
import com.shouzan.back.vo.SearchSatisfy;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
/**
 * @Author: bin.yang
 * @Date: 2019/1/23 5:19 PM
 *
 * @Description:  商户
 */
public interface MerchantsMapper extends Mapper<Merchants> {

    List<Merchants> selectMerAll();

    int queryPageCount(SearchSatisfy searchSatisfy);

    List<Merchants> queryPageList(SearchSatisfy searchSatisfy);

    Merchants selectMerInfoById(int id);

    int updateMerchantsStatus(@Param("id") String id, @Param("status") Integer status);

    int insertMerchantsInFo(Merchants entity);

    int updateMerchantsInFo(Merchants entity);

    int selectMerInfoByCenterId(@Param("id") String id);

    Merchants findInfoByCouponCenter(Integer id);

    Merchants findInfoById(Integer id);

    String queryIdByCenterId(@Param("id")String id);

    int findIdByCenterId(Integer id);

    String findMerCenterIdByMerId(@Param("id")String id);
}