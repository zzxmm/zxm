package com.shouzan.back.entity.website;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
/**
 * @Author: man.z
 * @Date: 2019-05-14 11:29
 *
 * @Description: description
 */
@Data
public class WebsiteImg implements Serializable {

    private static final long serialVersionUID = -111999906362963297L;

    //图片ID
    private Integer id;

    //案例ID
    private Integer textId;

    //图片路径
    @NotNull(message = "图片不能为空")
    private String imgUrl;

    private String response;

    //是否为封面
    @NotNull(message = "是否为封面，不能为空")
    private Integer isCover;

}