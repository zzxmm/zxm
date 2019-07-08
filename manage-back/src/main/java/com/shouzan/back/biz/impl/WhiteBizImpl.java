package com.shouzan.back.biz.impl;

import com.shouzan.back.biz.WhiteBiz;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.white.WhiteBase;
import com.shouzan.back.entity.white.WhiteUser;
import com.shouzan.back.mapper.WhiteMapper;
import com.shouzan.back.util.CodeValid;
import com.shouzan.back.util.FastUtil;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2019/3/28 12:52
 * @Description:
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class WhiteBizImpl implements WhiteBiz {

    @Autowired
    private WhiteMapper whiteMapper;

    /**
     * @Description: (查询白名单库总条数)
     * @param search
     * @[param] [search]
     * @return int
     * @author:  bin.yang
     * @date:  2019/3/28 4:38 PM
     */
    @Override
    public int queryPageCount(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() +"%");
        }else{
            search.setKeywords(null);
        }
        return whiteMapper.queryPageCount(search);
    }

    /**
     * @Description: (查询白名单数据集合)
     * @param search
     * @[param] [search]
     * @return java.util.List<com.shouzan.back.entity.white.WhiteUser>
     * @author:  bin.yang
     * @date:  2019/3/28 4:38 PM
     */
    @Override
    public List<WhiteUser> queryPageList(SearchSatisfy search) {
        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() +"%");
        }else{
            search.setKeywords(null);
        }
        return whiteMapper.queryPageList(search);
    }

    /**
     * @Description: (创建白名单库)
     * @param entity
     * @[param] [entity]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/3/28 4:39 PM
     */
    @Override
    public ObjectRestResponse createWhiteBase(WhiteBase entity) {
        if("".equals(entity.getBaseSign()) || entity.getBaseSign() == null){
            String sign = FastUtil.getDateSign();
            Integer i = whiteMapper.createUserTable(sign,entity.getBaseName());
            if(i == 0){
                entity.setBaseSign(sign);
                int c = whiteMapper.addWhiteBaseInfo(entity);
                if(c > 0){
                    return new ObjectRestResponse().rel(Response.SUCCESS).msg("创建白名单成功").result(entity);
                }else {
                    return new ObjectRestResponse().rel(Response.FAILURE).msg("创建白名单失败 , 请重新创建 !");
                }
            }else {
                return new ObjectRestResponse().rel(Response.FAILURE).msg("创建白名单失败 , 请重新创建 !");
            }
        }else {
            WhiteBase base = whiteMapper.findInfoBySign(entity.getBaseSign());
            if(base != null){
                int v = whiteMapper.addWhiteBaseInfo(entity);
                if(v > 0){
                    return new ObjectRestResponse().rel(Response.SUCCESS).msg("创建白名单成功").result(entity);
                }else {
                    return new ObjectRestResponse().rel(Response.FAILURE).msg("创建白名单失败 , 请重新创建 !");
                }
            }else {
                return new ObjectRestResponse().rel(Response.FAILURE).msg("创建白名单失败 , 请重新选择白名单库 !");
            }
        }
    }

    /**
     * @Description: (修改白名单库信息)
     * @param entity
     * @[param] [entity]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/3/28 4:47 PM
     */
    @Override
    public ObjectRestResponse updateWhiteBase(WhiteBase entity) {
        int i = whiteMapper.updateWhiteBase(entity);
        return CodeValid.CodeMsg(i,"修改");
    }
}
