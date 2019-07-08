package com.shouzan.back.rest;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.shouzan.back.entity.UserWechat;
import com.shouzan.back.mapper.UserMapper;
import com.shouzan.back.mapper.UserWechatMapper;
import com.shouzan.back.rpc.union.UserList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: com.shouzan.back.rest.UnionController
 * @Author: bin.yang
 * @Date: 2019/6/13 10:21
 * @Description: TODO
 */
@Controller
@RequestMapping("/uni")
@Validated
@Slf4j
public class UnionController {


    private final static String API = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=";

    private final static String TOKEN = "22_vFcBbcyS0r7oe2JDUQxFMqb3elKv5JmHN_NEV1F_Ts650p9_aTs3sOJjDyf2GtysnE7OxDF5GUdgmX4Nzouq5jkGvzvWS1sS3mTBuHF7ebo6aDWAGq9t3hi_brWcaJ1ipvOR2BBdLMU2Xqq6JELhABAVQX";

    private final static String WAPI = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=";

    private final static String OPEN = "&lang=zh_CN&openid=";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserWechatMapper userWechatMapper;

    @RequestMapping("/launch")
    @ResponseBody
    public String checkUnion() {

        log.info("获取用户unionid 同步至数据库");
        int go = goToWeChat();
        log.info("UnionId 用户同步完成 : 成功执行 [{}] 条", go);

        log.info("获取用户unionid 同步至用户中心数据库");
        int u = goToWeChatUuc();
        log.info("UnionId 用户中心同步完成 : 成功执行 [{}] 条", u);

        log.info("卡卷订单插入用户ID");
        int p = putUserId();
        log.info("用户ID同步至订单完成 : 成功执行 [{}] 条", p);

        log.info("卡卷记录插入用户ID");
        int i = putUserIdCard();
        log.info("用户ID同步至记录完成 : 成功执行 [{}] 条", i);

        return "任务执行完成";
    }

    public int goToWeChat() {

        int count = 0;
        RestTemplate restTemplate = new RestTemplate();
        int page = 1;
        int size = 1000;
        boolean flag = true;

        List<UserWechat> list;

        UserWechat userWechat;

        while (flag) {

            PageHelper.startPage(page, size, false);
            List<UserList> lists = userMapper.findUserOpenID();

            if (lists != null && lists.size() > 0) {
                list = new ArrayList<UserWechat>();

                for (UserList userList : lists) {
                    String forObject = restTemplate.getForObject(WAPI + TOKEN + OPEN + userList.getOpenId(), String.class);
                    JSONObject jsonObject = JSONObject.parseObject(forObject);
                    String unionid = jsonObject.getString("unionid");
                    userWechat = new UserWechat();
                    userWechat.setUserId(userList.getId());
                    userWechat.setOpenidType((byte) 0);
                    userWechat.setWechatOpenid(userList.getOpenId());
                    userWechat.setWechatUnionid(unionid);
                    list.add(userWechat);
                }

                log.info("同步至数据库 : 总条数[{}] , 数据集 [{}]", list.size(), list.toString());

                int i = userWechatMapper.insertPageInfo(list);

                log.info("数据插入成功 : 总条数 [{}]", i);

                count += i;

                page++;

            } else {
                flag = false;
            }
        }

        return count;

    }

    public int putUserId() {

        int count = 0;
        int page = 1;
        int size = 1000;
        boolean flag = true;

        while (flag) {

            PageHelper.startPage(page, size, false);
            List<UserList> lists = userWechatMapper.batchFindInfoOrder();
            if (lists != null && lists.size() > 0) {
                log.info("同步userId至卡卷订单 : 总条数[{}] , 数据集 [{}]", lists.size(), lists.toString());

                int i = userWechatMapper.batchUpdateOrder(lists);

                log.info("同步卡卷订单 : 总条数 [{}]", i);

                count += i;
            }

            PageHelper.startPage(page, size, false);
            List<UserList> listss = userWechatMapper.batchFindRechargeOrder();
            if (listss != null && listss.size() > 0) {
                log.info("同步userId至充值订单 : 总条数[{}] , 数据集 [{}]", listss.size(), listss.toString());

                int i = userWechatMapper.batchUpdateRechargeOrder(listss);

                log.info("同步充值订单 : 总条数 [{}]", i);

                count += i;
            }

            PageHelper.startPage(page, size, false);
            List<UserList> listsss = userWechatMapper.batchFindSecne();
            if (listsss != null && listsss.size() > 0) {
                log.info("同步userId至银行一分钱订单 : 总条数[{}] , 数据集 [{}]", listsss.size(), listsss.toString());

                int i = userWechatMapper.batchUpdateSecne(listsss);

                log.info("同步银行一分钱订单 : 总条数 [{}]", i);

                count += i;
            }

            if ((listss == null || listss.size() == 0) && (lists == null || lists.size() == 0) && (listsss == null || listsss.size() == 0)) {
                flag = false;
            } else {
                page++;
            }
        }

        return count;
    }

    public int putUserIdCard() {

        int count = 0;
        int page = 1;
        int size = 1000;
        boolean flag = true;

        while (flag) {

            PageHelper.startPage(page, size, false);
            List<UserList> lists = userWechatMapper.batchFindInfoCard();
            if (lists != null && lists.size() > 0) {
                log.info("同步userId至卡卷记录 : 总条数[{}] , 数据集 [{}]", lists.size(), lists.toString());

                int i = userWechatMapper.batchUpdateCard(lists);

                log.info("同步卡卷记录 : 总条数 [{}]", i);

                count += i;
            }

            PageHelper.startPage(page, size, false);
            List<UserList> listss = userWechatMapper.batchFindInfoCode();
            if (listss != null && listss.size() > 0) {
                log.info("同步userId至串码记录 : 总条数[{}] , 数据集 [{}]", listss.size(), listss.toString());

                int i = userWechatMapper.batchUpdateCode(listss);

                log.info("同步串码记录 : 总条数 [{}]", i);

                count += i;
            }

            PageHelper.startPage(page, size, false);
            List<UserList> listsss = userWechatMapper.batchFindRechargeCard();
            if (listsss != null && listsss.size() > 0) {
                log.info("同步userId至充值记录 : 总条数[{}] , 数据集 [{}]", listsss.size(), listsss.toString());

                int i = userWechatMapper.batchUpdateRechargeCard(listsss);

                log.info("同步充值记录 : 总条数 [{}]", i);

                count += i;
            }

            if ((listss == null || listss.size() == 0) && (lists == null || lists.size() == 0) && (listsss == null || listsss.size() == 0)) {
                flag = false;
            } else {
                page++;
            }
        }

        return count;
    }

    public int goToWeChatUuc() {

        int count = 0;
        RestTemplate restTemplate = new RestTemplate();
        int page = 1;
        int size = 1000;
        boolean flag = true;

        List<UserWechat> list;

        UserWechat userWechat;

        while (flag) {

            PageHelper.startPage(page, size, false);
            List<UserList> lists = userMapper.findUucUserOpenID();

            if (lists != null && lists.size() > 0) {
                list = new ArrayList<UserWechat>();

                for (UserList userList : lists) {
                    String forObject = restTemplate.getForObject(WAPI + TOKEN + OPEN + userList.getOpenId(), String.class);
                    JSONObject jsonObject = JSONObject.parseObject(forObject);
                    String unionid = jsonObject.getString("unionid");
                    userWechat = new UserWechat();
                    userWechat.setUserId(userList.getId());
                    userWechat.setOpenidType((byte) 0);
                    userWechat.setWechatOpenid(userList.getOpenId());
                    userWechat.setWechatUnionid(unionid);
                    list.add(userWechat);
                }

                log.info("同步至用户中心数据库 : 总条数[{}] , 数据集 [{}]", list.size(), list.toString());

                int i = userWechatMapper.insertUucPageInfo(list);

                log.info("用户中心数据插入成功 : 总条数 [{}]", i);

                count += i;

                page++;

            } else {
                flag = false;
            }
        }

        return count;

    }

}