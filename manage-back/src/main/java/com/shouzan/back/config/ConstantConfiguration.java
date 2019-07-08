package com.shouzan.back.config;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
/**
 * 
 * @ClassName:  ConstantConfiguration   
 * @Description:常量配置   
 * @author:
 * @date:   2017年11月9日 上午11:42:12   
 *    
 * @Copyright:2017
 *
 */
@Configuration
public class ConstantConfiguration implements EnvironmentAware {
	private RelaxedPropertyResolver propertyResolver;
	/*网关地址*/
	private static  String  GATE_WAY_URL;
	
	/*公众号填写服务器的token*/
	private static  String  WX_TOKE;
	
	/*支付通知模板id*/
	private static  String  PAY_TEMPLATE_CODE;
	
	/*微信第三方平台token*/
	private static  String  WX_THIRD_TOKEN;
	
	/*微信第三方平台消息加解密key*/
	private static  String  WX_THIRD_ENCODINGAESKEY;
	
	/*微信第三方平台APPID*/
	private static  String  WX_THIRD_APPID;
	
	/*微信第三方平台APPSECRET*/
	private static  String  WX_THIRD_APPSECRET;
	
	/*微信点餐小程序APPID*/
	private static  String  WX_MINIORDER_APPID;
	
	/*微信点餐小程序APPSECRET*/
	private static  String  WX_MINIORDER_APPSECRET;

	@SuppressWarnings("static-access")
	@Override
	public void setEnvironment(Environment environment) {
		propertyResolver = new RelaxedPropertyResolver(environment, null);
		this.GATE_WAY_URL = propertyResolver.getProperty("constant.gateWayUrl");
		this.WX_TOKE = propertyResolver.getProperty("wx.token");
		this.PAY_TEMPLATE_CODE = propertyResolver.getProperty("wx.payTemplateCode");
		this.WX_THIRD_TOKEN = propertyResolver.getProperty("wx.third.token");
		this.WX_THIRD_ENCODINGAESKEY = propertyResolver.getProperty("wx.third.encodingAesKey");
		this.WX_THIRD_APPID = propertyResolver.getProperty("wx.third.appid");
		this.WX_THIRD_APPSECRET = propertyResolver.getProperty("wx.third.appSecret");
		this.WX_MINIORDER_APPID = propertyResolver.getProperty("wx.miniorder.appid");
		this.WX_MINIORDER_APPSECRET = propertyResolver.getProperty("wx.miniorder.appSecret");
	}
	/**
	 * 
	 * @Description 方法描述:获取网关地址
	 * @Author      作        者:
	 * @UpdateDate  更新时间:2017年11月9日-下午1:14:33
	 *
	 * @return      
	 * String 网关url
	 */
	public static String getGateUrl() {
		return GATE_WAY_URL;
	}

	/**
	 * 
	 * @return 获取公众号填写服务器的token
	 */
	public static String getWxToken() {
		return WX_TOKE;
	}
	
	/**
	 * 
	 * @return 获取支付通知模板id
	 */
	public static String getPayTemplateCode() {
		return PAY_TEMPLATE_CODE;
	}
	/**
	 * 
	 * @return 获取微信第三方平台token
	 */
	public static String getWxThirdToken() {
		return WX_THIRD_TOKEN;
	}
	/**
	 * 
	 * @return 获取微信第三方平台消息加解密key
	 */
	public static String getWxThirdEncodingAesKey() {
		return WX_THIRD_ENCODINGAESKEY;
	}
	/**
	 * 
	 * @return 获取微信第三方平台APPID
	 */
	public static String getWxThirdAppId() {
		return WX_THIRD_APPID;
	}
	/**
	 * 
	 * @return 获取微信第三方平台APPSECRET
	 */
	public static String getWxThirdAppSecret() {
		return WX_THIRD_APPSECRET;
	}
	/**
	 * 
	 * @return 获取微信点餐小程序APPID
	 */
	public static String getWxMiniOrderAppId() {
		return WX_MINIORDER_APPID;
	}
	/**
	 * 
	 * @return 获取微信点餐小程序APPSECRET
	 */
	public static String getWxMiniOrderAppSecret() {
		return WX_MINIORDER_APPSECRET;
	}
}
