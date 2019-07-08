package com.shouzan.back.rest;

import com.github.pagehelper.PageHelper;
import com.shouzan.back.biz.OrderBiz;
import com.shouzan.back.biz.impl.OrderBizImpl;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.Order;
import com.shouzan.back.rpc.UserFeignService;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;
import com.shouzan.common.rest.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/9/19 17:45
 * @Description:  订单管理
 */
@Controller
@RequestMapping("/order")
@Validated
@Slf4j
public class OrderController extends BaseController<OrderBizImpl, Order> {

    @Autowired
    private OrderBiz orderBiz;

    @Autowired
    private UserFeignService userFeignService;

    /**
     * 功能描述:   订单列表分页查询
     *
     * @auther: bin.yang
     * @date: 2018/9/26 上午10:42
     */
    @RequestMapping(value = "/page" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Order> page(@RequestParam(defaultValue = "10") int pageSize , @RequestParam(defaultValue = "1") int current,
                                           SearchSatisfy search){
        try {
            search.setTypes(0);
            int count = orderBiz.queryPageCount(search);
            PageHelper.startPage(current,pageSize,false);
            List<Order> list = orderBiz.queryPageList(search);
            log.info("request : 订单列表查询 , 每页显示[{}] , 当前显示[{}] . parameter : [{}]",pageSize,current,search);
            return new ObjectRestResponse<Order>().total(count).rows(list).rel(Response.SUCCESS);
        } catch (Exception e) {
            log.error("订单分页查询异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * 功能描述:   订单详细信息查询
     *
     * @auther: bin.yang
     * @date: 2018/9/26 上午10:42
     */
    @RequestMapping(value = "/findInfo/{id}" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Order> findInfo(@PathVariable int id){
        try {
            log.info("request : 订单详细查询 . parameter : 订单ID[{}]",id);
            return orderBiz.queryOrderById(id);
        } catch (Exception e) {
            log.error("订单详细查询异常 ! 异常信息 : [{}]" , e);
           return new ObjectRestResponse().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * 功能描述:   订单撤销
     *
     * @auther: bin.yang
     * @date: 2018/9/26 上午10:42
     */
    @RequestMapping(value = "/update" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Order> update(@NotNull(message = "选择要撤销的订单") int id){
        try {
            log.info("request : 撤销订单 . parameter : 订单ID[{}]",id);
            return orderBiz.updateOrderStatus(id);
        } catch (Exception e) {
            log.error("订单撤销异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

}
