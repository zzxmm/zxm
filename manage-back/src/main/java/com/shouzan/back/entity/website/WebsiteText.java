package com.shouzan.back.entity.website;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: man.z
 * @Date: 2019-05-08 13:02
 * @Description: description
 */
@Data
public class WebsiteText implements Serializable {

    private static final long serialVersionUID = -1747710612168962262L;

    private Integer id;

    //案例名称
    @NotNull(message = "案列名称不能为空")
    private String caseName;

    //案例简介
    @NotNull(message = "案列简介不能为空")
    private String introduction;

    //创建人
    private Integer creatorId;

    //创建时间
    private Date createTime;

    //修改人
    private Integer lastEditId;

    //修改时间
    private Date lastEditTime;

    //图片集
    @Valid
    @Transient
    private List<WebsiteImg> imgs;

    private String coverUrl;

}