package com.shouzan.back.mapper.website;



import com.shouzan.back.entity.website.WebsiteCustomer;

import com.shouzan.back.vo.SearchSatisfy;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: man.z
 * @Date: 2019-05-08 13:59
 * @Description: 官网-商户合作
 */

public interface WebsiteCustomerMapper extends Mapper<WebsiteCustomer> {

    int queryWebsiteCustomerListCount(SearchSatisfy search);

    List<WebsiteCustomer> queryWebsiteCustomerList(SearchSatisfy search);

    List<WebsiteCustomer> queryByStateList(Integer state);

    int updateBystate(@Param("id")  Integer id, @Param("state") Integer state,@Param("userId")Integer userId);

    int updateWedsitaTextInfo(WebsiteCustomer entity);

    int insertWedsitaTextInfo(WebsiteCustomer entity);

}