package com.shouzan.back.biz;

import com.shouzan.back.entity.website.WebsiteCustomer;

import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;


import java.util.List;

/**
 * @ClassName: com.shouzan.back.biz.WebsiteCustomerBiz
 * @Author: shouzan
 * @Date: 2019-05-08 15:10
 * @Description: TODO
 */
public interface WebsiteCustomerBiz {

    int queryWebsiteCustomerListCount(SearchSatisfy search);

    List<WebsiteCustomer> queryWebsiteCustomerList(SearchSatisfy search);

    ObjectRestResponse updateWebsiteBystate(Integer id, Integer State, Integer userId);

    ObjectRestResponse insertInfo(WebsiteCustomer entity);

}

