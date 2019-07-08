package com.shouzan.back.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.shouzan.back.biz.CardrecordBiz;
import com.shouzan.back.biz.OrderBiz;
import com.shouzan.back.biz.WeChatCouponBiz;
import com.shouzan.back.biz.impl.WeChatCouponBizImpl;
import com.shouzan.back.config.redis.RedisTemplate;
import com.shouzan.back.constant.Response;
import com.shouzan.back.entity.Cardrecord;
import com.shouzan.back.entity.Order;
import com.shouzan.back.entity.card.CardBeanJson;
import com.shouzan.back.entity.card.page.CardPage;
import com.shouzan.back.entity.card.update.CardUp;
import com.shouzan.back.entity.card.update.StockUp;
import com.shouzan.back.util.WeChatCoupon;
import com.shouzan.back.vo.SearchSatisfy;
import com.shouzan.common.msg.ObjectRestResponse;
import com.shouzan.common.rest.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: bin.yang
 * @Date: 2018/12/28 12:34
 * @Description:  公众号卡卷管理
 */

@Controller
@RequestMapping("wcCoupon")
@Validated
@Slf4j
public class WeChatCouponController extends BaseController<WeChatCouponBizImpl, CardUp> {

    @Autowired
    private WeChatCouponBiz weChatCouponBiz;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${private_tokenKey}")
    private  String PRIVATE_KEY;

    @Value("${private_redisDB}")
    private  Integer PRIVATE_DB;

    @Value("${private_path}")
    private  String PRIVATE_PATH;

    @Value("${private_cospath}")
    private  String PRIVATE_COS;

    @Value("${center_url}")
    private  String PRIVATE_CURL;

    /**
     * @Description: (微信公众号卡卷分页列表查询)
     * @param pageSize
     * @param current
     * @param search
     * @[param] [pageSize, current, search]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/1/10 12:47 PM
     */
    @RequestMapping(value = "/page" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse page (@RequestParam(defaultValue = "10") int pageSize , @RequestParam(defaultValue = "1") int current,
                                                  SearchSatisfy search){
        try {
            int count = weChatCouponBiz.queryPageCount(search);
            PageHelper.startPage(current,pageSize,false);
            List<CardPage> list = weChatCouponBiz.queryPageList(search);
            log.info("request : 微信公众号卡卷列表查询 , 每页显示[{}] , 当前显示[{}] . parameter : [{}]",pageSize,current,search);
            return new ObjectRestResponse<>().total(count).rows(list).rel(Response.SUCCESS);
        } catch (Exception e) {
            log.error("异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (新增微信公众号卡卷)
     * @param beanJson
     * @[param] [card]
     * @return int
     * @author:  bin.yang
     * @date:  2019/1/3 10:59 AM
     */
    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse addCardBean (@RequestBody @Valid CardBeanJson beanJson){
        ObjectRestResponse restObject = weChatCouponBiz.validWcChatCoupon(beanJson);
        if(!restObject.getRel()){
            return restObject;
        }
        log.info("request : 新增微信公众号卡卷  ,  parameter : [{}]",beanJson);
        try {
            String currentUserId = getCurrentUserId();
            int id = weChatCouponBiz.addWeChetCouponInfo(beanJson,currentUserId);
            if(id > 0){
                beanJson.getCard().getGeneral_coupon().getBase_info().setCenter_url(PRIVATE_CURL+id);
                ObjectRestResponse weChatRest = createWeChatCard(beanJson);
                if(weChatRest.getRel()){
                    String wcCardId = weChatRest.getResult().toString();
                    weChatCouponBiz.insertWeChatCardId(wcCardId,id,beanJson.getCard().getGeneral_coupon().getBase_info().getCenter_url());
                    return new ObjectRestResponse<>().rel(Response.SUCCESS).msg("卡卷创建成功");
                }else{
                    weChatCouponBiz.deleteWeChetCouponById(id);
                    return weChatRest;
                }
            }else {
                return new ObjectRestResponse().rel(Response.FAILURE).msg("卡卷创建失败");
            }
        } catch (Exception e) {
            log.error("异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (微信公众号卡卷修改信息)
     * @param beanJson
     * @[param] [beanJson]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/1/7 3:25 PM
     */
    @RequestMapping(value = "/updateInfo" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse updateInfo (@RequestBody @Valid CardUp beanJson){
        ObjectRestResponse<CardUp> response = weChatCouponBiz.updateValidJsonData(beanJson);
        if(!response.getRel()){   return response;
        }

        CardUp result = response.getResult();
        log.info("request : 微信公众号卡卷信息修改  ,  parameter : [{}]",result);
        try {
            ObjectRestResponse objectRest = updateWeChatCard(result);
            if(objectRest.getRel()){
                String currentUserId = getCurrentUserId();
                int i = weChatCouponBiz.updateWcCardInfo(result,currentUserId);
                if(i > 0){
                    if(objectRest.getResult().equals(true)){
                        return new ObjectRestResponse<>().rel(Response.SUCCESS).result("修改成功 , 信息审核中.");
                    }else{
                        return new ObjectRestResponse<>().rel(Response.SUCCESS).result("卡卷信息修改成功");
                    }
                }else{
                    return objectRest;
                }
            }else{
                return objectRest;
            }
        } catch (Exception e) {
            log.error("异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (微信公众号卡卷详细查询)
     * @param id
     * @[param] [card]
     * @return int
     * @author:  bin.yang
     * @date:  2019/1/3 10:59 AM
     */
    @RequestMapping(value = "/findInfo/{id}" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse findInfo (@PathVariable int id){
        log.info("request : 微信公众号卡卷详细查询  ,  parameter : [{}]",id);
        try {
            CardBeanJson beanJson = weChatCouponBiz.findInfoWeChetCouById(id);
            return new ObjectRestResponse<>().rel(Response.SUCCESS).result(beanJson);
        } catch (Exception e) {
            log.error("异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (修改公众号卡卷库存)
     * @param stock
     * @[param] [stock]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:   4:25 PM
     */
    @RequestMapping(value = "/UpdateStock" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse UpdateStock (@RequestBody @Valid StockUp stock){
        log.info("request : 微信公众号卡卷库存修改  ,  parameter : [{}]",stock);
        StockUp stockUp = new StockUp();
        stockUp.setCard_id(stock.getCard_id());
        // 库存数量间的逻辑判断
        if(stock.getReduce_stock_value() < 0 || stock.getIncrease_stock_value() < 0){
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("修改库存失败 , 数据错误");
        }else {
            if(stock.getIncrease_stock_value() != stock.getReduce_stock_value()){
                if(stock.getIncrease_stock_value() > stock.getReduce_stock_value()){
                    stockUp.setReduce_stock_value(stock.getIncrease_stock_value() - stock.getReduce_stock_value());
                }else {
                    stockUp.setIncrease_stock_value(stock.getReduce_stock_value() - stock.getIncrease_stock_value());
                }
            }else {
                return new ObjectRestResponse<>().rel(Response.SUCCESS).msg("修改成功");
            }
        }
        try {
            ObjectRestResponse objectRest = updateWeChatCardStock(stockUp);
            if(objectRest.getRel()){
                return weChatCouponBiz.updateWcCardStock(stockUp);
            }else {
                return objectRest;
            }
        } catch (Exception e) {
            log.error("异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (删除公众号卡卷)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/1/10 3:34 PM
     */
    @RequestMapping(value = "/delete" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse delete (@NotNull(message = "卡卷ID无效") String id){
        log.info("request : 删除微信公众号卡卷  ,  parameter : [{}]",id);
        try {
            ObjectRestResponse restObject = deleteWeChatCard(id);
            if(restObject.getRel()){
                return weChatCouponBiz.deleteWeChetCoupon(id);
            }else{
                return restObject;
            }
        } catch (Exception e) {
            log.error("异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (微信素材上传接口)
     * @param file
     * @[param] [file]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/1/4 10:53 AM
     */
    @RequestMapping(value = "uploadFile" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse uploadFile (@RequestParam(value = "file" , required = false) MultipartFile file){
        //获取文件名
        String fileName = WeChatCoupon.getFileName(file.getOriginalFilename());
        log.info("request : 微信素材上传  ,  parameter : [{}]",fileName);
        if(WeChatCoupon.upload(file, PRIVATE_PATH,fileName)){
            String fullPathName = WeChatCoupon.getFullPathName(PRIVATE_PATH, fileName);
            // 获取token
            String token = getToken();
            try {
                JSONObject jsonObject = WeChatCoupon.sendWeChat(fullPathName, token);
                if(jsonObject.get("url") != null){
                    // 上传到COS服务器
                    String cosName = WeChatCoupon.getName(jsonObject.getString("url"), fullPathName);
                    String object = WeChatCoupon.HttpPostCOS(PRIVATE_PATH,PRIVATE_COS, cosName,fileName);
                    if(!"".equals(object)){
                        //删除 临时文件
                        WeChatCoupon.deleteFile(PRIVATE_PATH , fileName);
                        return new ObjectRestResponse().rel(Response.SUCCESS).result(jsonObject);
                    }else{
                        return new ObjectRestResponse().rel(Response.FAILURE).msg("上传失败");
                    }
                }else {
                    log.error("WeChatAPI upload error Tips  -> ->  "+jsonObject.getString("errmsg"));
                    return new ObjectRestResponse().rel(Response.FAILURE).msg("上传失败");
                }
            } catch (Exception e) {
                log.error("异常信息 : [{}]" , e);
                return new ObjectRestResponse().rel(Response.FAILURE).msg("上传失败");
            }
        }else {
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg("上传失败");
        }
    }

    /**
     * @Auther: bin.yang
     * @Date: 2019/1/4 3:41 PM
     *
     * @Description:  调用API创建微信公众号卡卷
     */
    private ObjectRestResponse createWeChatCard (CardBeanJson beanJson){
        log.info(" REQUEST : WeChatApi add  ,  parameter : [{}]",JSON.toJSONString(beanJson));
        String token = getToken();
        JSONObject jsonObject = WeChatCoupon.HttpPostWeChat(token,WeChatCoupon.WCCARD_CREATE, JSON.toJSONString(beanJson));
        if(jsonObject.get("errcode").equals(0)){
            String cardId = jsonObject.getString("card_id");
            return new ObjectRestResponse().rel(Response.SUCCESS).result(cardId);
        }else{
            log.error("WeChatAPI create error Tips  -> ->  "+jsonObject);
            return new ObjectRestResponse().rel(Response.FAILURE).msg("卡卷创建失败");
        }
    }

    /**
     * @Auther: bin.yang
     * @Date: 2019/1/7 3:26 PM
     *
     * @Description:  调用API修改微信公众号卡卷信息
     */
    private ObjectRestResponse updateWeChatCard (CardUp beanJson){
        log.info("request : WeChatApi update  ,  parameter : [{}]",JSON.toJSONString(beanJson));
        String token = getToken();
        JSONObject jsonObject = WeChatCoupon.HttpPostWeChat(token, WeChatCoupon.WCCARD_UPDATE ,JSON.toJSONString(beanJson));
        if(jsonObject.get("errcode").equals(0)){
            Boolean send_check = jsonObject.getBoolean("send_check");
            return new ObjectRestResponse().rel(Response.SUCCESS).result(send_check);
        }else{
            log.error("WeChatAPI update error Tips  -> ->  "+jsonObject);
            return new ObjectRestResponse().rel(Response.FAILURE).msg("修改卡卷信息失败");
        }
    }

    /**
     * @Auther: bin.yang
     * @Date: 2019/1/7 3:26 PM
     *
     * @Description:  调用API修改微信公众号卡卷库存
     */
    private ObjectRestResponse updateWeChatCardStock (@RequestBody @Valid StockUp stock){
        log.info("request : WeChatApi stock  ,  parameter : [{}]",JSON.toJSONString(stock));
        String token = getToken();
        JSONObject jsonObject = WeChatCoupon.HttpPostWeChat(token, WeChatCoupon.WCCARD_STOCK ,JSON.toJSONString(stock));
        if(jsonObject.get("errcode").equals(0)){
            return new ObjectRestResponse().rel(Response.SUCCESS);
        }else{
            log.error("WeChatAPI stock error Tips  -> ->  "+jsonObject);
            return new ObjectRestResponse().rel(Response.FAILURE).msg("修改卡卷库存失败");
        }
    }

    /**
     * @Auther: bin.yang
     * @Date: 2019/1/10 3:33 PM
     *
     * @Description:   删除公众号卡卷
     */
    private ObjectRestResponse deleteWeChatCard (String id){
        log.info("request : WeChatApi delete  ,  parameter : [{}]",id);
        String token = getToken();
        JSONObject json = new JSONObject();
        json.put("card_id",id);
        JSONObject jsonObject = WeChatCoupon.HttpPostWeChat(token, WeChatCoupon.WCCARD_DELETE , JSON.toJSONString(json));
        if(jsonObject.get("errcode").equals(0)){
            return new ObjectRestResponse().rel(Response.SUCCESS);
        }else{
            log.error("WeChatAPI delete error Tips  -> ->  "+jsonObject);
            return new ObjectRestResponse().rel(Response.FAILURE).msg("删除卡卷失败");
        }
    }

    /**
     * @Auther: bin.yang
     * @Date: 2019/1/4 3:28 PM
     *
     * @Description: 获取token
     */
    private String getToken() {
        redisTemplate.REDIS_DB_INDEX.set(PRIVATE_DB);
        String token = (String)redisTemplate.opsForValue().get(PRIVATE_KEY);
        return token;
    }
}


/**
 * @Author: bin.yang
 * @Date: 2019/1/23 2:52 PM
 *
 * @Description:  公众号卡卷订单管理
 */
@Controller
@RequestMapping(value = "wcOrder")
@Slf4j
@Validated
class wechatOrderController{

    @Autowired
    private OrderBiz orderBiz;

    /**
     * @Description: (公众号订单分页查询)
     * @param pageSize
     * @param current
     * @param search
     * @[param] [pageSize, current, search]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/1/23 4:17 PM
     */
    @RequestMapping("page")
    @ResponseBody
    public ObjectRestResponse page(@RequestParam(defaultValue = "10") int pageSize , @RequestParam(defaultValue = "1") int current,
                                   SearchSatisfy search){
        try {
            search.setTypes(3);
            int count = orderBiz.queryPageCount(search);
            PageHelper.startPage(current,pageSize,false);
            List<Order> list = orderBiz.queryPageList(search);
            log.info("request : 公众号订单分页查询 , 每页显示[{}] , 当前显示[{}] . parameter : [{}]",pageSize,current,search);
            return new ObjectRestResponse<>().total(count).rows(list).rel(Response.SUCCESS);
        } catch (Exception e) {
            log.error("异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (查询公众号订单详细)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Order>
     * @author:  bin.yang
     * @date:  2019/1/23 4:18 PM
     */
    @RequestMapping(value = "/findInfo/{id}" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Order> findInfo(@PathVariable int id){
        try {
            log.info("request : 公众号订单详细查询 . parameter : 订单ID[{}]",id);
            return orderBiz.queryOrderById(id);
        } catch (Exception e) {
            log.error("异常信息 : [{}]" , e);
            return new ObjectRestResponse().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (查询订单)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Order>
     * @author:  bin.yang
     * @date:  2019/1/23 4:18 PM
     */
    @RequestMapping(value = "/update" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Order> update(@NotNull(message = "选择要撤销的订单") int id){
        try {
            log.info("request : (公众号)撤销订单 . parameter : 订单ID[{}]",id);
            return orderBiz.updateOrderStatus(id);
        } catch (Exception e) {
            log.error("异常信息 : [{}]" , e);
            return new ObjectRestResponse().rel(Response.FAILURE).msg(e.getMessage());
        }
    }
}


/**
 * @Author: bin.yang
 * @Date: 2019/1/23 2:52 PM
 *
 * @Description:  公众号卡卷记录
 */
@Controller
@RequestMapping(value = "wcRecord")
@Slf4j
@Validated
class wechatCardRecord{

    @Autowired
    private CardrecordBiz cardrecordBiz;

    /**
     * @Description: (公众号卡卷记录分页查询)
     * @param pageSize
     * @param current
     * @param search
     * @[param] [pageSize, current, search]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/1/23 4:26 PM
     */
    @RequestMapping("page")
    @ResponseBody
    public ObjectRestResponse page(@RequestParam(defaultValue = "10") int pageSize , @RequestParam(defaultValue = "1") int current,
                                   SearchSatisfy search){
        try {
            search.setTypes(3);
            int count = cardrecordBiz.queryPageCount(search);
            PageHelper.startPage(current,pageSize,false);
            List<Cardrecord> list = cardrecordBiz.queryPageList(search);
            log.info("request : 公众号卡卷记录分页查询 , 每页显示[{}] , 当前显示[{}] . parameter : [{}]",pageSize,current,search);
            return new ObjectRestResponse<>().total(count).rows(list).rel(Response.SUCCESS);
        } catch (Exception e) {
            log.error("异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (查询公众号卡卷记录详细信息)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Cardrecord>
     * @author:  bin.yang
     * @date:  2019/1/23 4:27 PM
     */
    @RequestMapping(value = "/findInfo/{id}" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<Cardrecord> findInfo(@PathVariable int id){
        try {
            log.info("request : 公众号卡卷记录详细查询 . parameter : 卡卷记录ID[{}]",id);
            return cardrecordBiz.selectCardrecordById(id,3);
        } catch (Exception e) {
            log.error("异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (卡卷发放)
     * @param id
     * @[param] [id]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Cardrecord>
     * @author:  bin.yang
     * @date:  2019/1/23 4:28 PM
     */
    @RequestMapping(value = "/update" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Cardrecord> update(@NotNull(message = "选择要修改的卡卷记录ID")String id){
        try {
            log.info("request : 公众号卡卷发放 . parameter : 卡卷记录ID[{}]",id);
            return cardrecordBiz.updateCardrecordById(id);
        } catch (Exception e) {
            log.error("异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }

    /**
     * @Description: (卡卷退款)
     * @param orderNo
     * @[param] [orderNo]
     * @return com.shouzan.common.msg.ObjectRestResponse<com.shouzan.back.entity.Cardrecord>
     * @author:  bin.yang
     * @date:  2019/1/23 4:28 PM
     */
    @RequestMapping(value = "/refund" , method = RequestMethod.POST)
    @ResponseBody
    public ObjectRestResponse<Cardrecord> refund(@NotNull(message = "提交要退款的订单号")String orderNo){
        try {
            log.info("request : (公众号)卡卷退款 . parameter : 卡卷记录ID[{}]",orderNo);
            return cardrecordBiz.refundCardrecordByOrderNo(orderNo);
        } catch (Exception e) {
            log.error("异常信息 : [{}]" , e);
            return new ObjectRestResponse<>().rel(Response.FAILURE).msg(e.getMessage());
        }
    }
}