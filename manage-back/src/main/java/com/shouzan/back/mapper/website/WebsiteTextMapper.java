package com.shouzan.back.mapper.website;
import com.shouzan.back.entity.website.WebsiteText;
import com.shouzan.back.vo.SearchSatisfy;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


/**
 * @Author: man.z
 * @Date: 2019-05-08 13:55
 * @Description: 官网-案例
 */
public interface WebsiteTextMapper extends Mapper<WebsiteText> {

    int queryWebsiteTextListCount(SearchSatisfy search);

    List<WebsiteText> queryWebsiteTextList(@Param("search") SearchSatisfy search,@Param("pageSize") int pageSize, @Param("current")int current);

    int insertWebsiteText(WebsiteText entity);

    WebsiteText selectWebsiteById(Integer id);

    int updateWebsiteById(WebsiteText entity);

    int deleteWebsiteById(Integer id);



}