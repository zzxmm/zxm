package com.shouzan.common.vo.qf;

import java.io.Serializable;
import java.util.Map;

/** 
 * @description 类描述：支付宝实体
 * @author      作者：LIUZEKUN
 * @create      创建时间：2017年8月31日
 * @update      修改时间：2017年8月31日
 */
public class ZfbVO implements Serializable{

	private static final long serialVersionUID = 1L;
	//****************公共部分****************
	//以下所有部分都含有公共部分
	/** 应用ID */
	private String appId;
	/** 应用私钥 */
	private String privateKey;
	/** 支付宝公钥 */
	private String alipayPublicKey;
	/** 第三方应用授权令牌 */
	private String token;
	/** 订单号(清分系统生成的) */
	private String outTradeNo;
	/** 支付宝分配的系统商编号 */
	private String pId;
	//***************************************
	
	//****************支付部分****************
	/** 支付授权码 */
	private String authCode;
	/** 订单标题 */
	private String subject;
	/** 订单总金额 */
	private String totalAmount;
	/** 商户门店编号 */
	private String storeId;
	//***************************************
	
	//****************退款部分****************
	/** 退款金额 */
	private String refundAmount;
	/** 退款单号*/
	private String outRequestNo;
	//***************************************
	
	//****************交易创建部分****************
	/** 买家的支付宝唯一用户号 */
	private String buyerId;
	/** 订单标题 */
	private String creSubject;
	/** 订单总金额 */
	private String creTotalAmount;
	/** 异步通知地址 */
	private String notifyUrl;
	//***************************************
	
	//****************(获取user_id)授权回调部分****************
	//注意：此部分不需要公共部分的订单号
	/** 回调收到的auth_code */
	private String notifyAuthCode;
	//***************************************
	
	//****************异步通知****************
	//注意：此部分不需要公共部分的订单号和授权令牌
	/** 订单金额 */
	private String totalMoney;
	/** 支付宝异步通知参数 */
	private Map<String, String> params;
	//***************************************
	
	//****************对账单下载地址****************
	//注意：此部分不需要公共部分的订单号
	/** 账单日期 账单格式为yyyy-MM-dd */
	private String billDate;
	//***************************************
	
	
	//****************第三方应用授权令牌****************
	//注意：此部分不需要公共部分的订单号和第三方应用授权令牌
	/** 授权类型 authorization_code表示换取app_auth_token,refresh_token表示刷新app_auth_token */
	private String grantType;
	/** 授权码，如果grant_type的值为authorization_code。该值必须填写 */
	private String appCode;
	/** 刷新令牌，如果grant_type值为refresh_token。该值不能为空 */
	private String refreshToken;
	//***************************************
	
	//****************统一收单线下交易预创建****************
	/*商家收款金额*/
	private String prepareAmount;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	public String getAlipayPublicKey() {
		return alipayPublicKey;
	}
	public void setAlipayPublicKey(String alipayPublicKey) {
		this.alipayPublicKey = alipayPublicKey;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getOutRequestNo() {
		return outRequestNo;
	}
	public void setOutRequestNo(String outRequestNo) {
		this.outRequestNo = outRequestNo;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getCreSubject() {
		return creSubject;
	}
	public void setCreSubject(String creSubject) {
		this.creSubject = creSubject;
	}
	public String getCreTotalAmount() {
		return creTotalAmount;
	}
	public void setCreTotalAmount(String creTotalAmount) {
		this.creTotalAmount = creTotalAmount;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getNotifyAuthCode() {
		return notifyAuthCode;
	}
	public void setNotifyAuthCode(String notifyAuthCode) {
		this.notifyAuthCode = notifyAuthCode;
	}
	public String getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public String getGrantType() {
		return grantType;
	}
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public Map<String, String> getParams() {
		return params;
	}
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	public String getPrepareAmount() {
		return prepareAmount;
	}
	public void setPrepareAmount(String prepareAmount) {
		this.prepareAmount = prepareAmount;
	}
	public String getpId() {
	
		return pId;
	}
	public void setpId(String pId) {
	
		this.pId = pId;
	}
	/**  
	 * @Description: 商户门店编号
	 * @return: String <BR>  
	 */
	public String getStoreId() {
		return storeId;
	}
	/**  
	 * @Description: 商户门店编号
	 * @return: String <BR>  
	 */
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	

}
