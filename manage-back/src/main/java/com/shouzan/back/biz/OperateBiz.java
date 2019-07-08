package com.shouzan.back.biz;

import com.shouzan.back.entity.operate.Operate;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;

import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2019/1/21 15:44
 * @Description: 运营 BIZ
 */
public interface OperateBiz {

    int queryPageCount(SearchSatisfy search);

    List<Operate> queryPageList(SearchSatisfy search);

    ObjectRestResponse addOperateInfo(Operate operate);

    ObjectRestResponse updateCardStatus(String id, Integer status);

    ObjectRestResponse updateOperateCardInfo(Operate operate);

    Operate findInfoById(Integer id);

    ObjectRestResponse deleteInfoById(Integer id);

    ObjectRestResponse validationData(Operate operate);

}
