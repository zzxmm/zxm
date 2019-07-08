package com.shouzan.back.entity.website;

import lombok.Data;


import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
/**
 * @Author: man.z
 * @Date: 2019-05-08 13:02
 * @Description: description
 */
@Data
public class WebsiteCustomer implements Serializable {

    private static final long serialVersionUID = -8573167588119505215L;

    private Integer id;

    //选择类型
    private Byte selectType;

    //客户身份
    @NotNull(message = "客户身份不能为空")
    private Byte customerType;

    //客户名称
    @NotNull(message = "客户名称不能为空")
    private String customerName;

    //联系方式
    @NotNull(message = "联系方式不能为空")
    private String phone;

    //创建时间
    private Date createTime;

    //回访状态
    private Byte state;

    //修改人
    private Integer lastEditId;

    //修改时间
    private Date lastEditTime;

}