package com.shouzan.common.vo.qf;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/** 
 * @description 类描述：微信实体
 * @author      作者：LIUZEKUN
 * @create      创建时间：2017年9月6日
 * @update      修改时间：2017年9月6日
 */
public class WxVO implements Serializable{

	private static final long serialVersionUID = 1L;
	//****************公共部分****************
	/** 公众账号ID */
	private String appId;
	/** 商户号 */
	private String mchId;
	/** 子商户号 */
	private String subMchId;
	/** 订单号 */
	private String outTradeNo;
	/** 秘钥 */
	private String fkey;
	//***************************************
	
	//****************支付部分****************
	/** 商品描述 */
	private String body;
	/** 订单总金额，单位为分，只能为整数 */
	private String totalFee;
	/** 终端IP，调用微信支付API的机器IP */
	private String spbillCreateIp;
	/** 授权码 */
	private String authCode;
	/** 商品ID */
	private String productId;
	//***************************************
	
	//****************退款部分****************
	/** 微信订单号 */
	private String transactionId;
	/** 退款订单号 */
	private String outRefundNo;
	/** 订单总金额 ，单位为分，只能为整数 */
	private String totalMoney;
	/** 退款总金额 ，单位为分，只能为整数 */
	private String refundFee;
	/**证书路径*/
	private String certUrl;
	/** 退款证书 */
	private File cert;
	//***************************************
	
	//****************订单查询部分****************
	//订单查询部分只用公共部分的
	//***************************************
	
	//****************获取授权url****************
	/** 应用秘钥 */
	private String appSecret;
	/** 授权回调地址 */
	private String redirectUri;
	/** 商户id */
	private String merId;
//	/** 设备id */
//	private String terminalId;
	/** 码值 */
	private String codeKey;
	//***************************************
	
	//****************第三方获取授权url****************
	//还要使用appid和merId和terminalId
	/** 授权回调地址 */
	private String thirdRedirectUri;
	/** 第三方平台appid */
	private String thirdAppId;
	//***************************************
	
	//****************撤单部分****************
	/** 撤单证书 与 退款证书是同一个证书 */
	private File cancelCert;
	//***************************************
	
	//****************对账单部分****************
	//注意：不需要公共部分的订单号和子商户号
	/** 账单日期 账单格式为 yyyyMMdd */
	private String billDate;
	//***************************************
	
	//****************统一下单部分****************
	//注意：包含支付部分（不包括授权码）
	/** 微信用户唯一标识 */
	private String openId;
	/** 异步通知地址 */
	private String notifyUrl;
	//***************************************
	
	//****************异步通知****************
	//注意：只需要公共部分的key
	/** 订单总金额 ，单位为分，只能为整数 */
	private String totalAmount;
	/** 微信异步通知的参数 */
	private SortedMap<String,String> sortMap;
	//********************************
	
	//****************发送模板消息****************
	/** 微信消息模板 */
	private List<WechatTemplateMsgVO> wechatTemplateMsgVOList;
	/** 公众号的全局唯一接口调用凭据 */
	private String accessToken;
	//********************************
	
	//****************批量获取accesstoken****************
	private Set<String> appIdSet;
	private Map<String,String> appIdAndAppSecret;
	//********************************
	
	//****************刷新授权方令牌****************
	private Map<String,String> refreshMap;
	//********************************
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	public String getSubMchId() {
		return subMchId;
	}
	public void setSubMchId(String subMchId) {
		this.subMchId = subMchId;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}
	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getKey() {
		return fkey;
	}
	public void setKey(String key) {
		this.fkey = key;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getOutRefundNo() {
		return outRefundNo;
	}
	public void setOutRefundNo(String outRefundNo) {
		this.outRefundNo = outRefundNo;
	}
	public String getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}
	public String getRefundFee() {
		return refundFee;
	}
	public void setRefundFee(String refundFee) {
		this.refundFee = refundFee;
	}
	public File getCert() {
		return cert;
	}
	public void setCert(File cert) {
		this.cert = cert;
	}
	public File getCancelCert() {
		return cancelCert;
	}
	public void setCancelCert(File cancelCert) {
		this.cancelCert = cancelCert;
	}
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getCertUrl() {
		return certUrl;
	}
	public void setCertUrl(String certUrl) {
		this.certUrl = certUrl;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public String getRedirectUri() {
		return redirectUri;
	}
	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}
	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public SortedMap<String, String> getSortMap() {
		return sortMap;
	}
	public void setSortMap(SortedMap<String, String> sortMap) {
		this.sortMap = sortMap;
	}
//	public String getTerminalId() {
//		return terminalId;
//	}
//	public void setTerminalId(String terminalId) {
//		this.terminalId = terminalId;
//	}
	public List<WechatTemplateMsgVO> getWechatTemplateMsgVOList() {
	
		return wechatTemplateMsgVOList;
	}
	public void setWechatTemplateMsgVOList(List<WechatTemplateMsgVO> wechatTemplateMsgVOList) {
	
		this.wechatTemplateMsgVOList = wechatTemplateMsgVOList;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getThirdRedirectUri() {
	
		return thirdRedirectUri;
	}
	
	public void setThirdRedirectUri(String thirdRedirectUri) {
	
		this.thirdRedirectUri = thirdRedirectUri;
	}
	
	public String getThirdAppId() {
	
		return thirdAppId;
	}
	
	public void setThirdAppId(String thirdAppId) {
	
		this.thirdAppId = thirdAppId;
	}
	
	public String getCodeKey() {
	
		return codeKey;
	}
	
	public void setCodeKey(String codeKey) {
	
		this.codeKey = codeKey;
	}
	
	public Set<String> getAppIdSet() {
	
		return appIdSet;
	}
	
	public void setAppIdSet(Set<String> appIdSet) {
	
		this.appIdSet = appIdSet;
	}
	public Map<String, String> getAppIdAndAppSecret() {
	
		return appIdAndAppSecret;
	}
	
	public void setAppIdAndAppSecret(Map<String, String> appIdAndAppSecret) {
	
		this.appIdAndAppSecret = appIdAndAppSecret;
	}
	
	public Map<String, String> getRefreshMap() {
	
		return refreshMap;
	}
	
	public void setRefreshMap(Map<String, String> refreshMap) {
	
		this.refreshMap = refreshMap;
	}
	
	
}
