package com.shouzan.back.rest;

import com.shouzan.back.biz.ZoneBiz;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.Stock;
import com.shouzan.common.msg.ObjectRestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;

/**
 * @ClassName: com.shouzan.back.rest.ZoneController
 * @Author: bin.yang
 * @Date: 2019/4/18 10:10
 * @Description:   数据同步操作类 TODO
 */
@Controller
@RequestMapping("/sku")
@Validated
@Slf4j
public class ZoneController {

    @Autowired
    private ZoneBiz zoneBiz;

    @RequestMapping("stockUp")
    @ResponseBody
    public ObjectRestResponse stockUpdate(@Validated Stock stock, @NotNull(message = "提交卡卷类型")Integer type){
        try {
            if(type == 0){
                log.info("request : 商户号卡卷库存修改 ,  parameter :  卡卷ID[{}] ",stock.getId());
                return zoneBiz.updateMerchantsCardStock(stock);
            }else if(type == 1){
                log.info("request : 运营H5卡卷库存修改 ,  parameter :  卡卷ID[{}] ",stock.getId());
                return zoneBiz.updateOperateCardStock(stock);
            }else {
                return new ObjectRestResponse().rel(Response.FAILURE).msg("不支持该类型 !");
            }
        } catch (Exception e) {
            log.error("卡卷库存修改异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse().rel(Response.FAILURE).msg("卡卷库存修改失败, 失败原因: "+e.getMessage());
        }
    }
}
