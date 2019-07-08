package com.shouzan.back.entity.card;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/12/28 12:15
 * @Description:  封面摘要 信息表
 */
@Data
public class AbstractTemp  implements Serializable {

    private static final long serialVersionUID = -7167289254526379660L;

    //封面摘要简介。
    @JsonProperty("abstract")
    @JSONField(name = "abstract")
    private String abstractTempInfo;

    //封面图片列表，仅支持填入一 个封面图片链接， 上传图片接口 上传获取图片获得链接，填写 非CDN链接会报错，并在此填入。 建议图片尺寸像素850*350
    private List<String> icon_url_list;

}
