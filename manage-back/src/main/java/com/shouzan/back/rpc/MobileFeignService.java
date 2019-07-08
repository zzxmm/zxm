package com.shouzan.back.rpc;


import com.shouzan.back.vo.WithdrawalRequest;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: bin.yang
 * @Date: 2018/10/10 15:04
 * @Description:  用户中心 商户接口
 */
@FeignClient(value = "mobile-back")
public interface MobileFeignService {

    @RequestMapping(value = "/wx/send-red-pack", method = RequestMethod.POST)
    @ResponseBody
    public String sendRedPack(WithdrawalRequest withdrawalRequest);

}
