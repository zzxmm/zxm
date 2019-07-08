package com.shouzan.back.biz;

import com.shouzan.back.entity.operate.CodeBase;
import com.shouzan.back.entity.operate.CodeRecord;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;

import java.util.List;

/**
 * @ClassName: com.shouzan.back.biz.CodeBiz
 * @Author: bin.yang
 * @Date: 2019/4/11 15:07
 * @Description: TODO
 */
public interface CodeBiz {

    int queryPageCount(SearchSatisfy search);

    List<CodeBase> queryPageList(SearchSatisfy search);

    ObjectRestResponse createCodeBase(CodeBase entity);

    ObjectRestResponse updateCodeBase(CodeBase entity);

    ObjectRestResponse deleteCodeBase(Integer id);

    int queryRecordPageCount(SearchSatisfy search);

    List<CodeRecord> queryRecordPageList(SearchSatisfy search);

    ObjectRestResponse findCardRecordById(int id);

    CodeRecord restartCodeRecordById(Integer id);

    int updateCodeStatus(Integer id);

    String findInIdByOperate(SearchSatisfy search);

    int updateOperateStocks(Integer baseId, Integer maxId);
}
