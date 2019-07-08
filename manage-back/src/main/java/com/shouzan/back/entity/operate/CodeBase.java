package com.shouzan.back.entity.operate;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
/**
 * @Author: bin.yang
 * @Date: 2019/4/10 12:32 PM
 *
 * @Description: 串码库
 */
@Data
public class CodeBase implements Serializable {

    private static final long serialVersionUID = 7317741049764085670L;

    //主键
    private Integer id;

    //串码库名称
    @NotNull(message = "串码库名称不能为空")
    private String baseName;

    //创建时间
    private Date createTime;

    //创建人
    private Integer creatorId;

    //修改时间
    private Date lastEditTime;

    //修改人
    private Integer lastEditId;

}