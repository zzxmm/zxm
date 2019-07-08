package com.shouzan.back.mapper;

import com.shouzan.back.entity.Store;
import com.shouzan.back.vo.SearchSatisfy;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
/**
 * @Author: bin.yang
 * @Date: 2019/1/23 5:20 PM
 *
 * @Description:  商家门店
 */
public interface StoreMapper extends Mapper<Store> {

    List<Store> selectStoreAll(int id);

    int queryPageCount(SearchSatisfy searchSatisfy);

    List<Store> queryPageList(SearchSatisfy searchSatisfy);

    int updateStatus(@Param("id") String id, @Param("status") Integer status);

    Store findInfo(int id);

    int addInfo(Store entity);

    int updateInfo(Store entity);

    int deleteStoreById(int id);

    Store findInfoByCenterId(int id);

    int deleteStoreCoupon(int id);

    List<Store> selectStoreByCoupon(int id);

    String selectCenterIdById(@Param("id") String id);

    int updateStatusByMerId(@Param("id") String id);

    String findCenterIDById(@Param("id")String id);

    Store findInfoById(int id);

    String storeIdListAll(@Param("id")int id,@Param("region")String region);
}