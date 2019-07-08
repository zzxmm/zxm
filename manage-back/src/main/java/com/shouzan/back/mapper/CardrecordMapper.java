package com.shouzan.back.mapper;

import com.shouzan.back.entity.Cardrecord;
import com.shouzan.back.vo.SearchSatisfy;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
/**
 * @Author: bin.yang
 * @Date: 2019/1/23 5:18 PM
 *
 * @Description:  卡卷记录
 */
public interface CardrecordMapper extends Mapper<Cardrecord> {

    int queryPageCount(SearchSatisfy search);

    List<Cardrecord> queryPageList(SearchSatisfy search);

    Cardrecord selectCardrecordById(int id);

    int updateCardrecordById(@Param("id") String id);

    int refundCardrecordByOrderNo(@Param("orderNo") String orderNo);

    void paymentStatusUpdate(@Param("pay")String pay);

    void refundStatusUpdate(@Param("refu") String refu);
}