package com.shouzan.back.biz;

import com.shouzan.back.entity.Stock;
import com.shouzan.common.msg.ObjectRestResponse;

/**
 * @ClassName: com.shouzan.back.biz.ZoneBiz
 * @Author: bin.yang
 * @Date: 2019/4/18 10:21
 * @Description: TODO
 */
public interface ZoneBiz {

    ObjectRestResponse updateMerchantsCardStock(Stock stock);

    ObjectRestResponse updateOperateCardStock(Stock stock);

}
