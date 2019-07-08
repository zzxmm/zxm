package com.shouzan.back.rpc.union;

import lombok.Data;

/**
 * @ClassName: com.shouzan.back.rpc.union.UserList
 * @Author: bin.yang
 * @Date: 2019/6/12 16:02
 * @Description: TODO
 */
@Data
public class UserList {

    private Integer id;

    private String openId;

    private String userId;

    private String lang = "zh_CN";


}
