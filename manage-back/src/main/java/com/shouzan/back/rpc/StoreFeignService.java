package com.shouzan.back.rpc;


import com.shouzan.uuc.biz.StoreService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @Author: bin.yang
 * @Date: 2018/10/10 15:04
 * @Description:  用户中心 门店接口
 */
@FeignClient(value = "uuc-back")
public interface StoreFeignService extends StoreService {

}
