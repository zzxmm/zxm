package com.shouzan.back.biz.impl;

import com.shouzan.back.biz.WebsiteCustomerBiz;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.Merchants;
import com.shouzan.back.entity.website.WebsiteCustomer;
import com.shouzan.back.mapper.website.WebsiteCustomerMapper;
import com.shouzan.back.util.CodeValid;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.biz.BaseBiz;
import com.shouzan.common.msg.ObjectRestResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: com.shouzan.back.biz.impl.WebsiteCustomerBizImp
 * @Author: shouzan
 * @Date: 2019-05-08 15:15
 * @Description: 官网-商户合作
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class WebsiteCustomerBizImp extends BaseBiz<WebsiteCustomerMapper,WebsiteCustomer> implements WebsiteCustomerBiz {

    @Autowired
    private WebsiteCustomerMapper websiteCustomerMapper;

    /**
     * @Description: 添加客户信息

     * @[param] [entity]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.website.WebsiteCustomer>
     * @author:  man.z
     * @date:  2019-05-08 16:40
     */
    @Override
    public ObjectRestResponse insertInfo(WebsiteCustomer entity) {
        int i = websiteCustomerMapper.insertWedsitaTextInfo(entity);
        return CodeValid.CodeMsg(i,"添加");
    }

    /**
     * @Description: 查询信息总条数

     * @[param] [search]
     * @return int
     * @author:  man.z
     * @date:  2019-05-08 17:45
     */
    @Override
    public int queryWebsiteCustomerListCount(SearchSatisfy search) {
        if(StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() + "%");
        }else{
            search.setKeywords(null);
        }
        return websiteCustomerMapper.queryWebsiteCustomerListCount(search);
    }

    /**
     * @Description: 查询信息集合列表

     * @[param] [search]
     * @return java.util.List<com.shouzan.back.entity.website.WebsiteCustomer>
     * @author:  man.z
     * @date:  2019-05-08 17:46
     */
    @Override
    public List<WebsiteCustomer> queryWebsiteCustomerList(SearchSatisfy search) {
        if(StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() + "%");
        }else{
            search.setKeywords(null);
        }
        return websiteCustomerMapper.queryWebsiteCustomerList(search);

    }

    /**
     * @Description: 修改回访状态
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.website.WebsiteCustomer>
     * @author:  man.z
     * @date:  2019-05-08 16:38
     */
    @Override
    public ObjectRestResponse<WebsiteCustomer> updateWebsiteBystate(Integer id, Integer state,Integer userId) {

        int i = websiteCustomerMapper.updateBystate(id, state,userId);
        if(i > 0){
            return new ObjectRestResponse<Merchants>().rel(Response.SUCCESS).msg("修改回访状态成功");
        }else{
            return new ObjectRestResponse<Merchants>().rel(Response.FAILURE).msg("修改回访状态失败");
        }

    }

}
