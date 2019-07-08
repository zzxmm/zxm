package com.shouzan.back.rest;

import com.github.pagehelper.PageHelper;
import com.shouzan.back.biz.BillsBiz;
import com.shouzan.back.biz.impl.BillsBizImpl;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.*;
import com.shouzan.back.mapper.SystemUserMapper;
import com.shouzan.back.rpc.MerFeignService;
import com.shouzan.back.util.ExcelUtil;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;
import com.shouzan.common.rest.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/9/27 10:30
 * @Description:  资金台账管理
 */
@Controller
@RequestMapping("/bills")
@Validated
@Slf4j
public class BillsController extends BaseController<BillsBizImpl, Bills> {

    @Autowired
    private BillsBiz billsBiz;

    @Autowired
    private MerFeignService merFeignService;

    @Autowired
    private SystemUserMapper systemUserMapper;

    /**
     * @Description: (账单分页查询)
     * @param pageSize
     * @param current
     * @param search
     * @[param] [pageSize, current, search]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Bills>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:54
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Bills> page(@RequestParam(defaultValue = "10") int pageSize , @RequestParam(defaultValue = "1") int current,
                                           SearchSatisfy search){
        try {
            int count = billsBiz.queryPageCount(search);
            PageHelper.startPage(current,pageSize,false);
            List<Bills> list = billsBiz.queryPageList(search);
            BillsOverview billsOverview = billsBiz.queryBillsOverview(search);
            log.info("request : 账单记录列表查询 , 每页显示[{}] , 当前显示[{}] . parameter : [{}]",pageSize,current,search);
            return new ObjectRestResponse<Coupon>().total(count).rows(list).rel(Response.SUCCESS).result(billsOverview);
        } catch (Exception e) {
            log.error("账单分页查异常 !  异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("账单分页查异常 ! 异常信息 : "+e.getMessage());
        }
    }

    /**
     * @Description: (银行主体列表)
     * @param
     * @[param] []
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Bank>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:55
     */
    @RequestMapping(value = "/bankList", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Bank> bankList(){
        try {
            log.info("request : 交易主体银行列表 . parameter : [无]");
            return billsBiz.selectBankList();
        } catch (Exception e) {
            log.error("银行主体列表异常 !  异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("银行主体列表异常 ! 异常信息 : "+e.getMessage());
        }
    }

    /**
     * @Description: (商户主体列表)
     * @param
     * @[param] []
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Merchants>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:55
     */
    @RequestMapping(value = "/merList", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Merchants> merList(){
        try {
            log.info("request : 所属商家列表查询 , parameter : 无");
            return billsBiz.selectMerList();
        } catch (Exception e) {
            log.error("商户主体列表异常 !  异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("商户主体列表异常 ! 异常信息 : "+e.getMessage());
        }
    }

    /**
     * @Description: (查询详细信息)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Bills>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:55
     */
    @RequestMapping(value = "/findInfo/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Bills> findInfo(@PathVariable int id){
        try {
            log.info("request : 查看账单轨迹 . parameter : 账单ID[{}]",id);
            ObjectRestResponse<Bills> billsResponse = billsBiz.selectBillsInfoById(id);
            SystemUser systemUser = systemUserMapper.selectSystemUserById(billsResponse.getResult().getCreatorId());
            if(systemUser != null){
                billsResponse.getResult().setCreatorName(systemUser.getUserName());
            }
            return billsResponse;
        } catch (Exception e) {
            log.error("账单详细查询异常 !  异常信息 : [{}]" , e);
            return new ObjectRestResponse<Bills>().rel(Response.FAILURE).msg("账单详细查询异常 ! 异常信息 : "+e.getMessage());
        }
    }

    /**
     * @Description: (支出交易)
     * @param putAccParty
     * @param putAccPartyId
     * @param putAccPartyBalance
     * @param payAccParty
     * @param payAccId
     * @param payAccPartyBalance
     * @param transactionAmount
     * @param describes
     * @[param] [putAccParty, putAccPartyId, putAccPartyBalance, payAccParty, payAccId, payAccPartyBalance, transactionAmount, describes]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Bills>
     * @author:  bin.yang
     * @date:  2018/10/8 下午12:47
     */
    @RequestMapping(value = "/expenditure", method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Bills> expenditure(@NotNull(message = "选择交易对象类型")byte putAccParty, @NotNull(message = "选择交易对象主体")int putAccPartyId,
                                                 @NotNull(message = "补充交易对象主体余额")BigDecimal putAccPartyBalance, @NotNull(message = "补充交易对象主体名称")String putAccPartyName ,
                                                 @NotNull(message = "选择资金来源对象")byte payAccParty, @NotNull(message = "选择资金来源主体")int payAccId,
                                                 @NotNull(message = "补充资金来源主体余额")BigDecimal payAccPartyBalance, @NotNull(message = "补充资金来源主体名称")String payAccPartyName ,
                                                 @NotNull(message = "请输入交易金额")BigDecimal transactionAmount, @NotNull(message = "请输入交易描述")String describes){
        try {
            String currentUserId = getCurrentUserId();
            int userId = systemUserMapper.findIdByAccount(currentUserId);
            log.info("request : 支出交易操作 . parameter : 交易对象[{}] , 资金来源[{}] , 交易金额[{}] , 交易描述[{}]",transactionAmount,describes);
            return billsBiz.expenditureTransactionInfo(putAccParty,putAccPartyId,putAccPartyBalance,
                                                       payAccParty,payAccId,payAccPartyBalance,
                                                       transactionAmount,describes,userId,putAccPartyName,payAccPartyName);
        } catch (Exception e) {
            log.error("账单支出交易异常 !  异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("账单支出交易异常 ! 异常信息 : "+e.getMessage());
        }
    }

    /**
     * @Description: (收入交易)
     * @param outAccParty
     * @param outAccPartyId
     * @param outAccPartyBalance
     * @param payAccParty
     * @param payAccId
     * @param payAccPartyBalance
     * @param transactionAmount
     * @param describes
     * @[param] [outAccParty, outAccPartyId, outAccPartyName, outAccPartyBalance, payAccParty, payAccId, payAccPartyName, payAccPartyBalance, transactionAmount, describes]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Bills>
     * @author:  bin.yang
     * @date:  2018/9/30 下午3:56
     */
    @RequestMapping(value = "/incomeTransaction", method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Bills> incomeTransaction(@NotNull(message = "选择交易对象类型")byte outAccParty, @NotNull(message = "选择交易对象主体")int outAccPartyId,
                                                       @NotNull(message = "补充交易对象主体余额")BigDecimal outAccPartyBalance, @NotNull(message = "补充交易对象主体名称")String outAccPartyName ,
                                                       @NotNull(message = "选择资金来源对象")byte payAccParty, @NotNull(message = "选择资金来源主体")int payAccId,
                                                       @NotNull(message = "补充资金来源主体余额")BigDecimal payAccPartyBalance, @NotNull(message = "补充资金来源主体名称")String payAccPartyName ,
                                                       @NotNull(message = "请输入交易金额")BigDecimal transactionAmount, @NotNull(message = "请输入交易描述")String describes){
        try {
            String currentUserId = getCurrentUserId();
            int userId = systemUserMapper.findIdByAccount(currentUserId);
            log.info("request : 收入交易操作 . parameter : 交易对象[{}] , 资金来源[{}] , 交易金额[{}] , 交易描述[{}]",transactionAmount,describes);
            return billsBiz.incomeTransactionInfo(outAccParty,outAccPartyId,outAccPartyBalance,
                                                  payAccParty,payAccId,payAccPartyBalance,
                                                  transactionAmount,describes,userId,outAccPartyName,payAccPartyName);
        } catch (Exception e) {
            log.error("账单收入交易异常 !  异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("账单收入交易异常 ! 异常信息 : "+e.getMessage());
        }
    }

    /**
     * @Description: (导出excel表格)
     * @param searchSatisfy
     * @param response
     * @[param] [searchSatisfy, response]
     * @return void
     * @author:  bin.yang
     * @date:  2018/10/8 下午4:19
     */
    @RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
    public void exportExcel(SearchSatisfy searchSatisfy, HttpServletResponse response){
        try {
            log.info("request : 导出excel数据 . parameter : 交易对象[{}] , 开始时间[{}] ,结束时间[{}] ",searchSatisfy.getId(),searchSatisfy.getStartTimeStart(),searchSatisfy.getStartTimeEnd());
            List<Bills> billsList = billsBiz.exportExcel(searchSatisfy);
            if(billsList != null && billsList.size() > 0){
                for (Bills bills : billsList) {
                    SystemUser systemUser = systemUserMapper.selectSystemUserById(bills.getCreatorId());
                    if(systemUser != null){
                        bills.setCreatorName(systemUser.getUserName());
                    }
                }
            }
            List<String[]> stringList = new ArrayList();
            String[] stringArr ;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Bills bills : billsList) {
                stringArr = new String[]{
                        bills.getId().toString(),
                        bills.getOutAccPartyName(),
                        bills.getTransactionAmount().toString(),
                        bills.getOutAccPartyBalance().toString(),
                        bills.getPutAccPartyName(),
                        bills.getPutAccPartyBalance().toString(),
                        format.format(bills.getCreateTime()),
                        format.format(bills.getLastEditTime()),
                        bills.getCreatorName(),
                        bills.getPayAccPartyName()
                };
                stringList.add(stringArr);
            }
            String[] headers = {"账单ID","出账方","出账金额","余额","入账方","余额","创建时间","更新时间","操作人","资金来源"};
            ExcelUtil.export(response,"exportExcel",headers,stringList);
        } catch (Exception e) {
            log.error("账单导出异常 ! 异常信息 : [{}]" , e);
        }
    }
}
