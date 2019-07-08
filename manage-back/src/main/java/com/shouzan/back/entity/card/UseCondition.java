package com.shouzan.back.entity.card;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: bin.yang
 * @Date: 2018/12/28 12:16
 * @Description:  卡卷使用限制
 */
@Data
public class UseCondition  implements Serializable {

    private static final long serialVersionUID = -1266869864034874862L;

    //指定可用的商品类目，仅用于代金券类型 ，填入后将在券面拼写适用于xxx
    private String accept_category;

    //指定不可用的商品类目，仅用于代金券类型 ，填入后将在券面拼写不适用于xxxx
    private String reject_category;

    //不可以与其他类型共享门槛 ，填写false时系统将在使用须知里 拼写“不可与其他优惠共享”， 填写true时系统将在使用须知里 拼写“可与其他优惠共享”， 默认为true
    private boolean can_use_with_other_discount;

}
