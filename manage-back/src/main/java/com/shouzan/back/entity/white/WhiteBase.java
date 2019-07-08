package com.shouzan.back.entity.white;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
/**
 * @Author: bin.yang
 * @Date: 2019/3/28 4:00 PM
 *
 * @Description:
 */
@Data
public class WhiteBase implements Serializable {

    private static final long serialVersionUID = 1785484677712261265L;

    // 主键
    private Integer id;

    // 白名单名称
    @NotNull(message = "请填写白名单名称")
    private String baseName;

    // 名单库标识码
    private String baseSign;

    // 创建时间
    private Date createTime;

    // 创建人
    private Integer creatorId;

    // 修改时间
    private Date lastEditTime;

    // 修改人
    private Integer lastEditId;

}