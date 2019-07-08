package com.shouzan.back.mapper.website;

import com.shouzan.back.entity.website.WebsiteImg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WebsiteImgMapper {

    int addWebsitaImgsInfo(@Param("list") List<WebsiteImg> list , @Param("textId") Integer textId);

    int deleteByTextId(Integer textId);

}