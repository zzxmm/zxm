package com.shouzan.back.biz;

import com.shouzan.back.entity.website.WebsiteText;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;

import java.util.List;

/**
 * @ClassName: com.shouzan.back.biz.WebsiteTextBiz
 * @Author: shouzan
 * @Date: 2019-05-09 09:57
 * @Description: 宣传文
 */
public interface WebsiteTextBiz  {

    int queryPageCount(SearchSatisfy search);

    List<WebsiteText> queryPageList(SearchSatisfy search, int pageSize, int current);

    ObjectRestResponse insertWebsiteTextInfo(WebsiteText entity);

    ObjectRestResponse selectWebsiteByIdInfo(Integer id);

    ObjectRestResponse updateWebsiteByIdInfo(WebsiteText entity);

    ObjectRestResponse deleteWebsiteByIdInfo(Integer id);


}
