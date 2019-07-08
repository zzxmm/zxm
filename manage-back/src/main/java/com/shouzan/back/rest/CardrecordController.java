package com.shouzan.back.rest;

import com.github.pagehelper.PageHelper;
import com.shouzan.back.biz.CardrecordBiz;
import com.shouzan.back.biz.impl.CardrecordBizImpl;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.Cardrecord;
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
 * @Date: 2018/9/26 10:31
 * @Description: 卡卷记录管理
 */
@Controller
@RequestMapping("record")
@Validated
@Slf4j
public class CardrecordController extends BaseController<CardrecordBizImpl, Cardrecord> {

    @Autowired
    private CardrecordBiz cardrecordBiz;

    @Autowired
    private UserFeignService userFeignService;

    /**
     * 功能描述:  卡卷记录列表分页查询
     *
     * @auther: bin.yang
     * @date: 2018/9/26 上午11:41
     */
    @RequestMapping(value = "/page" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Cardrecord> page(@RequestParam(defaultValue = "10") int pageSize , @RequestParam(defaultValue = "1") int current,
                                          SearchSatisfy search){
        try {
            search.setTypes(0);
            int count = cardrecordBiz.queryPageCount(search);
            PageHelper.startPage(current,pageSize,false);
            List<Cardrecord> list = cardrecordBiz.queryPageList(search);
            log.info("request : 卡卷记录列表查询 , 每页显示[{}] , 当前显示[{}] . parameter : [{}]",pageSize,current,search);
            return new ObjectRestResponse<Order>().total(count).rows(list).rel(Response.SUCCESS);
        } catch (Exception e) {
            log.error("卡卷记录分页查询异常 !  异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("卡卷记录分页查询异常 ! 异常信息 : "+e.getMessage());
        }
    }

    /**
     * 功能描述:  卡卷记录详细查询
     *
     * @auther: bin.yang
     * @date: 2018/9/26 上午11:41
     */
    @RequestMapping(value = "/findInfo/{id}" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Cardrecord> findInfo(@PathVariable int id){
        try {
            log.info("request : 卡卷记录详细查询 . parameter : 卡卷记录ID[{}]",id);
            return cardrecordBiz.selectCardrecordById(id,0);
        } catch (Exception e) {
            log.error("卡卷记录详细查询异常 !  异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("卡卷记录详细查询异常 ! 异常信息 : "+e.getMessage());
        }
    }

    /**
     * 功能描述:  卡卷发放
     *
     * @auther: bin.yang
     * @date: 2018/9/26 上午11:41
     */
    @RequestMapping(value = "/update" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Cardrecord> update(@NotNull(message = "选择要修改的卡卷记录ID")String id){
        try {
            log.info("request : 卡卷发放 . parameter : 卡卷记录ID[{}]",id);
            return cardrecordBiz.updateCardrecordById(id);
        } catch (Exception e) {
            log.error("卡卷记录发放异常 !  异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("卡卷记录发放异常 ! 异常信息 : "+e.getMessage());
        }
    }

    /**
     * @Description: (卡卷退款)
     * @param orderNo
     * @[param] [orderNo]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Cardrecord>
     * @author:  bin.yang
     * @date:  2018/10/9 上午11:35
     */
    @RequestMapping(value = "/refund" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Cardrecord> refund(@NotNull(message = "提交要退款的订单号")String orderNo){
        try {
            log.info("request : 卡卷退款 . parameter : 卡卷记录ID[{}]",orderNo);
            return cardrecordBiz.refundCardrecordByOrderNo(orderNo);
        } catch (Exception e) {
            log.error("卡卷记录退款异常 !  异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("卡卷记录退款异常 ! 异常信息 : "+e.getMessage());
        }
    }

}
