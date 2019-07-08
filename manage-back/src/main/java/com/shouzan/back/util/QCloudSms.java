package com.shouzan.back.util;

import com.alibaba.fastjson.JSONException;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.shouzan.back.constant.Response;
import com.shouzan.common.msg.ObjectRestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: com.shouzan.back.util.QCloudSms
 * @Author: bin.yang
 * @Date: 2019/4/16 14:59
 * @Description: TODO
 */
@Slf4j
@Component
public class QCloudSms {

    @Value("${message.sms.appId}")
    private Integer APP_ID;

    @Value("${message.sms.appKey}")
    private String APP_KEY;

    @Value("${message.sms.code.templeId}")
    private Integer TEMPLATE_ID;

    @Value("${message.sms.smsSign}")
    private String SMS_SIGN;

    /**
     * @Description: (腾讯云短信服务)
     * @param phoneNumber
     * @param params
     * @[param] [phoneNumber]
     * @return com.shouzan.common.msg.ObjectRestResponse
     * @author:  bin.yang
     * @date:  2019/4/16 3:00 PM
     */
    public ObjectRestResponse messageCodeSms(String phoneNumber, String[] params){
        ObjectRestResponse resp = new ObjectRestResponse();
        try {
            SmsSingleSender sender = new SmsSingleSender(APP_ID, APP_KEY);
            SmsSingleSenderResult result = sender.sendWithParam("86", phoneNumber,
                    TEMPLATE_ID, params, SMS_SIGN, "", "");
            int smsCode = result.result;
            String errMsg = unicodeToString(result.errMsg);
            if(smsCode == 0){
                resp.msg("发送成功");
                resp.rel(Response.SUCCESS);
            } else{
                resp.msg("发送失败");
                resp.rel(Response.FAILURE);
                log.error("短信发送失败-手机号[{}],errCode:{},errMsg:{}", phoneNumber,smsCode,errMsg);
            }
            return resp;
        } catch (HTTPException | JSONException | IOException e) {
            e.printStackTrace();
            resp.msg("短信发送失败，请重试");
            resp.rel(Response.FAILURE);
            log.error("短信发送异常-手机号[{}]", phoneNumber, e);
        }
        return resp;
    }

    /**
     * unicode转中文
     */
    private String unicodeToString(String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch+"" );
        }
        return str;
    }
}
