package com.shouzan.back.biz.impl;

import com.shouzan.back.biz.CodeBiz;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.operate.CodeBase;
import com.shouzan.back.entity.operate.CodeList;
import com.shouzan.back.entity.operate.CodeRecord;
import com.shouzan.back.mapper.operate.CodeBaseMapper;
import com.shouzan.back.mapper.operate.CodeListMapper;
import com.shouzan.back.mapper.operate.CodeRecordMapper;
import com.shouzan.back.util.CodeValid;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

/**
 * @ClassName: com.shouzan.back.biz.impl.CodeBizImpl
 * @Author: bin.yang
 * @Date: 2019/4/11 15:07
 * @Description: TODO
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CodeBizImpl implements CodeBiz {

    @Autowired
    private CodeBaseMapper codeMapper;

    @Autowired
    private CodeListMapper codeListMapper;

    @Autowired
    private CodeRecordMapper codeRecordMapper;

    /**
     * @Description: (查询总条数)
     * @param search
     * @[param] [search]
     * @return int
     * @author:  bin.yang
     * @date:  2019/4/11 3:55 PM
     */
    @Override
    public int queryPageCount(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() +"%");
        }else{
            search.setKeywords(null);
        }
        return codeMapper.queryPageCount(search);
    }

    /**
     * @Description: (查询数据集合)
     * @param search
     * @[param] [search]
     * @return java.util.List<com.shouzan.back.entity.operate.CodeBase>
     * @author:  bin.yang
     * @date:  2019/4/11 3:55 PM
     */
    @Override
    public List<CodeBase> queryPageList(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() +"%");
        }else{
            search.setKeywords(null);
        }
        return codeMapper.queryPageList(search);
    }

    /**
     * @Description: (创建串码库)
     * @param entity
     * @[param] [entity]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/4/11 3:55 PM
     */
    @Override
    public ObjectRestResponse createCodeBase(CodeBase entity) {
        int i = codeMapper.createCodeBase(entity);
        if(i > 0){
            return new ObjectRestResponse().rel(Response.SUCCESS).msg("串码库创建成功").result(entity);
        }else {
            return new ObjectRestResponse().rel(Response.FAILURE).msg("串码库创建失败 , 请重新创建 !");
        }
    }

    /**
     * @Description: (修改串码库)
     * @param entity
     * @[param] [entity]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/4/11 3:56 PM
     */
    @Override
    public ObjectRestResponse updateCodeBase(CodeBase entity) {
        int i = codeMapper.updateCodeBase(entity);
        return CodeValid.CodeMsg(i,"修改串码库信息");
    }

    /**
     * @Description: (删除串码库)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/4/11 3:56 PM
     */
    @Override
    public ObjectRestResponse deleteCodeBase(Integer id) {
        int i = codeListMapper.findCodeCountByBaseId(id);
        if(i > 0){
            return new ObjectRestResponse().rel(Response.FAILURE).msg(" 删除失败 , 该库余有未核销的Code码 ! ");
        }else {
            int t = codeMapper.deleteCodeBase(id);
            return CodeValid.CodeMsg(t,"删除串码库信息");
        }
    }

    /**
     * @Description: (查询总条数)
     * @param search
     * @[param] [search]
     * @return int
     * @author:  bin.yang
     * @date:  2019/4/11 5:45 PM
     */
    @Override
    public int queryRecordPageCount(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() +"%");
        }else{
            search.setKeywords(null);
        }
        return codeRecordMapper.queryPageCount(search);
    }

    /**
     * @Description: (串码记录分页列表查询)
     * @param search
     * @[param] [search]
     * @return java.util.List<com.shouzan.back.entity.operate.CodeRecord>
     * @author:  bin.yang
     * @date:  2019/4/11 5:45 PM
     */
    @Override
    public List<CodeRecord> queryRecordPageList(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() +"%");
        }else{
            search.setKeywords(null);
        }
        return codeRecordMapper.queryPageList(search);
    }

    /**
     * @Description: (串码记录详细查询)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/4/11 5:45 PM
     */
    @Override
    public ObjectRestResponse findCardRecordById(int id) {
        CodeRecord codeRecord = codeRecordMapper.findCardRecordById(id);
        return new ObjectRestResponse().rel(Response.SUCCESS).result(codeRecord);
    }

    /**
     * @Description: (查询串码记录详情)
     * @param id
     * @[param] [id]
     * @return com.shouzan.back.entity.operate.CodeRecord
     * @author:  bin.yang
     * @date:  2019/4/17 10:33 AM
     */
    @Override
    public CodeRecord restartCodeRecordById(Integer id) {
        CodeRecord record = codeRecordMapper.findCardRecordById(id);
        return record;
    }

    /**
     * @Description: (更改串码记录状态)
     * @param id
     * @[param] [id]
     * @return int
     * @author:  bin.yang
     * @date:  2019/4/17 10:33 AM
     */
    @Override
    public int updateCodeStatus(Integer id) {
        return codeRecordMapper.updateCodeStatus(id);
    }

    /**
     * @Description: (获取活动串码ID)
     * @param search
     * @[param] [search]
     * @return java.lang.String
     * @author:  bin.yang
     * @date:  2019/4/17 3:38 PM
     */
    @Override
    public String findInIdByOperate(SearchSatisfy search) {
        return codeMapper.findInIdByOperate(search);
    }

    /**
     * @Description: (悲观锁异步同步库存)
     * @param baseId
     * @param maxId
     * @[param] [baseId, maxId]
     * @return int
     * @author:  bin.yang
     * @date:  2019/4/17 5:03 PM
     */
    @Override
    public int updateOperateStocks(Integer baseId, Integer maxId) {
        int i = 0;
        if(maxId == null){
            i = codeListMapper.updateOperateStocks(baseId);
        }else {
            int plus = codeListMapper.findCountByMaxId(maxId, baseId);
            int id = codeListMapper.lockFindByBaseId(baseId);
            i = codeListMapper.increaseOperateStocks(id, plus);
        }
        return i;
    }

}
