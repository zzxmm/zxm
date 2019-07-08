package com.shouzan.back.biz.impl;

import com.shouzan.back.biz.WebsiteTextBiz;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.website.WebsiteText;
import com.shouzan.back.mapper.website.WebsiteImgMapper;
import com.shouzan.back.mapper.website.WebsiteTextMapper;
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
 * @ClassName: com.shouzan.back.biz.impl.WebsitaTextBizImp
 * @Author: shouzan
 * @Date: 2019-05-09 10:55
 * @Description: 官网-案例
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class WebsitaTextBizImp extends BaseBiz<WebsiteTextMapper,WebsiteText> implements WebsiteTextBiz {

    @Autowired
    WebsiteTextMapper websiteTextMapper;
    @Autowired
    WebsiteImgMapper websiteImgMapper;

    /**
     * @Description: 计算总文章数量

     * @[param] [search]
     * @return int
     * @author:  man.z
     * @date:  2019-05-09 10:57
     */
    @Override
    public int queryPageCount(SearchSatisfy search) {

        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() +"%");
        }else{
            search.setKeywords(null);
        }
        return websiteTextMapper.queryWebsiteTextListCount(search);
    }

    /**
     * @Description: 查询数据集合

     * @[param] [search]
     * @return java.util.List<com.shouzan.back.entity.website.WebsiteText>
     * @author:  man.z
     * @date:  2019-05-09 11:08
     */
    @Override
    public List<WebsiteText> queryPageList(SearchSatisfy search, int pageSize, int current) {

        if (StringUtils.isNotBlank(search.getKeywords())){
            search.setKeywords("%" + search.getKeywords().trim() +"%");
        }else{
            search.setKeywords(null);
        }
        return websiteTextMapper.queryWebsiteTextList(search,pageSize,(current-1)*pageSize);
    }

    /**
     * @Description: 添加文章

     * @[param] [entity]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  man.z
     * @date:  2019-05-09 11:09
     */
    @Override
    public ObjectRestResponse insertWebsiteTextInfo(WebsiteText entity) {

        int i = websiteTextMapper.insertWebsiteText(entity);
        if(i > 0){
            websiteImgMapper.addWebsitaImgsInfo(entity.getImgs(),entity.getId());
            return new ObjectRestResponse().rel(Response.SUCCESS).msg("新增案例成功");
        }else {
            return new ObjectRestResponse().rel(Response.FAILURE).msg("新增案例失败");
        }
    }

    /**
     * @Description: 查看文章详情

     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  man.z
     * @date:  2019-05-09 15:30
     */
    @Override
    public ObjectRestResponse selectWebsiteByIdInfo(Integer id) {
        WebsiteText websiteText = websiteTextMapper.selectWebsiteById(id);
        return  new ObjectRestResponse<WebsiteText>().rel(Response.SUCCESS).result(websiteText);
    }

    /**
     * @Description: 根据id修改文章信息

     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  man.z
     * @date:  2019-05-09 15:32
     */
    @Override
    public ObjectRestResponse updateWebsiteByIdInfo(WebsiteText entity) {

        int text = websiteTextMapper.updateWebsiteById(entity);

        int deletImg = websiteImgMapper.deleteByTextId(entity.getId());

        int addImg = websiteImgMapper.addWebsitaImgsInfo(entity.getImgs(), entity.getId());

        if(text > 0 || addImg > 0 ){
            return new ObjectRestResponse<WebsiteText>().rel(Response.SUCCESS).msg("修改案例成功");
        }

        return new ObjectRestResponse<WebsiteText>().rel(Response.FAILURE).msg("修改案例失败");
    }

    @Override
    public ObjectRestResponse deleteWebsiteByIdInfo(Integer id) {

        int text = websiteTextMapper.deleteWebsiteById(id);
        int img = websiteImgMapper.deleteByTextId(id);
        if(text>0 && img>0){
            return  new ObjectRestResponse<WebsiteText>().rel(Response.SUCCESS).msg("案例删除成功");
        }
            return new ObjectRestResponse<WebsiteText>().rel(Response.FAILURE).msg("删除失败");
    }

}
