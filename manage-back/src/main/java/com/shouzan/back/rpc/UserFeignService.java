package com.shouzan.back.rpc;

import com.shouzan.uuc.biz.UserService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @Author: bin.yang
 * @Date: 2018/10/16 11:35
 * @Description: 用户中心 用户接口调用
 */
@FeignClient(value = "uuc-back")
public interface UserFeignService extends UserService {
}
