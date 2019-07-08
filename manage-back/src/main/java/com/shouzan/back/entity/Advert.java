package com.shouzan.back.entity;

import com.shouzan.back.util.CodeValid;
import lombok.Data;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
/**
 *
 * 功能描述:  广告表
 *
 * @param:
 * @return: 
 * @auther: bin.yang
 * @date: 2018/9/11 上午9:52
 */
@Data
public class Advert implements Serializable {

    private static final long serialVersionUID = 1594628821145578612L;

    private Integer id;

    /** 广告名称 **/
    @NotNull(message = "广告名称不能为空")
    private String advertName;

    /** 广告封面图 **/
    @NotNull(message = "广告封面不能为空")
    private String coverPic;

    /** 广告详情 **/
    private String advertDetails;

    /** 广告外链 **/
    private String advertUrl;

    /** 上架时间 **/
    private Date upshelfTime;

    /** 下架时间 **/
    private Date downshelfTime;

    /** 广告状态 **/
    @NotNull(message = "请设置广告状态")
    private Byte advertState;

    /** 广告位置: 0、信用卡banner；1、福利社banner；  2、福利社列表  .  多个banner直接逗号拼接 **/
    @NotNull(message = "请选择广告投放位置")
    private String advertPosition;

    /** 创建时间 **/
    private Date createTime;

    /** 创建人 **/
    private Integer creatorId;

    /** 修改时间 **/
    private Date lastEditTime;

    /** 修改人 **/
    private Integer lastEditId;

    /** 优先级 **/
    private Integer priority;

    /** 是否精选 :  0非精选  1精选 **/
    private Byte isSelect;

    /** 是否特惠 :  0非特惠   1特惠 **/
    private Byte isPreferen;

    /** 单选银行 **/
    private Integer bankId;

    @Transient
    private Bank bank;

    public void setUpshelfTime(String upshelfTime) {
        this.upshelfTime = CodeValid.dateFormat(upshelfTime);
    }

    public void setCreateTime(String createTime) {
        this.createTime = CodeValid.dateFormat(createTime);
    }

    public void setLastEditTime(String lastEditTime) {
        this.lastEditTime = CodeValid.dateFormat(lastEditTime);
    }

    public void setDownshelfTime(String downshelfTime) {
        this.downshelfTime = CodeValid.dateFormat(downshelfTime);
    }

}