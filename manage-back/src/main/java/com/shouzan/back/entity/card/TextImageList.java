package com.shouzan.back.entity.card;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: bin.yang
 * @Date: 2018/12/28 12:15
 * @Description:  图文信息
 */
@Data
public class TextImageList  implements Serializable {

    private static final long serialVersionUID = -8343317960771457506L;

    //图片链接，必须调用 上传图片接口 上传图片获得链接，并在此填入， 否则报错
    private String image_url;

    //图文描述
    private String text;
}
