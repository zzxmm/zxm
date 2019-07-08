package com.shouzan.back.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
/**
 * 功能描述:  门店表
 *
 * @param:
 * @return:
 * @auther: bin.yang
 * @date: 2018/9/14 下午4:20
 */
@Data
public class Store<T> implements Serializable {

    private static final long serialVersionUID = 2624561077640667894L;

    //门店ID
    private Integer id;

    //门店名称
    @NotNull(message = "门店名称不能为空")
    private String storeName;

    // * 所属商家
    private String belongedMer;

    //所属商家ID
    @NotNull(message = "所属商家ID不能为空")
    private Integer merId;

    //详细地址
    @NotNull(message = "详细地址不能为空")
    private String address;

    //创建时间
    private Date createTime;

    //创建人ID
    private int creatorId;

    //修改人
    private int lastEditId ;

    //修改时间
    private Date lastEditTime;

    //门店状态
    private Byte storeState;

    //省
    private String province;

    //市
    @NotNull(message = "选择所在城市")
    private String city;

    //区
    private String area;

    //营业时间
    private String businessHours;

    //服务号关联 0关联 , 1解绑
    private Byte servicenum;

    //经度
    private double longitude;

    //纬度
    private double latitude;

    //地理编码
    private String geocoding;

    //门店中心门店ID
    private Integer storeCenterId;

    //商户中心商户ID
    private Integer merCenterId;


}