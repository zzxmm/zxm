package com.shouzan.back.rest;

import com.github.pagehelper.PageHelper;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.shouzan.back.biz.*;
import com.shouzan.back.biz.impl.OperateBizImpl;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.Cardrecord;
import com.shouzan.back.entity.Order;
import com.shouzan.back.entity.operate.CodeRecord;
import com.shouzan.back.entity.operate.Operate;
import com.shouzan.back.entity.operate.RechargeOrder;
import com.shouzan.back.entity.operate.RechargeRecord;
import com.shouzan.back.util.QCloudSms;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;
import com.shouzan.common.rest.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: bin.yang
 * @Date: 2019/1/21 15:38
 * @Description:   运营H5
 */
@Controller
@RequestMapping(value = "/operate")
@Slf4j
@Validated
public class OperateController extends BaseController<OperateBizImpl, Operate> {

    @Autowired
    private OperateBiz operateBiz;

    @Autowired
    private SystemUserController systemUser;

    @Autowired
    private RuleController ruleController;

    @Autowired
    private RuleBiz ruleBiz;

    /**
     * @Description: (运营页基础信息分页查询)
     * @param pageSize
     * @param current
     * @param search
     * @[param] [pageSize, current, search]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/1/21 4:48 PM
     */
    @RequestMapping(value = "/page" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse page(@RequestParam(defaultValue = "10") int pageSize , @RequestParam(defaultValue = "1") int current,
                                   SearchSatisfy search){
        try {
            int count = operateBiz.queryPageCount(search);
            PageHelper.startPage(current,pageSize,false);
            List<Operate> list = operateBiz.queryPageList(search);
            log.info("request : 运营页信息分页查询 , 每页显示[{}] , 当前显示[{}] . parameter : [{}]",pageSize,current,search);
            return new ObjectRestResponse<>().total(count).rows(list).rel(Response.SUCCESS);
        } catch (Exception e) {
            log.error("运营活动分页查询异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (新增运营卡卷信息)
     * @param operate
     * @[param] [operate]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/1/21 4:48 PM
     */
    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse add(@Valid @RequestBody Operate operate){
        try {
            ObjectRestResponse<Operate> restResponse = ruleController.validationDateStatus(operate);
            if(!restResponse.getRel()){
                return restResponse;
            }
            ObjectRestResponse response = operateBiz.validationData(operate);
            if(!response.getRel()){
                return response;
            }
            operate.setCreatorId(systemUser.getUserId());
            log.info("request : 新增运营页信息 , parameter : [{}]",operate);
            ObjectRestResponse res = operateBiz.addOperateInfo(operate);
            if(res.getRel()){
                return ruleBiz.addOperateRuleList(operate);
            }else {
                return res;
            }
        } catch (Exception e) {
            log.error("新增运营活动异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (修改卡卷状态)
     * @param id
     * @param status
     * @[param] [id, status]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/1/21 4:48 PM
     */
    @RequestMapping(value = "/update" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse update(@NotNull(message = "请选择卡卷") String id , @NotNull(message = "请选择状态") Integer status){
        try {
            log.info("request : 修改卡卷状态 , 卡卷ID : [{}] . 修改状态 : [{}]",id,status);
            return operateBiz.updateCardStatus(id,status);
        } catch (Exception e) {
            log.error("运营活动修改状态异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (修改基础信息)
     * @param operate
     * @[param] [operate]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/1/21 4:53 PM
     */
    @RequestMapping(value = "/updateInfo" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse updateInfo(@Valid @RequestBody Operate operate){
        try {
            ObjectRestResponse<Operate> restResponse = ruleController.validationDateStatus(operate);
            if(!restResponse.getRel()){
                return restResponse;
            }
            ObjectRestResponse response = operateBiz.validationData(operate);
            if(!response.getRel()){
                return response;
            }
            operate.setLastEditId(systemUser.getUserId());
            log.info("request : 修改卡卷信息 , parameter : [{}]",operate);
            ObjectRestResponse res = operateBiz.updateOperateCardInfo(operate);
            if(res.getRel()){
                return ruleBiz.updateOperateRuleList(operate);
            }else {
                return res;
            }
        } catch (Exception e) {
            log.error("修改运营活动异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (通过ID查询详细信息)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/1/21 4:58 PM
     */
    @RequestMapping(value = "/findInfo/{id}" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse findInfo(@PathVariable Integer id) {
        try {
            log.info("request : 查询详细信息 , parameter : [{}]", id);
            Operate operate = operateBiz.findInfoById(id);
            if (operate != null) {
                return ruleBiz.selectOperateRuleList(operate);
            } else {
                return new ObjectRestResponse<>().rel(Response.FAILURE).msg("查询详情失败");
            }
        } catch (Exception e) {
            log.error("运营活动详细查询异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }



    /**
     * @Description: (删除运营页信息)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/1/21 5:10 PM
     */
    @RequestMapping(value = "/delete" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse delete(@NotNull(message = "请选择要删除的信息") Integer id){
        try {
            log.info("request : 删除运营信息 , parameter : [{}]",id);
            return operateBiz.deleteInfoById(id);
        } catch (Exception e) {
            log.error("删除运营活动异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }


}


/**
 * @Author: bin.yang
 * @Date: 2019/1/23 2:52 PM
 *
 * @Description:  运营H5订单管理
 */
@Controller
@RequestMapping(value = "opeOrder")
@Slf4j
@Validated
class OperateOrderController{

    @Autowired
    private OrderBiz orderBiz;

    /**
     * @Description: (H5订单分页查询)
     * @param pageSize
     * @param current
     * @param search
     * @[param] [pageSize, current, search]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/1/23 4:16 PM
     */
    @RequestMapping(value = "page" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse page(@RequestParam(defaultValue = "10") int pageSize , @RequestParam(defaultValue = "1") int current,
                                   SearchSatisfy search,@RequestParam(defaultValue = "0") Integer type){
        try {

            log.info("request : 运营卡卷订单分页查询 , 每页显示[{}] , 当前显示[{}] . parameter : [{}]",pageSize,current,search);
            if(type == 0){
                search.setTypes(2);
                int count = orderBiz.queryPageCount(search);
                PageHelper.startPage(current,pageSize,false);
                List<Order> list = orderBiz.queryPageList(search);
                return new ObjectRestResponse<>().total(count).rows(list).rel(Response.SUCCESS);
            }else if(type == 1){
                int count = orderBiz.queryRechPageCount(search);
                PageHelper.startPage(current,pageSize,false);
                List<RechargeOrder> list = orderBiz.queryPageRechList(search);
                return new ObjectRestResponse<>().total(count).rows(list).rel(Response.SUCCESS);
            }else if(type == 2){
                search.setTypes(4);
                int count = orderBiz.queryPageCount(search);
                PageHelper.startPage(current,pageSize,false);
                List<Order> list = orderBiz.queryPageList(search);
                return new ObjectRestResponse<>().total(count).rows(list).rel(Response.SUCCESS);
            }else {
                return new ObjectRestResponse<>().msg("未找到类型结果").rel(Response.FAILURE);
            }
        } catch (Exception e) {
            log.error("运营订单分页查询异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (查询H5订单详细信息)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Order>
     * @author:  bin.yang
     * @date:  2019/1/23 4:17 PM
     */
    @RequestMapping(value = "/findInfo/{id}" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse findInfo(@PathVariable int id,@RequestParam(defaultValue = "0") Integer type){
        try {
            log.info("request : (H5)订单详细查询 . parameter : 订单ID[{}]",id);
            if(type == 0 || type == 2){
                return orderBiz.queryOrderById(id);
            }else if(type == 1){
                return orderBiz.queryRechOrderById(id);
            }else{
                return new ObjectRestResponse().rel(Response.FAILURE).msg("未找到类型结果");
            }
        } catch (Exception e) {
            log.error("运营订单详细查询异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (撤销订单)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Order>
     * @author:  bin.yang
     * @date:  2019/1/23 4:17 PM
     */
    @RequestMapping(value = "/update" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Order> update(@NotNull(message = "选择要撤销的订单") int id){
        try {
            log.info("request : (H5)撤销订单 . parameter : 订单ID[{}]",id);
            return orderBiz.updateOrderStatus(id);
        } catch (Exception e) {
            log.error("运营订单撤销异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse().rel(Response.FAILURE).msg(e.getMessage());
        }
    }
}


/**
 * @Author: bin.yang
 * @Date: 2019/1/23 2:52 PM
 *
 * @Description:  运营H5卡卷记录
 */
@Controller
@RequestMapping(value = "opeRecord")
@Slf4j
@Validated
class OperateCardRecord{

    @Autowired
    private CardrecordBiz cardrecordBiz;

    @Autowired
    private CodeBiz codeBiz;

    @Autowired
    private QCloudSms qCloudSms;

    @Autowired
    private OperateBiz operateBiz;

    /**
     * @Description: (H5卡卷记录分页查询)
     * @param pageSize
     * @param current
     * @param search
     * @[param] [pageSize, current, search]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/1/23 4:28 PM
     */
    @RequestMapping(value = "page" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse page(@RequestParam(defaultValue = "10") int pageSize , @RequestParam(defaultValue = "1") int current,
                                   SearchSatisfy search,@RequestParam(defaultValue = "0") Integer type){
        log.info("request : H5卡卷记录分页查询 , 每页显示[{}] , 当前显示[{}] . parameter : [{}]",pageSize,current,search);
        try {
            if(type == 0){
                search.setTypes(2);
                int count = cardrecordBiz.queryPageCount(search);
                PageHelper.startPage(current,pageSize,false);
                List<Cardrecord> list = cardrecordBiz.queryPageList(search);
                return new ObjectRestResponse<>().total(count).rows(list).rel(Response.SUCCESS);
            }else if(type == 1){
                int count = cardrecordBiz.queryPageRechCount(search);
                PageHelper.startPage(current,pageSize,false);
                List<RechargeRecord> list = cardrecordBiz.queryPageRechList(search);
                return new ObjectRestResponse<>().total(count).rows(list).rel(Response.SUCCESS);
            }else if(type == 2){
                search.setTypes(4);
                int count = cardrecordBiz.queryPageCount(search);
                PageHelper.startPage(current,pageSize,false);
                List<Cardrecord> list = cardrecordBiz.queryPageList(search);
                return new ObjectRestResponse<>().total(count).rows(list).rel(Response.SUCCESS);
            }else if(type == 3){
                int count = codeBiz.queryRecordPageCount(search);
                PageHelper.startPage(current,pageSize,false);
                List<CodeRecord> list = codeBiz.queryRecordPageList(search);
                return new ObjectRestResponse<>().total(count).rows(list).rel(Response.SUCCESS);
            }else {
                return new ObjectRestResponse<>().rel(Response.FAILURE).msg("未找到类型结果");
            }
        } catch (Exception e) {
            log.error("运营记录分页查询异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (H5卡卷记录详细记录)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Cardrecord>
     * @author:  bin.yang
     * @date:  2019/1/23 4:29 PM
     */
    @RequestMapping(value = "/findInfo/{id}" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse findInfo(@PathVariable int id,@RequestParam(defaultValue = "0") Integer type){
        try {
            log.info("request : H5卡卷记录详细查询 . parameter : 卡卷记录ID[{}]",id);
            if(type == 0){
                return cardrecordBiz.selectCardrecordById(id,2);
            }else if(type == 1){
                return cardrecordBiz.findRechCardrecordById(id);
            }else if(type == 2){
                return cardrecordBiz.selectCardrecordById(id,4);
            }else if(type == 3){
                return codeBiz.findCardRecordById(id);
            }else {
                return new ObjectRestResponse<>().rel(Response.FAILURE).msg("未找到类型结果");
            }
        } catch (Exception e) {
            log.error("运营记录详细查询异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (串码重新发放)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Cardrecord>
     * @author:  bin.yang
     * @date:  2019/1/23 4:29 PM
     */
    @RequestMapping(value = "/restart" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse restart(@NotNull(message = "选择要重新发放串码的记录ID")Integer id){
        try {
            log.info("request : 串码重新发放 . parameter : 卡卷记录ID[{}]",id);
            CodeRecord codeRecord = codeBiz.restartCodeRecordById(id);
            Operate operate = operateBiz.findInfoById(codeRecord.getActivityId());
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
            StringBuilder builder = new StringBuilder();
            builder.append(codeRecord.getCode()).append("，有效期至").
                    append(format.format(operate.getCouponValidTimeEnd())).append("，").
                    append(operate.getMessage()).append("。").
                    append("如有问题请咨询客服电话4008060680。");
            //数组具体的元素个数和模板中变量个数必须一致
            String[] params = {operate.getCardName(),builder.toString()};
            ObjectRestResponse response = qCloudSms.
                    messageCodeSms(codeRecord.getUserTel(), params);
            if(response.getRel()){
                codeBiz.updateCodeStatus(id);
            }
            return response;
        } catch (Exception e) {
            log.error("串码重新发放异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (卡卷退款)
     * @param orderNo
     * @[param] [orderNo]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Cardrecord>
     * @author:  bin.yang
     * @date:  2019/1/23 4:30 PM
     */
    @RequestMapping(value = "/refund" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Cardrecord> refund(@NotNull(message = "提交要退款的订单号")String orderNo){
        try {
            log.info("request : (H5)卡卷退款 . parameter : 卡卷记录ID[{}]",orderNo);
            return cardrecordBiz.refundCardrecordByOrderNo(orderNo);
        } catch (Exception e) {
            log.error("卡卷退款异常 ! 异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }


}